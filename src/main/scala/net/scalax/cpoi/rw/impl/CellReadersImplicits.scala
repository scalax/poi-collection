package net.scalax.cpoi.rw

trait CellReadersImplicits {

  protected val readers: CellReadersImpl = new CellReadersImpl {}

  implicit lazy val stringReader = readers.stringReader

  implicit lazy val doubleReader = readers.doubleReader

  implicit lazy val booleanReader = readers.booleanReader

  implicit lazy val dateReader = readers.dateReader

}
