package net.scalax.cpoi.rw

import java.util.Date

trait CellReadersImplicits {

  protected val readers: CellReadersImpl = new CellReadersImpl {}

  lazy val nonEmptyStringReader: CellReader[String] = readers.nonEmptyStringReader

  lazy val nonBlankStringReader: CellReader[String] = readers.nonBlankStringReader

  implicit lazy val stringReader: CellReader[String] = readers.stringReader

  implicit lazy val doubleReader: CellReader[Double] = readers.doubleReader

  implicit lazy val booleanReader: CellReader[Boolean] = readers.booleanReader

  implicit lazy val dateReader: CellReader[Date] = readers.dateReader

}
