package net.scalax.cpoi.style

import net.scalax.cpoi.content.{CellContentAbs, CellData, CellDataAbs}
import net.scalax.cpoi.rw.CellWriter
import org.apache.poi.ss.usermodel.Cell

object CPoiUtils {

  def multiplySet(styleGen: StyleGen,
                  list: List[(Cell, CellDataAbs)]): StyleGen = {
    list.foldLeft(styleGen) {
      case (eachGen, (eachCell, eachCData)) =>
        val gen = eachGen.setCellStyle(eachCData, eachCell)
        eachCData.set(eachCell)
        gen
    }
  }

  def multiplySet(styleGen: StyleGen,
                  list: Stream[(Cell, CellDataAbs)]): StyleGen = {
    list.foldLeft(styleGen) {
      case (eachGen, (eachCell, eachCData)) =>
        val gen = eachGen.setCellStyle(eachCData, eachCell)
        eachCData.set(eachCell)
        gen
    }
  }

  def multiplySet(styleGen: MutableStyleGen,
                  list: List[(Cell, CellDataAbs)]): Unit = {
    list.foreach {
      case (eachCell, eachCData) =>
        styleGen.setCellStyle(eachCData, eachCell)
        eachCData.set(eachCell)
    }
  }

  def multiplySet(styleGen: MutableStyleGen,
                  list: Stream[(Cell, CellDataAbs)]): Unit = {
    list.foreach {
      case (eachCell, eachCData) =>
        styleGen.setCellStyle(eachCData, eachCell)
        eachCData.set(eachCell)
    }
  }

  def wrapCell(poiCell: Option[Cell]): CellContentAbs = {
    val c1 = poiCell
    new CellContentAbs {
      override val poiCell = c1
    }
  }

  def wrapCell(poiCell: Cell): CellContentAbs = {
    val c1 = poiCell
    new CellContentAbs {
      override val poiCell = Option(c1)
    }
  }

  def wrapData[T](data: T, styleTransform: List[StyleTransform] = List.empty)(
      implicit operation: CellWriter[T]): CellData[T] = {
    CellData.gen(data, styleTransform)
  }

}
