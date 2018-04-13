package net.scalax

import net.scalax.cpoi.rw.{CellReadersImplicits, CellWritersImplicits}

package object cpoi {

  val readers: CellReadersImplicits = new CellReadersImplicits {}
  val writers: CellWritersImplicits = new CellWritersImplicits {}

}
