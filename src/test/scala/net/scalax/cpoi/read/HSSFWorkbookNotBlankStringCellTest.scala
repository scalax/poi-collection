package net.scalax.cpoi

import java.util.Date

import net.scalax.cpoi.exception.{
  CellNotExistsException,
  ExpectBooleanCellException,
  ExpectDateException,
  ExpectNumericCellException
}
import net.scalax.cpoi.style.CPoiUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.scalatest._

class HSSFWorkbookNotBlankStringCellTest extends FlatSpec with Matchers {

  "not blank string cell" should "read as empty string by common string reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue("-123        ")
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[String]
    value.isRight should be(true)
    value.right.get should be("-123        ")
  }

  it should "throw exception when read by double reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue("-123        ")
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[Double]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[ExpectNumericCellException] should be(true)
  }

  it should "throw exception when read by boolean reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue("-123        ")
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[Boolean]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[ExpectBooleanCellException] should be(true)
  }

  it should "throw exception when read by date reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue("-123        ")
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[Date]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[ExpectDateException] should be(true)
  }

  it should "read as empty string by immutable string reader" in {
    import immutableReaders._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue("-123        ")
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[String]
    value.isRight should be(true)
    value.right.get should be("-123        ")
  }

  it should "read as string by non empty string reader" in {
    implicit val ec = readers.nonEmptyStringReader
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue("-123        ")
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[String]
    value.isRight should be(true)
    value.right.get should be("-123        ")
  }

  it should "read as trim string by non blank string reader" in {
    implicit val ec = readers.nonBlankStringReader
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue("-123        ")
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[String]
    value.isRight should be(true)
    value.right.get should be("-123")
  }

}
