package org.xarcher.cpoi

import java.util.UUID

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.scalatest._

class MemoryTest extends FlatSpec with Matchers {

  object PoiOperations extends PoiOperations

  import PoiOperations._

  "Workbook's cell" should "read as string" in {
    val redomCode = UUID.randomUUID.toString

   val workbook = new HSSFWorkbook()
    val sheet = workbook.createSheet("SheetA")
    val row = sheet.createRow(2)
    val cell = row.createCell(3)
    cell.setCellValue(redomCode)
    val ccell = CCell(cell)
    val value = ccell.tryValue[String]
    value.isRight should be (true)
    value.right.get should be (redomCode)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
  }

}