package net.scalax.cpoi.rw

import java.util.Date

import org.apache.poi.ss.usermodel.Cell

import scala.util.{Failure, Success, Try}

trait CellWritersImpl {

  val stringWriter = new CellWriter[String] {
    override def setValue(cell: Cell, value: String): Boolean = {
      Try {
        cell.setCellValue(value)
      } match {
        case Success(_: Unit) =>
          true
        case Failure(e) =>
          e.printStackTrace
          false
      }
    }
  }

  val doubleWriter = new CellWriter[Double] {
    override def setValue(cell: Cell, value: Double): Boolean = {
      Try {
        cell.setCellValue(value)
      } match {
        case Success(_: Unit) =>
          true
        case Failure(e) =>
          e.printStackTrace
          false
      }
    }
  }

  val booleanWriter = new CellWriter[Boolean] {
    override def setValue(cell: Cell, value: Boolean): Boolean = {
      Try {
        cell.setCellValue(value)
      } match {
        case Success(_: Unit) =>
          true
        case Failure(e) =>
          e.printStackTrace
          false
      }
    }
  }

  val dateWriter = new CellWriter[Date] {
    override def setValue(cell: Cell, value: Date): Boolean = {
      Try {
        cell.setCellValue(value)
      } match {
        case Success(_: Unit) =>
          true
        case Failure(e) =>
          e.printStackTrace
          false
      }
    }
  }

}
