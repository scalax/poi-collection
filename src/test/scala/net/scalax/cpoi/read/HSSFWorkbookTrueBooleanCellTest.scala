package net.scalax.cpoi.test

import java.util.Date

import net.scalax.cpoi.api._
import net.scalax.cpoi.exception.{ExpectDateException, ExpectNumericCellException, ExpectStringCellException}
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.scalatest._

class HSSFWorkbookTrueBooleanCellTest extends FlatSpec with Matchers {

  "boolean cell" should "read as empty string by common string reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet    = workbook.createSheet("Sheet1")
    val row      = sheet.createRow(1)
    val cell     = row.createCell(1)
    cell.setCellValue(true)
    val wrap  = CPoi.wrapCell(cell)
    val value = wrap.tryValue[String]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[ExpectStringCellException] should be(true)
  }

  it should "throw exception when read by double reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet    = workbook.createSheet("Sheet1")
    val row      = sheet.createRow(1)
    val cell     = row.createCell(1)
    cell.setCellValue(true)
    val wrap  = CPoi.wrapCell(cell)
    val value = wrap.tryValue[Double]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[ExpectNumericCellException] should be(true)
  }

  it should "read as boolean by boolean reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet    = workbook.createSheet("Sheet1")
    val row      = sheet.createRow(1)
    val cell     = row.createCell(1)
    cell.setCellValue(true)
    val wrap  = CPoi.wrapCell(cell)
    val value = wrap.tryValue[Boolean]
    value.isRight should be(true)
    value.getOrElse(throw new Exception("Test not pass")) should be(true)
  }

  it should "throw exception when read by date reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet    = workbook.createSheet("Sheet1")
    val row      = sheet.createRow(1)
    val cell     = row.createCell(1)
    cell.setCellValue(true)
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
    cell.setCellValue(true)
    val wrap  = CPoi.wrapCell(cell)
    val value = wrap.tryValue[String]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[ExpectStringCellException] should be(true)
  }

  it should "throw exception when read by non empty string reader" in {
    implicit val ec = readers.nonEmptyStringReader
    val workbook    = new HSSFWorkbook()
    val sheet       = workbook.createSheet("Sheet1")
    val row         = sheet.createRow(1)
    val cell        = row.createCell(1)
    cell.setCellValue(true)
    val wrap  = CPoi.wrapCell(cell)
    val value = wrap.tryValue[String]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[ExpectStringCellException] should be(true)
  }

  it should "throw exception when read by non blank string reader" in {
    implicit val ec = readers.nonBlankStringReader
    val workbook    = new HSSFWorkbook()
    val sheet       = workbook.createSheet("Sheet1")
    val row         = sheet.createRow(1)
    val cell        = row.createCell(1)
    cell.setCellValue(true)
    val wrap  = CPoi.wrapCell(cell)
    val value = wrap.tryValue[String]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[ExpectStringCellException] should be(true)
  }

}
