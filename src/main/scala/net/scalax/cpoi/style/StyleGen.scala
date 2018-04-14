package net.scalax.cpoi.style

import net.scalax.cpoi.content.CellDataAbs
import org.apache.poi.ss.usermodel.{Cell, CellStyle}

trait StyleGen {
  self =>

  protected val cellMap: Map[StyleKeyWrap, CellStyle]
  /*def getCellStyle(transformNew: StyleTransform*): StyleGenWrap = {
    getCellStyle(transformNew.toList)
  }

  def getCellStyle(transformNew: List[StyleTransform]): StyleGenWrap = {
    transformNew match {
      case Nil => StyleGenWrap(self, None)
      case trans =>
        cellMap.get(trans) match {
          case styleSome @ Some(s) =>
            StyleGenWrap(self, styleSome)
          case None =>
            val style = createCellStyle()
            val style1 = trans.foldLeft(style) { (style, tranform) =>
              tranform.operation(style)
            }
            val newCellMap = cellMap + (trans -> style1)
            //getCellStyle(trans: _*)
            val newStyleGen = new StyleGen {
              override val cellMap = newCellMap
              override val createCellStyle = self.createCellStyle
            }
            StyleGenWrap(newStyleGen, Option(style1))
        }
    }
  }*/
  protected def getCellStyle(cellDate: CellDataAbs,
                             cell: Cell): (CellStyle, StyleGen) = {
    val workbook = cell.getSheet.getWorkbook
    val key =
      StyleKeyWrap(workbook = workbook, styleTrans = cellDate.styleTransform)
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

  def setCellStyle(cellDate: CellDataAbs, cell: Cell): StyleGen = {
    val (cStyle, newStyleGen) = getCellStyle(cellDate, cell)
    cell.setCellStyle(cStyle)
    newStyleGen
  }

}

object StyleGen {
  def getInstance: StyleGen = {
    new StyleGen {
      override protected val cellMap: Map[StyleKeyWrap, CellStyle] = Map.empty
    }
  }
}
