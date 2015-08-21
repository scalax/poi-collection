package org.xarcher.poic

import org.apache.poi.ss.usermodel.{RichTextString, Cell}
import scala.language.implicitConversions
import java.util.Date

abstract class CCellData[T](val data: T)
case class CFormulaData(override val data: String) extends CCellData(data)
case class CNumericData(override val data: Double) extends CCellData(data)
case class CDateData(override val data: Date) extends CCellData(data)
case class CRichTextStringData(override val data: Date) extends CCellData(data)
case class CStringData(override val data: String) extends CCellData(data)
case class CBooleanData(override val data: Boolean) extends CCellData(data)
case class CErrorData(override val data: Byte) extends CCellData(data)

trait AbsCellConvert[T] {

  val convert: CCellAbs => Option[T]

}

trait DefaultCellConvert[T] extends AbsCellConvert[T]
trait CustomCellConvert[T] extends AbsCellConvert[T]

trait CCellAbs {

  val poiCell: Option[Cell]

  import scala.util.control.Exception._

  lazy val cellType: Option[Int] = allCatch.opt(poiCell.map(_.getCellType)).flatMap(t => t)

  lazy val formulaValue: Option[String] = allCatch.opt(poiCell.map(_.getCellFormula)).flatMap(t => t)

  lazy val numericValue: Option[Double] = allCatch.opt(poiCell.map(_.getNumericCellValue)).flatMap(t => t)

  lazy val dateValue: Option[Date] = allCatch.opt(poiCell.map(_.getDateCellValue)).flatMap(t => t)

  lazy val richTextStringValue: Option[RichTextString] = allCatch.opt(poiCell.map(_.getRichStringCellValue)).flatMap(t => t)

  lazy val stringValue: Option[String] = allCatch.opt(poiCell.map(_.getStringCellValue)).flatMap(t => t)

  lazy val booleanValue: Option[Boolean] = allCatch.opt(poiCell.map(_.getBooleanCellValue)).flatMap(t => t)

  lazy val errorValue: Option[Byte] = allCatch.opt(poiCell.map(_.getErrorCellValue)).flatMap(t => t)

  lazy val actualValue: Option[CCellData[_]] = cellType.flatMap {
    case Cell.CELL_TYPE_FORMULA => formulaValue.map(CFormulaData(_))
    case Cell.CELL_TYPE_NUMERIC => numericValue.map(CNumericData(_))
    case Cell.CELL_TYPE_STRING => stringValue.map(CStringData(_))
    case Cell.CELL_TYPE_BOOLEAN => booleanValue.map(CBooleanData(_))
    case Cell.CELL_TYPE_ERROR => errorValue.map(CErrorData(_))
    case Cell.CELL_TYPE_BLANK => None
    case _ => None
  }

  def tryValue[T](implicit defaultCellConvert: DefaultCellConvert[T]) = defaultCellConvert.convert(this)
  def tryCustomValue[T](implicit customCellConvert: CustomCellConvert[T]) = customCellConvert.convert(this)
  def tryAllValue[T](implicit allCellConvert: AbsCellConvert[T]) = allCellConvert.convert(this)

}

class CCell(override val poiCell: Option[Cell]) extends CCellAbs {
}