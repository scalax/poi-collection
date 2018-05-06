package net.scalax.cpoi.rw

sealed trait CPoiDone

object CPoiDone {
  val instance: CPoiDone = new CPoiDone {}
}
