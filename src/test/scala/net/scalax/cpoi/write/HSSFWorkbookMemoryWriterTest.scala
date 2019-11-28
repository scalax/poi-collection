package net.scalax.cpoi.test

import java.util.Date

import net.scalax.cpoi.api._
import org.apache.poi.hssf.usermodel.{HSSFCell, HSSFWorkbook}
import org.apache.poi.ss.usermodel.{CellStyle, CellType, Workbook}
import org.scalatest._

import scala.util.Try
import scala.collection.compat._

class HSSFWorkbookMemoryWriterTest extends FlatSpec with Matchers {

  case object TextStyle extends StyleTransform {
    override def operation(workbook: Workbook, cellStyle: CellStyle): CellStyle = {
      val format = workbook.createDataFormat.getFormat("@")
      cellStyle.setDataFormat(format)
      cellStyle
    }
  }

  case object DoubleStyle extends StyleTransform {
    override def operation(workbook: Workbook, cellStyle: CellStyle): CellStyle = {
      val format = workbook.createDataFormat.getFormat("0.00")
      cellStyle.setDataFormat(format)
      cellStyle
    }
  }

  case class Locked(lock: Boolean) extends StyleTransform {
    override def operation(workbook: Workbook, cellStyle: CellStyle): CellStyle = {
      cellStyle.setLocked(lock)
      cellStyle
    }
  }

  "HSSFWorkbook" should "set more than 4000 cells with their own CellStyle" in {
    import writers._

    val testUTF8Str = "  测试 UTF8 字符  "
    val testDouble  = 2333.2233
    val workbook    = new HSSFWorkbook()

    val defaultCellStyleCount = workbook.getNumCellStyles

    val sheet = workbook.createSheet("SheetA")
    val cells = (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.createRow(rowIndex + 2)
      List(
          row.createCell(3) -> CPoi.wrapData(testUTF8Str).addTransform(TextStyle, Locked(false))
        , row.createCell(6) -> CPoi.wrapData(testDouble).addTransform(DoubleStyle, Locked(true))
      )
    }).flatten.to(List)
    val gen = CPoi.newStyleGen
    CPoi.multiplySet(gen, cells): Try[StyleGen]

    (for (rowIndex <- (1 to 8000)) yield {
      val row     = sheet.getRow(rowIndex + 2)
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
    val testDouble  = 2333.2233
    val workbook    = new HSSFWorkbook()

    val defaultCellStyleCount = workbook.getNumCellStyles

    val sheet = workbook.createSheet("SheetA")
    val cells = (for (rowIndex <- (1 to 8000)) yield {
      val row = sheet.createRow(rowIndex + 2)
      List(
          row.createCell(3) -> CPoi.wrapData(testUTF8Str).addTransform(TextStyle, Locked(false))
        , row.createCell(6) -> CPoi.wrapData(testDouble).addTransform(DoubleStyle, Locked(true))
      )
    }).flatten.to(List)
    val gen = CPoi.newMutableStyleGen
    CPoi.multiplySet(gen, cells): Unit

    (for (rowIndex <- (1 to 8000)) yield {
      val row     = sheet.getRow(rowIndex + 2)
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
    val testDouble  = 2333.2233
    val workbook    = new HSSFWorkbook()

    val defaultCellStyleCount = workbook.getNumCellStyles

    val sheet = workbook.createSheet("SheetA")
    val cells: Stream[(HSSFCell, CellDataAbs)] = (for (rowIndex <- Stream.from(1 to 8000)) yield {
      val row = sheet.createRow(rowIndex + 2)
      List(
          row.createCell(3) -> CPoi.wrapData(testUTF8Str).addTransform(TextStyle, Locked(false))
        , row.createCell(6) -> CPoi.wrapData(testDouble).addTransform(DoubleStyle, Locked(true))
      )
    }).flatten
    val gen = CPoi.newStyleGen
    CPoi.multiplySet(gen, cells): Try[StyleGen]

    (for (rowIndex <- (1 to 8000)) yield {
      val row     = sheet.getRow(rowIndex + 2)
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
    val testDouble  = 2333.2233
    val workbook    = new HSSFWorkbook()

    val defaultCellStyleCount = workbook.getNumCellStyles

    val sheet = workbook.createSheet("SheetA")
    val cells: Stream[(HSSFCell, CellDataAbs)] = (for (rowIndex <- Stream.from(1 to 8000)) yield {
      val row = sheet.createRow(rowIndex + 2)
      List(
          row.createCell(3) -> CPoi.wrapData(testUTF8Str).addTransform(TextStyle, Locked(false))
        , row.createCell(6) -> CPoi.wrapData(testDouble).addTransform(DoubleStyle, Locked(true))
      )
    }).flatten
    val gen = CPoi.newMutableStyleGen
    CPoi.multiplySet(gen, cells): Unit

    (for (rowIndex <- (1 to 8000)) yield {
      val row     = sheet.getRow(rowIndex + 2)
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
    a[IllegalStateException] should be thrownBy (for ((_: Int) <- (1 to 4010)) yield {
      workbook.createCellStyle
    })
  }

  "HSSFWorkbook" should "not write to cell when data is None" in {
    import writers._

    val workbook = new HSSFWorkbook()

    val sheet = workbook.createSheet("SheetA")

    val poiCell1 = sheet.createRow(1).createCell(1)
    val poiCell2 = sheet.createRow(1).createCell(2)
    val poiCell3 = sheet.createRow(1).createCell(3)
    val poiCell4 = sheet.createRow(1).createCell(4)

    val cells = List(
        poiCell1 -> CPoi.wrapData(Option.empty[String])
      , poiCell2 -> CPoi.wrapData(Option.empty[Double])
      , poiCell3 -> CPoi.wrapData(Option.empty[Boolean])
      , poiCell4 -> CPoi.wrapData(Option.empty[Date])
    )

    val gen = CPoi.newStyleGen
    CPoi.multiplySet(gen, cells): Try[StyleGen]

    poiCell1.getCellType should be(CellType.BLANK)
    poiCell2.getCellType should be(CellType.BLANK)
    poiCell3.getCellType should be(CellType.BLANK)
    poiCell4.getCellType should be(CellType.BLANK)
  }

  "HSSFWorkbook" should "not write to cell with immutable style gen when there is a exception thrown" in {
    import writers._

    val workbook = new HSSFWorkbook()

    val sheet = workbook.createSheet("SheetA")

    val poiCells = (1 to 1000).map { i =>
      sheet.createRow(i).createCell(1)
    }

    val cellData     = CPoi.wrapData("2333").addTransform(TextStyle)
    val cellDataList = (1 to 1000).map(_ => cellData)
    val gen          = CPoi.newStyleGen

    val tuple2List = poiCells.zip(cellDataList).zipWithIndex.map {
      case (_, index) if index == 602 => null
      case (item, _)                  => item
    }

    CPoi.multiplySet(gen, tuple2List): Try[StyleGen]

    poiCells(600).getCellType should be(CellType.STRING)
    poiCells(601).getCellType should be(CellType.STRING)
    poiCells(602).getCellType should be(CellType.BLANK)
    poiCells(603).getCellType should be(CellType.BLANK)
  }

  "HSSFWorkbook" should "not write to cell with mutable style gen when there is a exception thrown" in {
    import writers._

    val workbook = new HSSFWorkbook()

    val sheet = workbook.createSheet("SheetA")

    val poiCells = (1 to 1000).map { i =>
      sheet.createRow(i).createCell(1)
    }

    val cellData     = CPoi.wrapData("2333").addTransform(TextStyle)
    val cellDataList = (1 to 1000).map(_ => cellData)
    val gen          = CPoi.newMutableStyleGen

    val tuple2List = poiCells.zip(cellDataList).zipWithIndex.map {
      case (_, index) if index == 602 => null
      case (item, _)                  => item
    }

    CPoi.multiplySet(gen, tuple2List): Try[CPoiDone]

    poiCells(600).getCellType should be(CellType.STRING)
    poiCells(601).getCellType should be(CellType.STRING)
    poiCells(602).getCellType should be(CellType.BLANK)
    poiCells(603).getCellType should be(CellType.BLANK)
  }

}
