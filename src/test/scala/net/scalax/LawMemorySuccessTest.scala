package org.xarcher.cpoi

import java.util.Date

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.scalatest._

class HSSFWorkbookLawMemoryTest extends FlatSpec with Matchers {

  object PoiOperations extends PoiOperations

  import PoiOperations._

  "HSSFWorkbook's cell" should "read as string" in {
    val testUtf8Str = "Test cell str（一些 utf-8 测试字符）"
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testUtf8Str)
    val ccell = CCell(cell)
    val value = ccell.stringValue
    value.isRight should be(true)
    value.right.get should be(testUtf8Str)
  }

  it should "read as numbric" in {
    val testDouble = 23467894.489643415d
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testDouble)
    val ccell = CCell(cell)
    val value = ccell.doubleValue
    value.isRight should be(true)
    value.right.get should be(testDouble)

    val largerDouble = testDouble + 0.00001d
    value.right.get should not be largerDouble
  }

  it should "read as boolean" in {
    val testBoolean1 = true
    val testBoolean2 = false
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell1 = row.createCell(4)
    val cell2 = row.createCell(6)
    cell1.setCellValue(testBoolean1)
    cell2.setCellValue(testBoolean2)
    val ccell1 = CCell(cell1)
    val ccell2 = CCell(cell2)
    val value1 = ccell1.booleanValue
    val value2 = ccell2.booleanValue
    value1.isRight should be(true)
    value2.isRight should be(true)

    value1.right.get should be(testBoolean1)
    value2.right.get should be(testBoolean2)
  }

  it should "read as date" in {
    val testDate = new Date()
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(testDate)
    val ccell = CCell(cell)
    val value = ccell.dateValue
    value.isRight should be(true)
    value.right.get should be(testDate)

    val laterDate = new Date(new Date().getTime + 120L)
    value.right.get should not be laterDate
  }

}
