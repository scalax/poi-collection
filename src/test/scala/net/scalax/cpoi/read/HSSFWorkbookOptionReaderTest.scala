package net.scalax.cpoi.test

import net.scalax.cpoi._
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.scalatest._

class HSSFWorkbookOptionReaderTest extends FlatSpec with Matchers {

  "Non empty string cell" should "read as option string reader" in {
    import readers._

    val testBlankStr = "    " * 100
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testBlankStr)
    val ccell = CPoiUtils.wrapCell(cell)
    val value = ccell.tryValue[Option[String]]
    value.isRight should be(true)
    value.right.get should be(Option(testBlankStr))
  }

  "Blank string cell" should "read as None by non blank string reader" in {
    implicit val strReader = readers.nonBlankStringReader

    val testBlankStr = "    " * 100
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testBlankStr)
    val ccell = CPoiUtils.wrapCell(cell)
    val value = ccell.tryValue[Option[String]]
    value.isRight should be(true)
    value.right.get should be(Option.empty)
  }

}
