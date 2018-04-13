package net.scalax.cpoi.rw

trait CellWritersImplicits {

  protected val writers: CellWritersImpl = new CellWritersImpl {}

  implicit val stringWriter = writers.stringWriter

  implicit val doubleWriter = writers.doubleWriter

  implicit val booleanWriter = writers.booleanWriter

  implicit val dateWriter = writers.dateWriter

}
