package net.scalax.cpoi

import java.util.Date

import net.scalax.cpoi.exception._
import net.scalax.cpoi.style.CPoiUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.scalatest._

class HSSFWorkbookNumbericCellTest1 extends FlatSpec with Matchers {

  "numberic cell" should "read as empty string by common string reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue(123.321)
    val wrap = CPoiUtils.wrapCell(cell)
    wrap.cellType should be(Option(CellType.NUMERIC))
    val value = wrap.tryValue[String]
    wrap.cellType should be(Option(CellType.STRING))
    value.isRight should be(true)
    value.right.get should be("123.321")
  }

  it should "read as double by double reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue(123.321)
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[Double]
    value.isRight should be(true)
    value.right.get should be(123.321)
  }

  it should "throw exception when read by boolean reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue(123.321)
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[Boolean]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[ExpectBooleanCellException] should be(true)
  }

  it should "read as date when read by date reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue(123.321)
    val wrap = CPoiUtils.wrapCell(cell)
    val value = wrap.tryValue[Date]
    value.isRight should be(true)
    value.right.get should be(new Date(-2198535808600L))
  }

  it should "throw exception when read by immutable string reader" in {
    import immutableReaders._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue(123.321)
    val wrap = CPoiUtils.wrapCell(cell)
    wrap.cellType should be(Option(CellType.NUMERIC))
    val value = wrap.tryValue[String]
    wrap.cellType should be(Option(CellType.NUMERIC))
    value.isLeft should be(true)
    value.left.get.isInstanceOf[ExpectStringCellException] should be(true)
  }

  it should "read as string by non empty string reader" in {
    implicit val ec = readers.nonEmptyStringReader
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue(123.321)
    val wrap = CPoiUtils.wrapCell(cell)
    wrap.cellType should be(Option(CellType.NUMERIC))
    val value = wrap.tryValue[String]
    wrap.cellType should be(Option(CellType.STRING))
    value.isRight should be(true)
    value.right.get should be("123.321")
  }

  it should "read as trim string by non blank string reader" in {
    implicit val ec = readers.nonBlankStringReader
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    cell.setCellValue(123.321)
    val wrap = CPoiUtils.wrapCell(cell)
    wrap.cellType should be(Option(CellType.NUMERIC))
    val value = wrap.tryValue[String]
    wrap.cellType should be(Option(CellType.STRING))
    value.isRight should be(true)
    value.right.get should be("123.321")
  }

}
