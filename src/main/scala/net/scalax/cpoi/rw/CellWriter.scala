package net.scalax.cpoi.rw

import org.apache.poi.ss.usermodel.{Cell, CellStyle}

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

object CellWriter {

  implicit def optionCellOperationToNoneOptionCellOpreation[T: CellWriter]
    : CellWriter[Option[T]] = {
    new CellWriter[Option[T]] {

      override def setValue(cell: Cell, value: Option[T]): Boolean = {
        value
          .map { v =>
            implicitly[CellWriter[T]].setValue(cell, v)
          }
          .getOrElse(true)
      }

    }
  }

}
