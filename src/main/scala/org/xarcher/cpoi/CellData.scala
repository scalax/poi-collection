package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.{ Cell, CellStyle }

import scala.language.existentials
import scala.language.implicitConversions

trait CellDataAbs {
  self =>

  type DataType
  val data: Option[DataType]

  protected val operation: WriteableCellOperationAbs[DataType]

  val styleTrans: List[StyleTransform]

  def setValue(cell: Cell): Boolean = {
    operation.set(data, Option(cell))
  }

  def withTransforms(trans: List[StyleTransform]): CellDataAbs = {
    implicit val operation1 = self.operation
    new CellData(data) {
      override val styleTrans = trans
    }
  }

  def withTransforms(trans: StyleTransform*): CellDataAbs = {
    implicit val operation1 = self.operation
    new CellData(data) {
      override val styleTrans = trans.toList
    }
  }

  def addTransform(tran: List[StyleTransform]): CellDataAbs = {
    implicit val operation1 = self.operation
    val thisTrans = this.styleTrans
    new CellData(data) {
      override val styleTrans = thisTrans ::: tran
    }
  }

  def addTransform(tran: StyleTransform*): CellDataAbs = {
    implicit val operation1 = self.operation
    val thisTrans = this.styleTrans
    new CellData(data) {
      override val styleTrans = thisTrans ::: tran.toList
    }
  }

}

case class CellData[T: WriteableCellOperationAbs](override val data: Option[T]) extends CellDataAbs {

  override val styleTrans: List[StyleTransform] = List.empty

  override type DataType = T

  override protected val operation = implicitly[WriteableCellOperationAbs[T]]

  /*def setValue(cell: Cell, styleGen: StyleGen): Boolean = {
    val style = styleGen.getCellStyle(styleTrans)
    operation.set(data, Option(cell), style)
  }*/

  override def withTransforms(trans: List[StyleTransform]): CellData[T] = {
    new CellData(data) {
      override val styleTrans = trans
    }
  }

  override def withTransforms(trans: StyleTransform*): CellData[T] = {
    new CellData(data) {
      override val styleTrans = trans.toList
    }
  }

  override def addTransform(tran: List[StyleTransform]): CellData[T] = {
    val thisTrans = this.styleTrans
    new CellData(data) {
      override val styleTrans = thisTrans ::: tran
    }
  }

  override def addTransform(tran: StyleTransform*): CellData[T] = {
    val thisTrans = this.styleTrans
    new CellData(data) {
      override val styleTrans = thisTrans ::: tran.toList
    }
  }

}

object CellData {

  def gen[T](data: T)(implicit operation: WriteableCellOperationAbs[T]): CellData[T] = {
    CellData(Option(data))
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