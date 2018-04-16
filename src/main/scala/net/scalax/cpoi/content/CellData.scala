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
    override val styleTransform: List[StyleTransform] = List.empty)
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
