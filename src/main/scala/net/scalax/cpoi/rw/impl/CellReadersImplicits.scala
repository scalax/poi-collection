package net.scalax.cpoi.rw

trait CellReadersImplicits {

  protected val readers: CellReadersImpl = new CellReadersImpl {}

  implicit val stringReader = readers.stringReader

  implicit val doubleReader = readers.doubleReader

  implicit val booleanReader = readers.booleanReader

  implicit val dateReader = readers.dateReader

}
