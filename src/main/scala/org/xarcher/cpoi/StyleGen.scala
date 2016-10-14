package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.CellStyle

case class StyleGenWrap(styleGen: StyleGen, cellStyle: Option[CellStyle])

trait StyleGen {
  self =>

  val cellMap: Map[List[StyleTransform], CellStyle] = Map.empty
  val createCellStyle: () => CellStyle

  def getCellStyle(transformNew: StyleTransform*): StyleGenWrap = {
    getCellStyle(transformNew.toList)
  }

  def getCellStyle(transformNew: List[StyleTransform]): StyleGenWrap = {
    transformNew match {
      case Nil => StyleGenWrap(self, None)
      case trans =>
        cellMap.get(trans) match {
          case styleSome@Some(s) =>
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
  }

}

object StyleGen {

  def apply(styleGen: () => CellStyle): StyleGen = new StyleGen {
    override val createCellStyle = styleGen
  }

}