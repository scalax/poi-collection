package net.scalax.cpoi

import java.util.Date

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.scalatest._

class HSSFWorkbookLawStringCellTest extends FlatSpec with Matchers {

  "Numberic cell" should "be set a string cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(2333.2233)
    cell.setCellType(CellType.STRING)
    cell.getStringCellValue should be("2333.2233")
  }

  "Boolean cell" should "be set a string cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(true)
    cell.setCellType(CellType.STRING)
    cell.getStringCellValue should be("TRUE")

    val cell2 = sheet.createRow(0).createCell(2)
    cell2.setCellValue(false)
    cell2.setCellType(CellType.STRING)
    cell2.getStringCellValue should be("FALSE")
  }

  "Date cell" should "be set a string cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(new Date(0))
    cell.setCellType(CellType.STRING)
    cell.getStringCellValue.startsWith("25569") should be(true)
  }

}
