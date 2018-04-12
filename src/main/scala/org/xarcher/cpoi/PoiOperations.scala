package org.xarcher.cpoi

import java.util.Date

import org.apache.poi.ss.usermodel.Cell

import scala.util.Try

trait PoiOperations {

  implicit class CellContentOptExtensionMethon(cellOpt: Option[CellContent]) {

    def openAlways: CellContent = {
      cellOpt match {
        case Some(s) => s
        case None    => CCell(None)
      }
    }

  }

  implicit def optionCellReaderToNoneOptionCellReader[T: CellReader]
    : CellReader[Option[T]] = {
    new CellReader[Option[T]] {

      override def get(
          cellOpt: Option[Cell]): PoiCellContent.CellReadResult[Option[T]] = {
        implicitly[CellReader[T]]
          .get(cellOpt) match {
          case Right(v) =>
            Right(Option(v))
          case Left(_: CellReaderException) =>
            Right(Option.empty)
        }
      }

    }
  }

  implicit def optionCellOperationToNoneOptionCellOpreation[T: CellWriter]
    : CellWriter[Option[T]] = {
    new CellWriter[Option[T]] {

      override def setValue(cell: Cell, value: Option[T]): Boolean = {
        value
          .map { v =>
            implicitly[CellWriter[T]].setValue(cell, v)
          }
          .getOrElse(true)
      }

    }
  }

  implicit def cellFormatterCompose[T: CellWriter: CellReader] = {
    val formatter = new CellFormatter[T] {

      override def get(
          cellOpt: Option[Cell]): PoiCellContent.CellReadResult[T] = {
        implicitly[CellReader[T]]
          .get(cellOpt)
      }
      override def setValue(cell: Cell, value: T): Boolean = {
        implicitly[CellWriter[T]].setValue(cell, value)
      }

    }
    formatter: CellFormatter[T]
  }

  implicit def poiCollectionStringDefaultConvert: CellFormatter[String] =
    new CellFormatter[String] {

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

      override def setValue(cell: Cell, value: String): Boolean = {
        Try {
          cell.setCellValue(value)
        }.map((_: Unit) => true).getOrElse(false)
      }

    }

  implicit def poiCollectionDoubleDefaultConvert: CellFormatter[Double] =
    new CellFormatter[Double] {

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

      override def setValue(cell: Cell, value: Double): Boolean = {
        Try {
          cell.setCellValue(value)
        }.map((_: Unit) => true)
          .getOrElse(false)
      }

    }

  implicit def poiCollectionBooleanDefaultConvert: CellFormatter[Boolean] =
    new CellFormatter[Boolean] {

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

      override def setValue(cell: Cell, value: Boolean): Boolean = {
        Try {
          cell.setCellValue(value)
        }.map((_: Unit) => true)
          .getOrElse(false)
      }

    }

  implicit def poiCollectionDateDefaultConvert: CellFormatter[Date] =
    new CellFormatter[Date] {

      override def get(
          cell: Option[Cell]): PoiCellContent.CellReadResult[Date] = {
        val content = CCell(cell)
        content.dateValue.fold(
          (e: CellReaderException) => Left(new ExcepectDateException(e)),
          date => Right(date)
        )
      }

      override def setValue(cell: Cell, value: Date): Boolean = {
        Try {
          cell.setCellValue(value)
        }.map((_: Unit) => true).getOrElse(false)
      }

    }

}
