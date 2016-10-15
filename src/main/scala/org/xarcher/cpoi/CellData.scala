package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.{Cell, CellStyle}

import scala.language.existentials
import scala.language.implicitConversions

case class CellData[T : WriteableCellOperationAbs](val data: Option[T]) {

  val styleTrans: List[StyleTransform] = Nil

  type DataType = T

  protected val operation = implicitly[WriteableCellOperationAbs[T]]

  def typeTag = operation.typeTag

  def setValue(cell: Cell): Boolean = {
    operation.set(data, Option(cell))
  }

  /*def setValue(cell: Cell, styleGen: StyleGen): Boolean = {
    val style = styleGen.getCellStyle(styleTrans)
    operation.set(data, Option(cell), style)
  }*/

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

  def gen[T](data: T)(implicit operation: WriteableCellOperationAbs[T]) = {
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