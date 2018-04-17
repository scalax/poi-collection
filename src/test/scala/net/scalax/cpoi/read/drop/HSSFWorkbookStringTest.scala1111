package net.scalax.cpoi

import net.scalax.cpoi.exception.CellNotExistsException
import net.scalax.cpoi.rw.CellReadersImplicits
import net.scalax.cpoi.style.CPoiUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.scalatest._

class HSSFWorkbookStringTest extends FlatSpec with Matchers {

  val cusReaders: CellReadersImplicits = new CellReadersImplicits {
    self =>
    override lazy val stringReader = self.readers.nonEmptyStringReader
  }

  "Custom string reader" should "throw exception when read an blank string" in {
    import cusReaders._

    val testBlankStr = "    " * 100
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testBlankStr)
    val ccell = CPoiUtils.wrapCell(cell)

    val value = ccell.tryValue[String]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  "Custom string reader" should "read as None when read an blank string" in {
    import cusReaders._

    val testBlankStr = "    " * 100
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testBlankStr)
    val ccell = CPoiUtils.wrapCell(cell)

    val value1 = ccell.tryValue[Option[String]]
    value1.isRight should be(true)
    value1.right.get should be(Option.empty)
  }

  "Custom string reader" should "throw exception when read an null cell" in {
    import cusReaders._

    val ccell = CPoiUtils.wrapCell(Option.empty)

    val value = ccell.tryValue[String]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  "Custom string reader" should "read as None when read an null cell" in {
    import cusReaders._

    val ccell = CPoiUtils.wrapCell(Option.empty)

    val value = ccell.tryValue[Option[String]]
    value.isRight should be(true)
    value.right.get should be(Option.empty)
  }

  "Custom string reader" should "throw exception when read an empty string" in {
    import cusReaders._

    val testEmptyStr = ""
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testEmptyStr)
    val ccell = CPoiUtils.wrapCell(cell)

    val value = ccell.tryValue[String]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  "Custom string reader" should "read as None when read an empty string" in {
    import cusReaders._

    val testEmptyStr = ""
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testEmptyStr)
    val ccell = CPoiUtils.wrapCell(cell)

    val value1 = ccell.tryValue[Option[String]]
    value1.isRight should be(true)
    value1.right.get should be(Option.empty)
  }

  "Default string reader" should "read blank string from cell" in {
    import readers._

    val testBlankStr = "    " * 100
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testBlankStr)
    val ccell = CPoiUtils.wrapCell(cell)

    val value = ccell.tryValue[String]
    value.isRight should be(true)
    value.right.get should be(testBlankStr)
  }

  "Default string reader" should "read empty string from cell" in {
    import readers._

    val testEmptyStr = ""
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testEmptyStr)
    val ccell = CPoiUtils.wrapCell(cell)

    val value1 = ccell.tryValue[String]
    value1.isRight should be(true)
    value1.right.get should be(testEmptyStr)
  }

}
