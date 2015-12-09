package org.xarcher.cpoi

import java.util.Date

import org.apache.poi.ss.usermodel.{Cell, CellStyle, RichTextString}

import scala.language.existentials
import scala.language.implicitConversions
import scala.util.control.Exception._

trait CellContent {

  val poiCell: Option[Cell]

  lazy val formulaValue: Option[String] = allCatch.opt(poiCell.map(_.getCellFormula)).flatten

  lazy val numericValue: Option[BigDecimal] = allCatch.opt(poiCell.map(s => BigDecimal(s.getNumericCellValue))).flatten

  lazy val dateValue: Option[Date] = allCatch.opt(poiCell.map(_.getDateCellValue)).flatten

  lazy val richTextStringValue: Option[RichTextString] = allCatch.opt(poiCell.map(_.getRichStringCellValue)).flatten

  lazy val stringValue: Option[String] = allCatch.opt(poiCell.map(_.getStringCellValue)).flatten

  lazy val booleanValue: Option[Boolean] = allCatch.opt(poiCell.map(_.getBooleanCellValue)).flatten

  lazy val errorValue: Option[Byte] = allCatch.opt(poiCell.map(_.getErrorCellValue)).flatten

  lazy val cellType: Option[Int] = allCatch.opt(poiCell.map(_.getCellType)).flatten
  lazy val cellStyle: Option[CellStyle] = poiCell.map(_.getCellStyle)
  lazy val rowIndex: Option[Int] = poiCell.map(_.getRowIndex)
  lazy val columnIndex: Option[Int] = poiCell.map(_.getColumnIndex)

  def genData[T : WriteableCellOperation : ReadableCellOperation]: CellData[T] = {
    val value = implicitly[ReadableCellOperation[T]].get(poiCell)
    CellData(value)
  }

  def tryValue[T : ReadableCellOperation]: Option[T] = {
    implicitly[ReadableCellOperation[T]].get(poiCell)
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