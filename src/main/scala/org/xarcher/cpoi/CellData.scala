package org.xarcher.cpoi

import java.util.Date

import org.apache.poi.ss.usermodel.{Cell, CellStyle, RichTextString, Workbook}

import scala.language.existentials
import scala.language.implicitConversions
import scala.util.control.Exception._

case class CellData[T : WriteableCellOperation](val data: Option[T]) {

  val styleTrans: List[StyleTransform] = Nil

  type DataType = T

  private val operation = implicitly[WriteableCellOperation[T]]

  def typeName = operation.typeName

  def setValue(cell: Cell, style: Option[CellStyle] = None): Boolean = {
    operation.set(data, Option(cell), style)
  }

  def setValue(cell: Cell, styleGen: StyleGen): Boolean = {
    val style = styleGen.getCellStyle(styleTrans)
    operation.set(data, Option(cell), style)
  }

  def withTransforms(trans: List[StyleTransform]) = {
    new CellData(data) {
      override val styleTrans = trans
    }
  }

  def withTransforms(trans: StyleTransform*) = {
    new CellData(data) {
      override val styleTrans = trans.toList
    }
  }

  def addTransform(tran: List[StyleTransform]) = {
    val thisTrans = this.styleTrans
    new CellData(data) {
      override val styleTrans = thisTrans ::: tran
    }
  }

  def addTransform(tran: StyleTransform*) = {
    val thisTrans = this.styleTrans
    new CellData(data) {
      override val styleTrans = thisTrans ::: tran.toList
    }
  }

}

object CellData {

  def gen[T](data: T)(implicit operation: WriteableCellOperation[T]) = {
    CellData(Option(data))
  }

}

trait StyleTransform {

  val operation: CellStyle => CellStyle

}
/*object StyleTransform {

  def apply(tran: CellStyle => CellStyle) = {
    new StyleTransform {
      override def operation = tran
    }
  }

}*/