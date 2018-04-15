package net.scalax.cpoi

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.{Cell, CellType}
import org.scalatest._

class HSSFWorkbookFileStringTest extends FlatSpec with Matchers {

  "CellContent" should "throw exception when read an empty cell as string" in {
    val workbook = new HSSFWorkbook(getClass.getClassLoader.getResourceAsStream("test01.xls"))
    val sheet = workbook.getSheet("Sheet1")
    println("11" * 100)

    val cell = sheet.getRow(6).getCell(0)

    val cloneCell1 = cell.clone().asInstanceOf[Cell]

    cloneCell1.setCellType(CellType.STRING)
    println(cloneCell1.getStringCellValue)

    val cloneCell12 = cell.clone().asInstanceOf[Cell]

    cloneCell12.setCellType(CellType.NUMERIC)
    println(cloneCell12.getDateCellValue)
  }

}