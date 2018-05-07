package net.scalax.cpoi.utils

import net.scalax.cpoi.rw.{
  CellReadersImplicits,
  CellWritersImplicits,
  ImmutableCellReadersImplicits
}

trait Alias {

  val CPoiUtils: CPoiUtils = new CPoiUtils {}

  val readers: CellReadersImplicits = new CellReadersImplicits {}
  val immutableReaders: ImmutableCellReadersImplicits =
    new ImmutableCellReadersImplicits {}
  val writers: CellWritersImplicits = new CellWritersImplicits {}

  type MutableStyleGen = net.scalax.cpoi.style.MutableStyleGen
  type StyleGen = net.scalax.cpoi.style.StyleGen
  type StyleTransform = net.scalax.cpoi.style.StyleTransform

  type CPoiDone = net.scalax.cpoi.rw.CPoiDone
  val CPoiDone: CPoiDone = net.scalax.cpoi.rw.CPoiDone.instance

  type CellWriter[T] = net.scalax.cpoi.rw.CellWriter[T]
  type CellReader[T] = net.scalax.cpoi.rw.CellReader[T]

  type CellContentAbs = net.scalax.cpoi.content.CellContentAbs
  type CellDataAbs = net.scalax.cpoi.content.CellDataAbs
  type CellData[T] = net.scalax.cpoi.content.CellData[T]

  type CellReaderException = net.scalax.cpoi.exception.CellReaderException
  type CellReadResult[T] = net.scalax.cpoi.rw.CellReader.CellReadResult[T]

}