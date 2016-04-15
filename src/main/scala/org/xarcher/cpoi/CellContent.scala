package org.xarcher.cpoi

import java.util.Date

import org.apache.poi.ss.usermodel.{Cell, CellStyle, RichTextString}

import scala.language.existentials
import scala.language.implicitConversions
import scala.util.Try
import scala.util.control.Exception._

trait CellContent {

  val poiCell: Option[Cell]

  lazy val formulaValue: Option[String] =
    Try {
      poiCell.flatMap(s => Option(s.getCellFormula))
    }.getOrElse(None)

  lazy val numericValue: Option[BigDecimal] =
    Try {
      poiCell.flatMap(s => Option(s.getNumericCellValue).map(BigDecimal(_)))
    }.getOrElse(None)

  lazy val doubleValue: Option[Double] =
    Try {
      poiCell.flatMap(s => Option(s.getNumericCellValue))
    }.getOrElse(None)

  lazy val dateValue: Option[Date] =
    Try {
      poiCell.flatMap(s => Option(s.getDateCellValue))
    }.getOrElse(None)

  lazy val richTextStringValue: Option[RichTextString] =
    Try {
      poiCell.flatMap(s => Option(s.getRichStringCellValue))
    }.getOrElse(None)

  lazy val stringValue: Option[String] =
    Try {
      poiCell.flatMap(s => Option(s.getStringCellValue))
    }.getOrElse(None)

  lazy val booleanValue: Option[Boolean] =
    Try {
      poiCell.flatMap(s => Option(s.getBooleanCellValue))
    }.getOrElse(None)

  lazy val errorValue: Option[Byte] =
    Try {
      poiCell.flatMap(s => Option(s.getErrorCellValue))
    }.getOrElse(None)

  lazy val isEmpty: Boolean = {
    poiCell.map(_.getCellType == Cell.CELL_TYPE_BLANK).getOrElse(true)
    //doubleValue == Option(0d) && dateValue == None && stringValue == Option("") && booleanValue == Option(false)
  }

  lazy val isDefined: Boolean = ! isEmpty

  lazy val cellType: Option[Int] = Try(poiCell.map(_.getCellType)).getOrElse(None)
  lazy val cellStyle: Option[CellStyle] = poiCell.map(_.getCellStyle)
  lazy val rowIndex: Option[Int] = poiCell.map(_.getRowIndex)
  lazy val columnIndex: Option[Int] = poiCell.map(_.getColumnIndex)

  def genData[T : WriteableCellOperationAbs : ReadableCellOperationAbs]: CellData[T] = {
    val value = implicitly[ReadableCellOperationAbs[T]].get(poiCell)
    CellData(value)
  }

  def tryValue[T : ReadableCellOperationAbs]: Option[T] = {
    implicitly[ReadableCellOperationAbs[T]].get(poiCell)
  }

}

class CCell(override val poiCell: Option[Cell]) extends CellContent {
}

object CCell {

  def apply(poiCell: Option[Cell]): CCell = new CCell(poiCell)
  def apply(poiCell: Cell): CCell = CCell(Option(poiCell))

}

case class CWorkbook(sheets: Set[CSheet])
case class CSheet(index: Int, name: String, rows: Set[CRow])
case class CRow(index: Int, cells: Set[CCell])