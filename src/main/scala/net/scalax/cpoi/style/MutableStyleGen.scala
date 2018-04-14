package net.scalax.cpoi.style

import net.scalax.cpoi.content.CellDataAbs
import org.apache.poi.ss.usermodel.{Cell, CellStyle}

import scala.collection.mutable.{Map => MutableMap}

trait MutableStyleGen {
  self =>

  protected val cellMap: MutableMap[StyleKeyWrap, CellStyle] = MutableMap.empty

  protected def getCellStyle(cellDate: CellDataAbs, cell: Cell): CellStyle = {
    val workbook = cell.getSheet.getWorkbook
    val key =
      StyleKeyWrap(workbook = workbook, styleTrans = cellDate.styleTransform)
    val cellStyleOpt = cellMap.get(key)
    val cellStyle = cellStyleOpt match {
      case Some(c) =>
        c
      case None =>
        val c = workbook.createCellStyle
        val newCellStyle = key.styleTrans.foldLeft(c) { (style, tran) =>
          tran.operation(workbook, style)
        }
        self.cellMap += (key -> newCellStyle)
        newCellStyle
    }
    cellStyle
  }

  def setCellStyle(cellDate: CellDataAbs, cell: Cell): Unit = {
    val cStyle = getCellStyle(cellDate, cell)
    cell.setCellStyle(cStyle)
  }

}

object MutableStyleGen {
  def getInstance: MutableStyleGen = {
    new MutableStyleGen {
      override protected val cellMap: MutableMap[StyleKeyWrap, CellStyle] =
        MutableMap.empty
    }
  }
}
