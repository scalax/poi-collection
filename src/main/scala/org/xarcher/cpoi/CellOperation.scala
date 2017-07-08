package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.{ CellStyle, Cell }

import scala.util.control.Exception._

//写入操作
trait WriteableCellOperationAbs[T] {

  type DataType = T

  //如果被正确写入，则返回 true
  def set(value: Option[T], cell: Option[Cell]): Boolean

}

abstract class WriteableCellOperation[T] extends WriteableCellOperationAbs[T] {

  override type DataType = T

}

//读取操作
trait ReadableCellOperationAbs[T] {

  type DataType = T

  def get(cell: Option[Cell]): Option[T]

}

abstract class ReadableCellOperation[T] extends ReadableCellOperationAbs[T] {

  override type DataType = T

}

//写入和读取操作
trait CellOperationAbs[T] extends WriteableCellOperation[T] with ReadableCellOperationAbs[T] {

  override type DataType = T

}

abstract class CellOperation[T] extends CellOperationAbs[T] {

  override type DataType = T

}

//CellContent 为基础的写入和读取操作
trait CellContentOperationAbs[T] extends CellOperationAbs[T] with WriteableCellContentOperationAbs[T] with ReadableCellContentOperationAbs[T]

abstract class CellContentOperation[T] extends CellContentOperationAbs[T] {

  override type DataType = T

}

//CellContent 为基础的写入操作
trait WriteableCellContentOperationAbs[T] extends WriteableCellOperationAbs[T] {

  override def set(value: Option[T], cell: Option[Cell]): Boolean = {
    allCatch.opt {
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

abstract class WriteableCellContentOperation[T] extends WriteableCellContentOperationAbs[T] {

  override type DataType = T

}

//CellContent 为基础的读取操作
trait ReadableCellContentOperationAbs[T] extends ReadableCellOperationAbs[T] {

  def contentGet(content: CellContent): Option[T]

  override def get(cellOpt: Option[Cell]): Option[T] = {
    val content = new CellContent {
      override val poiCell = cellOpt
    }
    contentGet(content)
  }

}

abstract class ReadableCellContentOperation[T] extends ReadableCellContentOperationAbs[T] {

  override type DataType = T

}