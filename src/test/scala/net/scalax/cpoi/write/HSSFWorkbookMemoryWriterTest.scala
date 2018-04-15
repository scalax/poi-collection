package net.scalax.cpoi

import net.scalax.cpoi.content.CellData
import net.scalax.cpoi.style.{
  CPoiUtils,
  MutableStyleGen,
  StyleGen,
  StyleTransform
}
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.{CellStyle, Workbook}
import org.scalatest._

class HSSFWorkbookMemoryWriterTest extends FlatSpec with Matchers {

  case object TextStyle extends StyleTransform {
    override def operation(workbook: Workbook,
                           cellStyle: CellStyle): CellStyle = {
      val format = workbook.createDataFormat.getFormat("@")
      cellStyle.setDataFormat(format)
      cellStyle
    }
  }

  case object DoubleStyle extends StyleTransform {
    override def operation(workbook: Workbook,
                           cellStyle: CellStyle): CellStyle = {
      val format = workbook.createDataFormat.getFormat("0.00")
      cellStyle.setDataFormat(format)
      cellStyle
    }
  }

  case class Locked(lock: Boolean) extends StyleTransform {
    override def operation(workbook: Workbook,
                           cellStyle: CellStyle): CellStyle = {
      cellStyle.setLocked(lock)
      cellStyle
    }
  }

  "HSSFWorkbook" should "set more than 4000 cells with their own CellStyle" in {
    import writers._

    val testUTF8Str = "  测试 UTF8 字符  "
    val testDouble = 2333.2233
    val workbook = new HSSFWorkbook()

    val defaultCellStyleCount = workbook.getNumCellStyles

    val sheet = workbook.createSheet("SheetA")
    val cells = (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.createRow(rowIndex + 2)
      List(
        row.createCell(3) -> CellData(testUTF8Str).addTransform(TextStyle,
                                                                Locked(false)),
        row.createCell(6) -> CellData(testDouble).addTransform(DoubleStyle,
                                                               Locked(true))
      )
    }).flatten.toList
    val gen = StyleGen.getInstance
    CPoiUtils.multiplySet(gen, cells): StyleGen

    (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.getRow(rowIndex + 2)
      val strCell = row.getCell(3)
      strCell.getStringCellValue should be(testUTF8Str)
      strCell.getCellStyle.getDataFormatString should be("@")
      strCell.getCellStyle.getLocked should be(false)
      val doubleCell = row.getCell(6)
      doubleCell.getNumericCellValue should be(testDouble)
      doubleCell.getCellStyle.getDataFormatString should be("0.00")
      doubleCell.getCellStyle.getLocked should be(true)
    })

    val newCellStyleCount = workbook.getNumCellStyles
    (newCellStyleCount - defaultCellStyleCount) should be(2)
    (newCellStyleCount < 4000) should be(true)
  }

  "HSSFWorkbook" should "set more than 4000 cells with their own CellStyle by MutableStyleGen" in {
    import writers._

    val testUTF8Str = "  测试 UTF8 字符  "
    val testDouble = 2333.2233
    val workbook = new HSSFWorkbook()

    val defaultCellStyleCount = workbook.getNumCellStyles

    val sheet = workbook.createSheet("SheetA")
    val cells = (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.createRow(rowIndex + 2)
      List(
        row.createCell(3) -> CellData(testUTF8Str).addTransform(TextStyle,
                                                                Locked(false)),
        row.createCell(6) -> CellData(testDouble).addTransform(DoubleStyle,
                                                               Locked(true))
      )
    }).flatten.toList
    val gen = MutableStyleGen.getInstance
    CPoiUtils.multiplySet(gen, cells): Unit

    (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.getRow(rowIndex + 2)
      val strCell = row.getCell(3)
      strCell.getStringCellValue should be(testUTF8Str)
      strCell.getCellStyle.getDataFormatString should be("@")
      strCell.getCellStyle.getLocked should be(false)
      val doubleCell = row.getCell(6)
      doubleCell.getNumericCellValue should be(testDouble)
      doubleCell.getCellStyle.getDataFormatString should be("0.00")
      doubleCell.getCellStyle.getLocked should be(true)
    })

    val newCellStyleCount = workbook.getNumCellStyles
    (newCellStyleCount - defaultCellStyleCount) should be(2)
    (newCellStyleCount < 4000) should be(true)
  }

  "HSSFWorkbook" should "set more than 4000 cells with their own CellStyle by StyleGen and stream" in {
    import writers._

    val testUTF8Str = "  测试 UTF8 字符  "
    val testDouble = 2333.2233
    val workbook = new HSSFWorkbook()

    val defaultCellStyleCount = workbook.getNumCellStyles

    val sheet = workbook.createSheet("SheetA")
    val cells = (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.createRow(rowIndex + 2)
      List(
        row.createCell(3) -> CellData(testUTF8Str).addTransform(TextStyle,
                                                                Locked(false)),
        row.createCell(6) -> CellData(testDouble).addTransform(DoubleStyle,
                                                               Locked(true))
      )
    }).flatten.toList.toStream
    val gen = StyleGen.getInstance
    CPoiUtils.multiplySet(gen, cells): StyleGen

    (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.getRow(rowIndex + 2)
      val strCell = row.getCell(3)
      strCell.getStringCellValue should be(testUTF8Str)
      strCell.getCellStyle.getDataFormatString should be("@")
      strCell.getCellStyle.getLocked should be(false)
      val doubleCell = row.getCell(6)
      doubleCell.getNumericCellValue should be(testDouble)
      doubleCell.getCellStyle.getDataFormatString should be("0.00")
      doubleCell.getCellStyle.getLocked should be(true)
    })

    val newCellStyleCount = workbook.getNumCellStyles
    (newCellStyleCount - defaultCellStyleCount) should be(2)
    (newCellStyleCount < 4000) should be(true)
  }

  "HSSFWorkbook" should "set more than 4000 cells with their own CellStyle by MutableStyleGen and stream" in {
    import writers._

    val testUTF8Str = "  测试 UTF8 字符  "
    val testDouble = 2333.2233
    val workbook = new HSSFWorkbook()

    val defaultCellStyleCount = workbook.getNumCellStyles

    val sheet = workbook.createSheet("SheetA")
    val cells = (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.createRow(rowIndex + 2)
      List(
        row.createCell(3) -> CellData(testUTF8Str).addTransform(TextStyle,
                                                                Locked(false)),
        row.createCell(6) -> CellData(testDouble).addTransform(DoubleStyle,
                                                               Locked(true))
      )
    }).flatten.toList.toStream
    val gen = MutableStyleGen.getInstance
    CPoiUtils.multiplySet(gen, cells): Unit

    (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.getRow(rowIndex + 2)
      val strCell = row.getCell(3)
      strCell.getStringCellValue should be(testUTF8Str)
      strCell.getCellStyle.getDataFormatString should be("@")
      strCell.getCellStyle.getLocked should be(false)
      val doubleCell = row.getCell(6)
      doubleCell.getNumericCellValue should be(testDouble)
      doubleCell.getCellStyle.getDataFormatString should be("0.00")
      doubleCell.getCellStyle.getLocked should be(true)
    })

    val newCellStyleCount = workbook.getNumCellStyles
    (newCellStyleCount - defaultCellStyleCount) should be(2)
    (newCellStyleCount < 4000) should be(true)
  }

  "HSSFWorkbook" should "throw exception when create more than 4000 cell style" in {
    val workbook = new HSSFWorkbook()
    a[IllegalStateException] should be thrownBy (for ((_: Int) <- (1 to 4010))
      yield {
        workbook.createCellStyle
      })
  }

}
