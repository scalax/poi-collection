package net.scalax.cpoi.content

import java.util.Date

import net.scalax.cpoi.exception._
import net.scalax.cpoi.rw.{CellReader, CellWriter}
import org.apache.poi.ss.usermodel.{Cell, CellStyle, CellType, RichTextString}

import scala.util.Try

object PoiCellContent {
  type CellReadResult[R] = Either[CellReaderException, R]
}

trait CellContent {

  val poiCell: Option[Cell]

  import PoiCellContent._

  lazy val formulaValue: CellReadResult[String] =
    poiCell
      .map { c =>
        Try {
          c.getCellFormula
        }.toEither.left.map {
          case e: IllegalStateException =>
            new ExcepectFormulaException(e)
          case e @ (_: Throwable) =>
            throw e
        }
      }
      .getOrElse(Left(new CellNotExistsException))

  lazy val doubleValue: CellReadResult[Double] =
    poiCell
      .map { c =>
        Try {
          c.getNumericCellValue
        }.toEither.left.map {
          case e: IllegalStateException =>
            new ExcepectNumericCellException(e)
          case e: NumberFormatException =>
            new ExcepectNumericCellException(e)
          case e @ (_: Throwable) =>
            throw e
        }
      }
      .getOrElse(Left(new CellNotExistsException))

  lazy val dateValue: CellReadResult[Date] =
    poiCell
      .map { c =>
        Try {
          c.getDateCellValue
        }.toEither.left.map {
          case e: IllegalStateException =>
            new ExcepectDateException(e)
          case e: NumberFormatException =>
            new ExcepectDateException(e)
          case e @ (_: Throwable) =>
            throw e
        }
      }
      .getOrElse(Left(new CellNotExistsException))

  lazy val richTextStringValue: CellReadResult[RichTextString] =
    poiCell
      .map { c =>
        Try {
          c.getRichStringCellValue
        }.toEither.left.map {
          case e @ (_: Throwable) =>
            new ExcepectRichTextException(e)
        }
      }
      .getOrElse(Left(new CellNotExistsException))

  lazy val stringValue: CellReadResult[String] =
    poiCell
      .map { c =>
        Try {
          c.getStringCellValue
        }.toEither.left.map {
          case e @ (_: Throwable) =>
            new ExcepectStringCellException(e)
        }
      }
      .getOrElse(Left(new CellNotExistsException))

  lazy val booleanValue: CellReadResult[Boolean] =
    poiCell
      .map { c =>
        Try {
          c.getBooleanCellValue
        }.toEither.left.map {
          case e: IllegalStateException =>
            new ExcepectBooleanCellException(e)
          case e @ (_: Throwable) =>
            throw e
        }
      }
      .getOrElse(Left(new CellNotExistsException))

  lazy val errorValue: CellReadResult[Byte] =
    poiCell
      .map { c =>
        Try {
          c.getErrorCellValue
        }.toEither.left.map {
          case e: IllegalStateException =>
            new ExcepectErrorCellException(e)
          case e @ (_: Throwable) =>
            throw e
        }
      }
      .getOrElse(Left(new CellNotExistsException))

  lazy val isEmpty: Boolean = {
    poiCell.map(_.getCellTypeEnum == CellType.BLANK).getOrElse(true)
  }

  lazy val isDefined: Boolean = !isEmpty

  lazy val cellType: Option[CellType] =
    Try(poiCell.map(_.getCellTypeEnum)).toOption.flatten

  lazy val cellStyle: Option[CellStyle] = poiCell.map(_.getCellStyle)

  lazy val rowIndex: Option[Int] = poiCell.map(_.getRowIndex)
  lazy val columnIndex: Option[Int] = poiCell.map(_.getColumnIndex)

  def genData[T: CellWriter: CellReader]: CellReadResult[CellData[T]] = {
    val valueEt = implicitly[CellReader[T]].get(poiCell)
    valueEt.map(s => CellData(s, List.empty))
  }

  def tryValue[T: CellReader]: CellReadResult[T] = {
    implicitly[CellReader[T]].get(poiCell)
  }

}

object CellContent {

  implicit class CellContentOptExtensionMethon(cellOpt: Option[CellContent]) {

    def openAlways: CellContent = {
      cellOpt match {
        case Some(s) => s
        case None    => CCell(None)
      }
    }

  }

}

class CCell(override val poiCell: Option[Cell]) extends CellContent {}

object CCell {

  def apply(poiCell: Option[Cell]): CCell = new CCell(poiCell)
  def apply(poiCell: Cell): CCell = CCell(Option(poiCell))

}
/*case class CWorkbook(sheets: Set[CSheet])
case class CSheet(index: Int, name: String, rows: Set[CRow])
case class CRow(index: Int, cells: Set[CCell])*/
