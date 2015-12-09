package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.{CellStyle, Cell}

import scala.util.control.Exception._

//写入操作
trait WriteableCellOperation[T] {

  type DataType = T

  //如果被正确写入，则返回 true
  def set(value: Option[T], cell: Option[Cell], style: Option[CellStyle] = None): Boolean

  def typeName: String

}

//读取操作
trait ReadableCellOperation[T] {

  type DataType = T

  def get(cell: Option[Cell]): Option[T]

  def typeName: String

}

//写入和读取操作
trait CellOperation[T] extends WriteableCellOperation[T] with ReadableCellOperation[T] {

  override type DataType = T

  override def typeName: String

}

//CellContent 为基础的写入和读取操作
trait CellContentOperation[T] extends CellOperation[T] with WriteableCellContentOperation[T] with ReadableCellContentOperation[T]

//CellContent 为基础的写入操作
trait WriteableCellContentOperation[T] extends WriteableCellOperation[T] {

  override def set(value: Option[T], cell: Option[Cell], style: Option[CellStyle] = None): Boolean = {
    allCatch.opt {
      for {
        cell1 <- cell
        style1 <- style
      } {
        cell1.setCellStyle(style1)
      }
      for {
        value1 <- value
        cell1 <- cell
      } yield {
        //style.map(style1 => cell1.setCellStyle(style1))
        notNullSet(value1, cell1)
        true
      }
    }.flatten.getOrElse(false)
  }

  def notNullSet(value: T, cell: Cell): Unit

}

//CellContent 为基础的读取操作
trait ReadableCellContentOperation[T] extends ReadableCellOperation[T] {

  def contentGet(content: CellContent): Option[T]

  override def get(cellOpt: Option[Cell]): Option[T] = {
    val content = new CellContent {
      override val poiCell = cellOpt
    }
    contentGet(content)
  }

}