package net.scalax.cpoi.test

import java.util.Date

import net.scalax.cpoi.api._
import net.scalax.cpoi.exception.{ExpectBooleanCellException, ExpectDateException, ExpectStringCellException}
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.scalatest._

class HSSFWorkbookNumbericCellTest2 extends FlatSpec with Matchers {

  "numberic cell" should "read as empty string by common string reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet    = workbook.createSheet("Sheet1")
    val row      = sheet.createRow(1)
    val cell     = row.createCell(1)
    cell.setCellValue(-123.321)
    val wrap = CPoi.wrapCell(cell)
    wrap.cellType should be(Option(CellType.NUMERIC))
    val value = wrap.tryValue[String]
    wrap.cellType should be(Option(CellType.STRING))
    value.isRight should be(true)
    value.getOrElse(throw new Exception("Test not pass")) should be("-123.321")
  }

  it should "read as double by double reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet    = workbook.createSheet("Sheet1")
    val row      = sheet.createRow(1)
    val cell     = row.createCell(1)
    cell.setCellValue(-123.321)
    val wrap  = CPoi.wrapCell(cell)
    val value = wrap.tryValue[Double]
    value.isRight should be(true)
    value.getOrElse(throw new Exception("Test not pass")) should be(-123.321)
  }

  it should "throw exception when read by boolean reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet    = workbook.createSheet("Sheet1")
    val row      = sheet.createRow(1)
    val cell     = row.createCell(1)
    cell.setCellValue(-123.321)
    val wrap  = CPoi.wrapCell(cell)
    val value = wrap.tryValue[Boolean]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[ExpectBooleanCellException] should be(true)
  }

  it should "read as date when read by date reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet    = workbook.createSheet("Sheet1")
    val row      = sheet.createRow(1)
    val cell     = row.createCell(1)
    cell.setCellValue(-123.321)
    val wrap  = CPoi.wrapCell(cell)
    val value = wrap.tryValue[Date]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[ExpectDateException] should be(true)
  }

  it should "throw exception when read by immutable string reader" in {
    import immutableReaders._
    val workbook = new HSSFWorkbook()
    val sheet    = workbook.createSheet("Sheet1")
    val row      = sheet.createRow(1)
    val cell     = row.createCell(1)
    cell.setCellValue(-123.321)
    val wrap = CPoi.wrapCell(cell)
    wrap.cellType should be(Option(CellType.NUMERIC))
    val value = wrap.tryValue[String]
    wrap.cellType should be(Option(CellType.NUMERIC))
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[ExpectStringCellException] should be(true)
  }

  it should "read as string by non empty string reader" in {
    implicit val ec = readers.nonEmptyStringReader
    val workbook    = new HSSFWorkbook()
    val sheet       = workbook.createSheet("Sheet1")
    val row         = sheet.createRow(1)
    val cell        = row.createCell(1)
    cell.setCellValue(-123.321)
    val wrap = CPoi.wrapCell(cell)
    wrap.cellType should be(Option(CellType.NUMERIC))
    val value = wrap.tryValue[String]
    wrap.cellType should be(Option(CellType.STRING))
    value.isRight should be(true)
    value.getOrElse(throw new Exception("Test not pass")) should be("-123.321")
  }

  it should "read as trim string by non blank string reader" in {
    implicit val ec = readers.nonBlankStringReader
    val workbook    = new HSSFWorkbook()
    val sheet       = workbook.createSheet("Sheet1")
    val row         = sheet.createRow(1)
    val cell        = row.createCell(1)
    cell.setCellValue(-123.321)
    val wrap = CPoi.wrapCell(cell)
    wrap.cellType should be(Option(CellType.NUMERIC))
    val value = wrap.tryValue[String]
    wrap.cellType should be(Option(CellType.STRING))
    value.isRight should be(true)
    value.getOrElse(throw new Exception("Test not pass")) should be("-123.321")
  }

}
