package net.scalax.cpoi.style

import net.scalax.cpoi.content.CellDataAbs
import net.scalax.cpoi.rw.CPoiDone
import org.apache.poi.ss.usermodel.{Cell, CellStyle}

import scala.collection.mutable.{Map => MutableMap}
import scala.util.Try
import scala.collection.compat._

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

  def setCellStyle(cellDate: CellDataAbs, cell: Cell): Try[CPoiDone] = {
    Try {
      val cStyle = getCellStyle(cellDate, cell)
      cell.setCellStyle(cStyle)
      CPoiDone.instance
    }
  }

  def toImmutable: StyleGen = new StyleGen {
    override protected val cellMap = Map.from(self.cellMap)
  }

}
