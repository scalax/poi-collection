package net.scalax.cpoi.utils

import net.scalax.cpoi.content.{CellContentAbs, CellData, CellDataAbs, CellDataImpl}
import net.scalax.cpoi.rw.{CPoiDone, CellWriter}
import net.scalax.cpoi.style.{MutableStyleGen, StyleGen, StyleKeyWrap, StyleTransform}
import org.apache.poi.ss.usermodel.{Cell, CellStyle}

import scala.util.{Failure, Try}
import scala.collection.mutable.{Map => MutableMap}
import scala.collection.compat._

trait CPoi {

  def multiplySet(styleGen: StyleGen, seq: Seq[(Cell, CellDataAbs)]): Try[StyleGen] = {
    val ms = styleGen.toMutable
    Stream
      .from(seq)
      .map { item =>
        Try {
          item match {
            case (eachCell, eachCData) =>
              ms.setCellStyle(eachCData, eachCell).flatMap { (_: CPoiDone) =>
                eachCData.set(eachCell)
              }
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

  def multiplySet(styleGen: MutableStyleGen, seq: Seq[(Cell, CellDataAbs)]): Try[CPoiDone] = {
    Stream
      .from(seq)
      .map { item =>
        Try {
          item match {
            case (eachCell, eachCData) =>
              styleGen.setCellStyle(eachCData, eachCell).flatMap { (_: CPoiDone) =>
                eachCData.set(eachCell)
              }
          }
        }.flatten: Try[CPoiDone]
      }
      .collectFirst { case Failure(e) => e } match {
      case Some(e) =>
        Failure(e)
      case None =>
        Try { CPoiDone.instance }
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
    implicit
    operation: CellWriter[T]
  ): CellData[T] =
    CellDataImpl(data, styleTransform)

  def newStyleGen: StyleGen = new StyleGen {
    override protected val cellMap: Map[StyleKeyWrap, CellStyle] = Map.empty
  }

  def newMutableStyleGen: MutableStyleGen = new MutableStyleGen {
    override protected val cellMap: MutableMap[StyleKeyWrap, CellStyle] =
      MutableMap.empty
  }

}
