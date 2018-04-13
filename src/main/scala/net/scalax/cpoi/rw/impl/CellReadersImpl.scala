package net.scalax.cpoi.rw

import java.util.Date

import net.scalax.cpoi.{CCell, PoiCellContent}
import net.scalax.cpoi.exception._
import org.apache.poi.ss.usermodel.Cell

import scala.util.Try

trait CellReadersImpl {

  val stringReader = new CellReader[String] {
    override def get(
        cell: Option[Cell]): PoiCellContent.CellReadResult[String] = {
      val content = CCell(cell)
      content.stringValue
        .fold(
          (_: CellReaderException) =>
            content.doubleValue.fold(
              (_: CellReaderException) =>
                content.dateValue.fold(
                  (_: CellReaderException) =>
                    content.booleanValue.fold(
                      (_: CellReaderException) =>
                        content.richTextStringValue.fold(
                          (_: CellReaderException) =>
                            Left(new ExcepectStringCellException()),
                          rich => Right(rich.getString)),
                      bool => Right(bool.toString)
                  ),
                  date => Right(date.toString)
              ),
              dou => Right(dou.toString)
          ),
          str => Right(str)
        )
    }
  }

  val doubleReader = new CellReader[Double] {
    override def get(
        cell: Option[Cell]): PoiCellContent.CellReadResult[Double] = {
      val content = CCell(cell)
      content.doubleValue.fold(
        (_: CellReaderException) =>
          content.stringValue.fold(
            (_: CellReaderException) =>
              Left(new ExcepectNumericCellException()),
            str =>
              Try { str.toDouble }.toEither.fold(
                (_: Throwable) => Left(new ExcepectNumericCellException()),
                d => Right(d))
        ),
        dou => Right(dou)
      )
    }
  }

  val booleanReader = new CellReader[Boolean] {
    override def get(
        cell: Option[Cell]): PoiCellContent.CellReadResult[Boolean] = {
      val content = CCell(cell)
      content.booleanValue.fold(
        (_: CellReaderException) =>
          content.stringValue.fold(
            (_: CellReaderException) =>
              Left(new ExcepectBooleanCellException()),
            str =>
              Try { str.toBoolean }.toEither.fold(
                (_: Throwable) => Left(new ExcepectBooleanCellException()),
                d => Right(d))
        ),
        dou => Right(dou)
      )
    }
  }

  val dateReader = new CellReader[Date] {
    override def get(
        cell: Option[Cell]): PoiCellContent.CellReadResult[Date] = {
      val content = CCell(cell)
      content.dateValue.fold(
        (e: CellReaderException) => Left(new ExcepectDateException(e)),
        date => Right(date)
      )
    }
  }

}
