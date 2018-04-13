package net.scalax.cpoi.rw

import net.scalax.cpoi.PoiCellContent
import net.scalax.cpoi.exception._
import org.apache.poi.ss.usermodel.Cell

trait CellReader[T] {

  def get(cell: Option[Cell]): PoiCellContent.CellReadResult[T]

}

object CellReader {

  implicit def optionCellReaderToNoneOptionCellReader[T: CellReader]
    : CellReader[Option[T]] = {
    new CellReader[Option[T]] {

      override def get(
          cellOpt: Option[Cell]): PoiCellContent.CellReadResult[Option[T]] = {
        implicitly[CellReader[T]]
          .get(cellOpt) match {
          case Right(v) =>
            Right(Option(v))
          case Left(_: CellReaderException) =>
            Right(Option.empty)
        }
      }

    }
  }

}
