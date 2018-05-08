package net.scalax.cpoi.test

import java.util.Date

import net.scalax.cpoi.exception.CellNotExistsException
import net.scalax.cpoi.api._
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.scalatest._

class HSSFWorkbookBlankCellTest extends FlatSpec with Matchers {

  "blank cell" should "read as empty string by common string reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    val wrap = CPoi.wrapCell(cell)
    val value = wrap.tryValue[String]
    wrap.isBlank should be(true)
    value.isRight should be(true)
    value.right.get should be("")
  }

  it should "throw exception when read by double reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    val wrap = CPoi.wrapCell(cell)
    val value = wrap.tryValue[Double]
    wrap.isBlank should be(true)
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read by boolean reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    val wrap = CPoi.wrapCell(cell)
    val value = wrap.tryValue[Boolean]
    wrap.isBlank should be(true)
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read by date reader" in {
    import readers._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    val wrap = CPoi.wrapCell(cell)
    val value = wrap.tryValue[Date]
    wrap.isBlank should be(true)
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "read as empty string by immutable string reader" in {
    import immutableReaders._
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    val wrap = CPoi.wrapCell(cell)
    val value = wrap.tryValue[String]
    wrap.isBlank should be(true)
    value.isRight should be(true)
    value.right.get should be("")
  }

  it should "throw exception when read by non empty string reader" in {
    implicit val ec = readers.nonEmptyStringReader
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    val wrap = CPoi.wrapCell(cell)
    val value = wrap.tryValue[String]
    wrap.isBlank should be(true)
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read by non blank string reader" in {
    implicit val ec = readers.nonBlankStringReader
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val row = sheet.createRow(1)
    val cell = row.createCell(1)
    val wrap = CPoi.wrapCell(cell)
    val value = wrap.tryValue[String]
    wrap.isBlank should be(true)
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

}
