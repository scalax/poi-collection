package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.CellStyle

trait StyleGen {

  var cellMap: Map[List[StyleTransform], CellStyle] = Map()
  val createCellStyle: () => CellStyle

  def getCellStyle(transformNew: StyleTransform*): Option[CellStyle] = {
    getCellStyle(transformNew.toList)
  }

  def getCellStyle(transformNew: List[StyleTransform]): Option[CellStyle] = {
    transformNew match {
      case Nil => None
      case trans =>
        cellMap.get(trans) match {
          case styleSome@Some(s) =>
            styleSome
          case None =>
            val style = createCellStyle()
            val style1 = trans.foldLeft(style)((style, tranform) => {
              tranform.operation(style)
            })
            cellMap += (trans -> style1)
            getCellStyle(trans: _*)
        }
    }
  }

}

object StyleGen {

  def apply(styleGen: => CellStyle) = new StyleGen {
    override val createCellStyle: () => CellStyle = () => styleGen
  }

}