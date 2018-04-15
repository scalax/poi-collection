package net.scalax.cpoi.rw

import org.apache.poi.ss.usermodel.Cell

trait CellWriter[T] {
  def setValue(cell: Cell, value: T): Boolean
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
