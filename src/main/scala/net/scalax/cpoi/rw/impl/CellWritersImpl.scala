package net.scalax.cpoi.rw

import java.util.Date

import org.apache.poi.ss.usermodel.Cell

import scala.util.Try

trait CellWritersImpl {

  val stringWriter = new CellWriter[String] {
    override def setValue(cell: Cell, value: String): Boolean = {
      Try {
        cell.setCellValue(value)
      }.map((_: Unit) => true).getOrElse(false)
    }
  }

  val doubleWriter = new CellWriter[Double] {
    override def setValue(cell: Cell, value: Double): Boolean = {
      Try {
        cell.setCellValue(value)
      }.map((_: Unit) => true)
        .getOrElse(false)
    }
  }

  val booleanWriter = new CellWriter[Boolean] {
    override def setValue(cell: Cell, value: Boolean): Boolean = {
      Try {
        cell.setCellValue(value)
      }.map((_: Unit) => true)
        .getOrElse(false)
    }
  }

  val dateWriter = new CellWriter[Date] {
    override def setValue(cell: Cell, value: Date): Boolean = {
      Try {
        cell.setCellValue(value)
      }.map((_: Unit) => true).getOrElse(false)
    }
  }

}
