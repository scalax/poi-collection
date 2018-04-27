package net.scalax.cpoi.content

import net.scalax.cpoi.rw.CellWriter
import net.scalax.cpoi.style.StyleTransform
import org.apache.poi.ss.usermodel.Cell

import scala.util.Try

trait CellDataAbs {
  self =>

  type DataType
  val data: DataType

  protected val operation: CellWriter[DataType]

  val styleTransform: List[StyleTransform]

  def set(cell: Cell): Try[Boolean] = {
    operation.setValue(cell, data)
  }

  def withTransforms(trans: List[StyleTransform]): CellDataAbs = {
    implicit val operation1 = self.operation
    CellData.gen(data, trans)
  }

  def withTransforms(trans: StyleTransform*): CellDataAbs = {
    implicit val operation1 = self.operation
    CellData.gen(data, trans.toList)
  }

  def addTransform(tran: List[StyleTransform]): CellDataAbs = {
    implicit val operation1 = self.operation
    val thisTrans = this.styleTransform
    CellData.gen(data, thisTrans ::: tran)
  }

  def addTransform(tran: StyleTransform*): CellDataAbs = {
    implicit val operation1 = self.operation
    val thisTrans = this.styleTransform
    CellData.gen(data, thisTrans ::: tran.toList)
  }

}

trait CellData[T] extends CellDataAbs {
  self =>

  override val data: T
  override val styleTransform: List[StyleTransform]

  override type DataType = T

  override protected val operation: CellWriter[T]

  override def withTransforms(trans: List[StyleTransform]): CellData[T] = {
    implicit val operation1 = self.operation
    CellData.gen(data, trans)
  }

  override def withTransforms(trans: StyleTransform*): CellData[T] = {
    implicit val operation1 = self.operation
    CellData.gen(data, trans.toList)
  }

  override def addTransform(tran: List[StyleTransform]): CellData[T] = {
    val thisTrans = this.styleTransform
    implicit val operation1 = self.operation
    CellData.gen(data, thisTrans ::: tran)
  }

  override def addTransform(tran: StyleTransform*): CellData[T] = {
    val thisTrans = this.styleTransform
    implicit val operation1 = self.operation
    CellData.gen(data, thisTrans ::: tran.toList)
  }

}

object CellData {

  def gen[T](data: T, styleTransform: List[StyleTransform])(
      implicit operation: CellWriter[T]): CellData[T] = {
    val data1 = data
    val styleTransform1 = styleTransform
    val operation1 = operation
    new CellData[T] {
      override val data = data1
      override val styleTransform = styleTransform1
      override protected val operation = operation1
    }
  }

}
