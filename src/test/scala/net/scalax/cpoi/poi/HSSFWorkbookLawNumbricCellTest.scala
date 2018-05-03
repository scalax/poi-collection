package net.scalax.cpoi

import java.util.Date

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.scalatest._

class HSSFWorkbookLawNumbricCellTest extends FlatSpec with Matchers {

  "String cell" should "throw exception when setting a numeric cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue("2333.44")
    a[IllegalStateException] should be thrownBy {
      cell.setCellType(CellType.NUMERIC)
    }
  }

  "Boolean cell" should "be set a numberic cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(false)
    a[IllegalStateException] should be thrownBy {
      cell.setCellType(CellType.NUMERIC)
    }
  }

  "Date cell" should "be set a numeric cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(new Date(0))
    cell.setCellType(CellType.NUMERIC)
    cell.getNumericCellValue.toInt should be(25569)
    cell.getDateCellValue should be(new Date(0))
  }

}
