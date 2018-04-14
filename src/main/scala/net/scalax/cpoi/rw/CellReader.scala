package net.scalax.cpoi.rw

import cats._
import net.scalax.cpoi.content.PoiCellContent
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

  implicit val functor = new Functor[CellReader] {
    override def map[A, B](fa: CellReader[A])(f: A => B): CellReader[B] = {
      new CellReader[B] {
        def get(cell: Option[Cell]): PoiCellContent.CellReadResult[B] = {
          fa.get(cell).map(f)
        }
      }
    }
  }

}
