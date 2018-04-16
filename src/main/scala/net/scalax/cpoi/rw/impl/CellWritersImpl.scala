package net.scalax.cpoi.rw

import java.util.Date

import org.apache.poi.ss.usermodel.Cell

import scala.util.Try

trait CellWritersImpl {

  val stringWriter = new CellWriter[String] {
    override def setValue(cell: Cell, value: String): Try[Boolean] = {
      Try {
        cell.setCellValue(value)
        true
      }
    }
  }

  val doubleWriter = new CellWriter[Double] {
    override def setValue(cell: Cell, value: Double): Try[Boolean] = {
      Try {
        cell.setCellValue(value)
        true
      }
    }
  }

  val booleanWriter = new CellWriter[Boolean] {
    override def setValue(cell: Cell, value: Boolean): Try[Boolean] = {
      Try {
        cell.setCellValue(value)
        true
      }
    }
  }

  val dateWriter = new CellWriter[Date] {
    override def setValue(cell: Cell, value: Date): Try[Boolean] = {
      Try {
        cell.setCellValue(value)
        true
      }
    }
  }

}
