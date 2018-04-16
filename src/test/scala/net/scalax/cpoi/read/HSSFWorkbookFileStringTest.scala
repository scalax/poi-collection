package net.scalax.cpoi

import java.util.Date

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.{Cell, CellType}
import org.scalatest._

class HSSFWorkbookFileStringTest extends FlatSpec with Matchers {

  "String cell" should "throw exception when setting a numeric cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue("2333.44")
    a[IllegalStateException] should be thrownBy {
      cell.setCellType(CellType.NUMERIC)
    }
  }

  "String cell" should "be set a boolean cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue("2333.44")
    cell.setCellType(CellType.BOOLEAN)
    cell.getBooleanCellValue should be(false)
  }

  "Numberic cell" should "be set a string cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(2333.2233)
    cell.setCellType(CellType.STRING)
    cell.getStringCellValue should be("2333.2233")
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

  "Boolean cell" should "be set a numberic cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(false)
    a[IllegalStateException] should be thrownBy {
      cell.setCellType(CellType.NUMERIC)
    }
  }

  "Date cell" should "be set a boolean cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(new Date())
    cell.setCellType(CellType.BOOLEAN)
    cell.getBooleanCellValue should be(true)
  }

  "Date cell" should "be set a string cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(new Date(0))
    cell.setCellType(CellType.STRING)
    cell.getStringCellValue should be("25569.3333333333")
  }

  "Date cell" should "be set a numeric cell type" in {
    val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")
    val cell = sheet.createRow(0).createCell(0)
    cell.setCellValue(new Date(0))
    cell.setCellType(CellType.NUMERIC)
    cell.getNumericCellValue should be(25569.333333333332d)
  }

}
