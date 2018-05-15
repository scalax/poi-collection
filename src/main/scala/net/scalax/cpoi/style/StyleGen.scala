package net.scalax.cpoi.style

import net.scalax.cpoi.content.CellDataAbs
import org.apache.poi.ss.usermodel.{Cell, CellStyle}
import scala.collection.compat._

trait StyleGen {
  self =>

  protected val cellMap: Map[StyleKeyWrap, CellStyle]

  protected def getCellStyle(cellData: CellDataAbs,
                             cell: Cell): (CellStyle, StyleGen) = {
    val workbook = cell.getSheet.getWorkbook
    val key =
      StyleKeyWrap(workbook = workbook, styleTrans = cellData.styleTransform)
    val cellStyleOpt = cellMap.get(key)
    val (cellStyle, newMap) = cellStyleOpt match {
      case Some(c) => (c, self.cellMap)
      case None =>
        val c = workbook.createCellStyle
        val newCellStyle = key.styleTrans.foldLeft(c) { (style, tran) =>
          tran.operation(workbook, style)
        }
        (newCellStyle, self.cellMap + (key -> newCellStyle))
    }
    val newGen = new StyleGen {
      override protected val cellMap = newMap
    }: StyleGen
    (cellStyle, newGen)
  }

  def setCellStyle(cellData: CellDataAbs, cell: Cell): StyleGen = {
    val (cStyle, newStyleGen) = getCellStyle(cellData, cell)
    cell.setCellStyle(cStyle)
    newStyleGen
  }

  def toMutable: MutableStyleGen = {
    val newMap = scala.collection.mutable.Map.from(self.cellMap)
    new MutableStyleGen {
      override protected val cellMap = newMap
    }
  }

}