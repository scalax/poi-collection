package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.{Cell, CellStyle}

trait CellDataAbs {
  self =>

  type DataType
  val data: DataType

  protected val operation: CellWriter[DataType]

  val styleTransform: List[StyleTransform]

  def set(cell: Cell, cellStlye: Option[CellStyle]): Boolean = {
    operation.set(cell, data, cellStlye)
  }

  def withTransforms(trans: List[StyleTransform]): CellDataAbs = {
    implicit val operation1 = self.operation
    new CellData(data, trans)
  }

  def withTransforms(trans: StyleTransform*): CellDataAbs = {
    implicit val operation1 = self.operation
    new CellData(data, trans.toList)
  }

  def addTransform(tran: List[StyleTransform]): CellDataAbs = {
    implicit val operation1 = self.operation
    val thisTrans = this.styleTransform
    new CellData(data, thisTrans ::: tran)
  }

  def addTransform(tran: StyleTransform*): CellDataAbs = {
    implicit val operation1 = self.operation
    val thisTrans = this.styleTransform
    new CellData(data, thisTrans ::: tran.toList)
  }

}

case class CellData[T: CellWriter](
    override val data: T,
    override val styleTransform: List[StyleTransform])
    extends CellDataAbs {

  override type DataType = T

  override protected val operation = implicitly[CellWriter[T]]

  override def withTransforms(trans: List[StyleTransform]): CellData[T] = {
    new CellData(data, trans)
  }

  override def withTransforms(trans: StyleTransform*): CellData[T] = {
    new CellData(data, trans.toList)
  }

  override def addTransform(tran: List[StyleTransform]): CellData[T] = {
    val thisTrans = this.styleTransform
    new CellData(data, thisTrans ::: tran)
  }

  override def addTransform(tran: StyleTransform*): CellData[T] = {
    val thisTrans = this.styleTransform
    new CellData(data, thisTrans ::: tran.toList)
  }

}

object CellData {

  def gen[T](data: T)(implicit operation: CellWriter[T]): CellData[T] = {
    CellData(data, List.empty)
  }

}

trait StyleTransform {

  def operation(cellStyle: CellStyle): CellStyle

}

object StyleTransform {

  def apply(tran: CellStyle => CellStyle): StyleTransform = {
    new StyleTransform {
      override def operation(cs: CellStyle): CellStyle = tran(cs)
    }
  }

}
