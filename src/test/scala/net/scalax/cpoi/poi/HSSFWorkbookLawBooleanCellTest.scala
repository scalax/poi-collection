package net.scalax.cpoi.test

import java.util.Date

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.scalatest._

class HSSFWorkbookLawBooleanCellTest extends FlatSpec with Matchers {

  "String cell" should "be set a boolean cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue("2333.44")
    cell.setCellType(CellType.BOOLEAN)
    cell.getBooleanCellValue should be(false)
  }

  "Numberic cell" should "be set a boolean cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(2333.2233)
    cell.setCellType(CellType.BOOLEAN)
    cell.getBooleanCellValue should be(true)

    val cell2 = sheet.createRow(0).createCell(0)
    cell2.setCellValue(-2333.2233)
    cell2.setCellType(CellType.BOOLEAN)
    cell2.getBooleanCellValue should be(true)
  }

  "Date cell" should "be set a boolean cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(new Date())
    cell.setCellType(CellType.BOOLEAN)
    cell.getBooleanCellValue should be(true)
  }

}
