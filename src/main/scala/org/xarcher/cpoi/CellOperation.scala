package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.{CellStyle, Cell}

trait CellWriter[T] {

  def set(value: T, cell: Cell, cellStyle: Option[CellStyle]): Boolean

}

trait CellReader[T] {

  def get(cell: Option[Cell]): PoiCellContent.CellReadResult[T]

}

trait CellFormatter[T] extends CellReader[T] with CellWriter[T] {}
