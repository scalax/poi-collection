package net.scalax.cpoi.content

import net.scalax.cpoi.rw.{CPoiDone, CellWriter}
import net.scalax.cpoi.style.StyleTransform
import org.apache.poi.ss.usermodel.Cell

import scala.util.Try
import scala.collection.compat._

trait CellDataAbs {
  self =>

  type DataType
  val data: DataType

  protected val operation: CellWriter[DataType]

  def untyped: CellDataAbs = self

  val styleTransform: List[StyleTransform]

  def set(cell: Cell): Try[CPoiDone] = {
    operation.setValue(cell, data)
  }

  def withTransforms(trans: List[StyleTransform]): CellDataAbs = {
    implicit val operation1 = self.operation
    CellDataImpl(data, trans)
  }

  def withTransforms(trans: StyleTransform*): CellDataAbs = {
    implicit val operation1 = self.operation
    CellDataImpl(data, trans.to(List))
  }

  def addTransform(tran: List[StyleTransform]): CellDataAbs = {
    implicit val operation1 = self.operation
    val thisTrans           = this.styleTransform
    CellDataImpl(data, thisTrans ::: tran)
  }

  def addTransform(tran: StyleTransform*): CellDataAbs = {
    implicit val operation1 = self.operation
    val thisTrans           = this.styleTransform
    CellDataImpl(data, thisTrans ::: tran.to(List))
  }

}

trait CellData[T] extends CellDataAbs {
  self =>

  override val data: T
  override val styleTransform: List[StyleTransform]

  override type DataType = T

  override protected val operation: CellWriter[T]

  override def untyped: CellDataAbs = self

  override def withTransforms(trans: List[StyleTransform]): CellData[T] = {
    implicit val operation1 = self.operation
    CellDataImpl(data, trans)
  }

  override def withTransforms(trans: StyleTransform*): CellData[T] = {
    implicit val operation1 = self.operation
    CellDataImpl(data, trans.to(List))
  }

  override def addTransform(tran: List[StyleTransform]): CellData[T] = {
    val thisTrans           = this.styleTransform
    implicit val operation1 = self.operation
    CellDataImpl(data, thisTrans ::: tran)
  }

  override def addTransform(tran: StyleTransform*): CellData[T] = {
    val thisTrans           = this.styleTransform
    implicit val operation1 = self.operation
    CellDataImpl(data, thisTrans ::: tran.to(List))
  }

}

case class CellDataImpl[T](override val data: T, override val styleTransform: List[StyleTransform] = List.empty)(
    implicit
  override val operation: CellWriter[T]
) extends CellData[T]
