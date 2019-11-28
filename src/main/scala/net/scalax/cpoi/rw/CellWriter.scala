package net.scalax.cpoi.rw

import cats.Contravariant
import org.apache.poi.ss.usermodel.Cell

import scala.util.Try

trait CellWriter[T] {
  def setValue(cell: Cell, value: T): Try[CPoiDone]
}

object CellWriter {

  implicit def optionCellOperationToNoneOptionCellOpreation[T: CellWriter]: CellWriter[Option[T]] = {
    new CellWriter[Option[T]] {
      override def setValue(cell: Cell, value: Option[T]): Try[CPoiDone] = {
        value
          .map { v =>
            implicitly[CellWriter[T]].setValue(cell, v)
          }
          .getOrElse(Try { CPoiDone.instance })
      }
    }
  }

  implicit val contravariant: Contravariant[CellWriter] = {
    new Contravariant[CellWriter] {
      override def contramap[A, B](fa: CellWriter[A])(f: B => A): CellWriter[B] = {
        new CellWriter[B] {
          override def setValue(cell: Cell, value: B): Try[CPoiDone] = {
            Try { f(value) }.flatMap(s => fa.setValue(cell, s))
          }
        }
      }
    }
  }

}
