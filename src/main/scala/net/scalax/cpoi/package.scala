package net.scalax

import net.scalax.cpoi.exception.CellReaderException
import net.scalax.cpoi.rw.{
  CellReadersImplicits,
  CellWritersImplicits,
  ImmutableCellReadersImplicits
}

package object cpoi {

  val readers: CellReadersImplicits = new CellReadersImplicits {}
  val immutableReaders: ImmutableCellReadersImplicits =
    new ImmutableCellReadersImplicits {}
  val writers: CellWritersImplicits = new CellWritersImplicits {}

  type CellReadResult[R] = Either[CellReaderException, R]

}
