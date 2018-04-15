package net.scalax.cpoi.rw

import java.util.Date

import cats.ApplicativeError
import net.scalax.cpoi.content.CCell
import net.scalax.cpoi.exception._
import net.scalax.cpoi._

import org.apache.poi.ss.usermodel.Cell

import cats.implicits._

import scala.util.Try

trait CellReadersImpl {

  def nonEmptyStringReader: CellReader[String] = {

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

  def stringReader = new CellReader[String] {
    override def get(cell: Option[Cell]): CellReadResult[String] = {
      val content = CCell(cell)
      content.stringValue
        .fold(
          (e: CellReaderException) =>
            e match {
              case r: CellNotExistsException =>
                Left(r)
              case _: CellReaderException =>
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
                )
          },
          str => Right(str)
        )
    }
  }

  def doubleReader = new CellReader[Double] {
    override def get(cell: Option[Cell]): CellReadResult[Double] = {
      val content = CCell(cell)
      content.doubleValue.fold(
        (e: CellReaderException) =>
          e match {
            case r: CellNotExistsException =>
              Left(r)
            case _: CellReaderException =>
              content.stringValue.fold(
                (_: CellReaderException) =>
                  Left(new ExcepectNumericCellException()),
                str =>
                  Try { str.toDouble }.toEither.fold(
                    (_: Throwable) => Left(new ExcepectNumericCellException()),
                    d => Right(d))
              )
        },
        dou => Right(dou)
      )
    }
  }

  def booleanReader = new CellReader[Boolean] {
    override def get(cell: Option[Cell]): CellReadResult[Boolean] = {
      val content = CCell(cell)
      content.booleanValue.fold(
        (e: CellReaderException) =>
          e match {
            case r: CellNotExistsException =>
              Left(r)
            case _: CellReaderException =>
              content.stringValue.fold(
                (_: CellReaderException) =>
                  Left(new ExcepectBooleanCellException()),
                str =>
                  Try { str.toBoolean }.toEither.fold(
                    (_: Throwable) => Left(new ExcepectBooleanCellException()),
                    d => Right(d))
              )
        },
        dou => Right(dou)
      )
    }
  }

  def dateReader = new CellReader[Date] {
    override def get(cell: Option[Cell]): CellReadResult[Date] = {
      val content = CCell(cell)
      content.dateValue.fold(
        (e: CellReaderException) =>
          e match {
            case r: CellNotExistsException =>
              Left(r)
            case r: CellReaderException =>
              Left(r)
        },
        date => Right(date)
      )
    }
  }

}
