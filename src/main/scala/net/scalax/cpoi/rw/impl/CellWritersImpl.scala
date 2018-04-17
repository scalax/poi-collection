package net.scalax.cpoi.rw

import java.util.Date

import org.apache.poi.ss.usermodel.Cell

import scala.util.Try

trait CellWritersImpl {

  val stringWriter: CellWriter[String] = new CellWriter[String] {
    override def setValue(cell: Cell, value: String): Try[Boolean] = {
      Try {
        cell.setCellValue(value)
        true
      }
    }
  }

  val doubleWriter: CellWriter[Double] = new CellWriter[Double] {
    override def setValue(cell: Cell, value: Double): Try[Boolean] = {
      Try {
        cell.setCellValue(value)
        true
      }
    }
  }

  val booleanWriter: CellWriter[Boolean] = new CellWriter[Boolean] {
    override def setValue(cell: Cell, value: Boolean): Try[Boolean] = {
      Try {
        cell.setCellValue(value)
        true
      }
    }
  }

  val dateWriter: CellWriter[Date] = new CellWriter[Date] {
    override def setValue(cell: Cell, value: Date): Try[Boolean] = {
      Try {
        cell.setCellValue(value)
        true
      }
    }
  }

}
