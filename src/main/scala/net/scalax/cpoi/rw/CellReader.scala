package net.scalax.cpoi.rw

import cats._
import net.scalax.cpoi.content.PoiCellContent
import net.scalax.cpoi.exception._
import org.apache.poi.ss.usermodel.Cell
import cats.implicits._

trait CellReader[T] {

  def get(cell: Option[Cell]): PoiCellContent.CellReadResult[T]

}

object CellReader {

  implicit def optionCellReaderToNoneOptionCellReader[T: CellReader]
    : CellReader[Option[T]] = {
    implicitly[CellReader[T]].map(Option(_)).recover {
        case _: CellNotExistsException =>
          Option.empty
      }
  }

  implicit val monadError: MonadError[CellReader, CellReaderException] =
    new MonadError[CellReader, CellReaderException]
    with StackSafeMonad[CellReader] {

      override def map[A, B](fa: CellReader[A])(f: A => B): CellReader[B] = {
        new CellReader[B] {
          def get(cell: Option[Cell]): PoiCellContent.CellReadResult[B] = {
            fa.get(cell).map(f)
          }
        }
      }

      override def pure[A](x: A): CellReader[A] = new CellReader[A] {
        def get(cell: Option[Cell]): PoiCellContent.CellReadResult[A] = {
          Right(x)
        }
      }

      override def flatMap[A, B](fa: CellReader[A])(
          f: A => CellReader[B]): CellReader[B] = new CellReader[B] {
        def get(cell: Option[Cell]): PoiCellContent.CellReadResult[B] = {
          fa.get(cell).right.flatMap(s => f(s).get(cell))
        }
      }

      override def raiseError[A](e: CellReaderException): CellReader[A] =
        new CellReader[A] {
          def get(cell: Option[Cell]): PoiCellContent.CellReadResult[A] = {
            Left(e)
          }
        }

      override def handleError[A](fa: CellReader[A])(
          f: CellReaderException => A): CellReader[A] = new CellReader[A] {
        def get(cell: Option[Cell]): PoiCellContent.CellReadResult[A] = {
          fa.get(cell) match {
            case Left(e) =>
              Right(f(e))
            case r @ Right(_) =>
              r
          }
        }
      }

      override def handleErrorWith[A](fa: CellReader[A])(
          f: CellReaderException => CellReader[A]): CellReader[A] =
        new CellReader[A] {
          def get(cell: Option[Cell]): PoiCellContent.CellReadResult[A] = {
            fa.get(cell) match {
              case Left(e) =>
                f(e).get(cell)
              case r @ Right(_) =>
                r
            }
          }
        }
    }

}
