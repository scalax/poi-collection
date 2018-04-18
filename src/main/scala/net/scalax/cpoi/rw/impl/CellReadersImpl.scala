package net.scalax.cpoi.rw

import java.util.Date

import cats.ApplicativeError
import net.scalax.cpoi.exception._
import net.scalax.cpoi._
import org.apache.poi.ss.usermodel.{Cell, CellType}
import cats.implicits._

trait CellReadersImpl {

  def nonBlankStringReader: CellReader[String] = {

    val m = ApplicativeError[CellReader, CellReaderException]

    stringReader.flatMap { str =>
      val trimStr = str.trim

      val either = if (trimStr.isEmpty) {
        Left(new CellNotExistsException())
      } else {
        Right(trimStr)
      }

      m.fromEither(either)
    }

  }

  def nonEmptyStringReader: CellReader[String] = {

    val m = ApplicativeError[CellReader, CellReaderException]

    stringReader.flatMap { str =>
      val trimStr = str

      val either = if (trimStr.isEmpty) {
        Left(new CellNotExistsException())
      } else {
        Right(trimStr)
      }

      m.fromEither(either)
    }

  }

  def stringReader: CellReader[String] = new CellReader[String] {
    self =>

    override def get(cell: Option[Cell]): CellReadResult[String] = {
      cell match {
        case Some(c) =>
          c.getCellTypeEnum match {
            case CellType.BLANK =>
              Right(c.getStringCellValue)
            case CellType.STRING =>
              Right(c.getStringCellValue)
            case CellType.NUMERIC =>
              c.setCellType(CellType.STRING)
              Right(c.getStringCellValue)
            //convert boolean to string is meaningless.
            //case CellType.BOOLEAN =>
            //c.setCellType(CellType.STRING)
            //Right(c.getStringCellValue)
            case CellType.FORMULA =>
              val wb = c.getSheet.getWorkbook
              val crateHelper = wb.getCreationHelper
              val evaluator = crateHelper.createFormulaEvaluator
              self.get(Option(evaluator.evaluateInCell(c)))
            case _ =>
              Left(new ExpectStringCellException())
          }
        case _ =>
          //read null as empty cell
          Right("")
      }
    }
  }

  def doubleReader: CellReader[Double] = new CellReader[Double] {
    self =>

    override def get(cell: Option[Cell]): CellReadResult[Double] = {
      cell match {
        case Some(c) =>
          c.getCellTypeEnum match {
            case CellType.BLANK =>
              Left(new CellNotExistsException())
            case CellType.NUMERIC =>
              Right(c.getNumericCellValue)
            case CellType.FORMULA =>
              val wb = c.getSheet.getWorkbook
              val crateHelper = wb.getCreationHelper
              val evaluator = crateHelper.createFormulaEvaluator
              self.get(Option(evaluator.evaluateInCell(c)))
            case _ =>
              Left(new ExpectNumericCellException())
          }
        case _ =>
          Left(new CellNotExistsException())
      }
    }
  }

  def booleanReader: CellReader[Boolean] = new CellReader[Boolean] {
    self =>

    override def get(cell: Option[Cell]): CellReadResult[Boolean] = {
      cell match {
        case Some(c) =>
          c.getCellTypeEnum match {
            case CellType.BLANK =>
              Left(new CellNotExistsException())
            case CellType.BOOLEAN =>
              Right(c.getBooleanCellValue)
            case CellType.FORMULA =>
              val wb = c.getSheet.getWorkbook
              val crateHelper = wb.getCreationHelper
              val evaluator = crateHelper.createFormulaEvaluator
              self.get(Option(evaluator.evaluateInCell(c)))
            case _ =>
              Left(new ExpectBooleanCellException())
          }
        case _ =>
          Left(new CellNotExistsException())
      }
    }
  }

  def dateReader: CellReader[Date] = new CellReader[Date] {
    self =>

    override def get(cell: Option[Cell]): CellReadResult[Date] = {
      cell match {
        case Some(c) =>
          c.getCellTypeEnum match {
            case CellType.BLANK =>
              Left(new CellNotExistsException())
            case CellType.NUMERIC =>
              Option(c.getDateCellValue) match {
                case Some(s) =>
                  Right(s)
                case _ =>
                  Left(new ExpectDateException())
              }
            case CellType.FORMULA =>
              val wb = c.getSheet.getWorkbook
              val crateHelper = wb.getCreationHelper
              val evaluator = crateHelper.createFormulaEvaluator
              self.get(Option(evaluator.evaluateInCell(c)))
            case _ =>
              Left(new ExpectDateException())
          }
        case _ =>
          Left(new CellNotExistsException())
      }
    }
  }

}
