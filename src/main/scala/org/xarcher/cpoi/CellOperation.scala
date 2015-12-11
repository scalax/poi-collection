package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.{CellStyle, Cell}

import scala.util.control.Exception._
import scala.reflect.runtime.universe._

//写入操作
trait WriteableCellOperationAbs[T] {

  type DataType = T

  //如果被正确写入，则返回 true
  def set(value: Option[T], cell: Option[Cell], style: Option[CellStyle] = None): Boolean

  val typeTag: WeakTypeTag[DataType]

}

abstract class WriteableCellOperation[T : WeakTypeTag] extends WriteableCellOperationAbs[T] {

  override type DataType = T

  override val typeTag: WeakTypeTag[DataType] = implicitly[WeakTypeTag[T]]

}

//读取操作
trait ReadableCellOperationAbs[T] {

  type DataType = T

  def get(cell: Option[Cell]): Option[T]

  val typeTag: WeakTypeTag[DataType]

}

abstract class ReadableCellOperation[T : WeakTypeTag] extends ReadableCellOperationAbs[T] {

  override type DataType = T

  override val typeTag: WeakTypeTag[DataType] = implicitly[WeakTypeTag[T]]

}

//写入和读取操作
trait CellOperationAbs[T] extends WriteableCellOperation[T] with ReadableCellOperationAbs[T] {

  override type DataType = T

  override val typeTag: WeakTypeTag[DataType]

}

abstract class CellOperation[T : WeakTypeTag] extends CellOperationAbs[T] {

  override type DataType = T

  override val typeTag: WeakTypeTag[DataType] = implicitly[WeakTypeTag[T]]

}

//CellContent 为基础的写入和读取操作
trait CellContentOperationAbs[T] extends CellOperationAbs[T] with WriteableCellContentOperationAbs[T] with ReadableCellContentOperationAbs[T]

abstract class CellContentOperation[T : WeakTypeTag] extends CellContentOperationAbs[T] {

  override type DataType = T

  override val typeTag: WeakTypeTag[DataType] = implicitly[WeakTypeTag[T]]

}

//CellContent 为基础的写入操作
trait WriteableCellContentOperationAbs[T] extends WriteableCellOperationAbs[T] {

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

abstract class WriteableCellContentOperation[T : WeakTypeTag] extends WriteableCellContentOperationAbs[T] {

  override type DataType = T

  override val typeTag: WeakTypeTag[DataType] = implicitly[WeakTypeTag[T]]

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

abstract class ReadableCellContentOperation[T : WeakTypeTag] extends ReadableCellContentOperationAbs[T] {

  override type DataType = T

  override val typeTag: WeakTypeTag[DataType] = implicitly[WeakTypeTag[T]]

}