package net.scalax.cpoi.rw

import java.util.Date

trait CellWritersImplicits {

  protected val writers: CellWritersImpl = new CellWritersImpl {}

  implicit val stringWriter: CellWriter[String] = writers.stringWriter

  implicit val doubleWriter: CellWriter[Double] = writers.doubleWriter

  implicit val booleanWriter: CellWriter[Boolean] = writers.booleanWriter

  implicit val dateWriter: CellWriter[Date] = writers.dateWriter

}
