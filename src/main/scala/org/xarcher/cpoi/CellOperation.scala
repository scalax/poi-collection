package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.{CellStyle, Cell}

trait CellWriter[T] {

  def set(cell: Cell, value: T, cellStyle: Option[CellStyle]): Boolean = {
    val success = setValue(cell, value)
    setCellStyle(cell, cellStyle)
    success
  }

  def setValue(cell: Cell, value: T): Boolean

  def setCellStyle(cell: Cell, cellStyle: Option[CellStyle]): Boolean = {
    cellStyle
      .map { style =>
        cell.setCellStyle(style)
        true
      }
      .getOrElse(false)
  }

}

trait CellReader[T] {

  def get(cell: Option[Cell]): PoiCellContent.CellReadResult[T]

}

trait CellFormatter[T] extends CellReader[T] with CellWriter[T] {}
