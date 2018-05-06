package net.scalax.cpoi.style

import net.scalax.cpoi.content.{CellContentAbs, CellData, CellDataAbs}
import net.scalax.cpoi.rw.{CPoiDone, CellWriter}
import org.apache.poi.ss.usermodel.Cell

import scala.util.{Failure, Try}

object CPoiUtils {

  def multiplySet(styleGen: StyleGen,
                  seq: Seq[(Cell, CellDataAbs)]): Try[StyleGen] = {
    val ms = styleGen.toMutable
    seq.toStream
      .map { item =>
        Try {
          item match {
            case (eachCell, eachCData) =>
              ms.setCellStyle(eachCData, eachCell)
              eachCData.set(eachCell)
          }
        }.flatten: Try[CPoiDone]
      }
      .collectFirst { case Failure(e) => e } match {
      case Some(e) =>
        Failure(e)
      case None =>
        Try { ms.toImmutable }
    }
  }

  def multiplySet(styleGen: MutableStyleGen,
                  seq: Seq[(Cell, CellDataAbs)]): Try[Unit] = {
    seq.toStream
      .map { item =>
        Try {
          item match {
            case (eachCell, eachCData) =>
              styleGen.setCellStyle(eachCData, eachCell)
              eachCData.set(eachCell)
          }
        }.flatten: Try[CPoiDone]
      }
      .collectFirst { case Failure(e) => e } match {
      case Some(e) =>
        Failure(e)
      case None =>
        Try { () }
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

  def newStyleGenInstance: StyleGen = {
    StyleGen.newInstance
  }

  def newMutableStyleGenInstance: MutableStyleGen = {
    MutableStyleGen.newInstance
  }

}
