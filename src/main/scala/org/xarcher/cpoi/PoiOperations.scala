package org.xarcher.cpoi

import java.util.{Calendar, Date}

import org.apache.poi.ss.usermodel.Cell
import org.joda.time.DateTime

/**
  * Created by djx314 on 15-8-22.
  */
trait PoiOperations {

  implicit class CellContentOptExtensionMethon(cellOpt: Option[CellContent]) {

    def openAlways: CellContent = {
      cellOpt match {
        case Some(s) => s
        case None    => CCell(None)
      }
    }

  }

  import scala.util.control.Exception._

  implicit def optionCellReaderToNoneOptionCellReader[T:CellReader]
    : CellReader[Option[T]] = {
    new CellReader[Option[T]] {
      override def get(cellOpt: Option[Cell]): PoiCellContent.CellReadResult[Option[T]] = {
        implicitly[CellReader[T]].get(cellOpt).right.map(s => Option(s)).left.map(e => Option.empty)
      }
    }
  }

  implicit def optionCellOperationToNoneOptionCellOpreation[
      T: CellWriter]: CellWriter[Option[T]] = {
    new CellWriter[Option[T]] {
      override def set(value: Option[T], cell: Option[Cell]): Boolean = {
        implicitly[CellWriter[T]].set(value, cell)
      }
    }
  }

  implicit def poiCollectionStringDefaultConvert: CellContentOperation[String] =
    new CellContentOperation[String] {

      override def contentGet(cell: CellContent): Option[String] = {
        if (cell.isDefined)
          cell.stringValue
            .fold(cell.numericValue.map(_.toString))(Option(_))
            .fold(cell.dateValue.map(_.toString))(Option(_))
            .fold(cell.booleanValue.map(_.toString))(Option(_))
            .fold(cell.richTextStringValue.map(_.toString))(Option(_))
        else
          None
      }

      override def notNullSet(value: String, cell: Cell): Unit = {
        //throw new Exception("此方法不应该被使用")
        cell.setCellValue(value)
      }
      /*override def set(value: Option[String], cell: Option[Cell], style: Option[CellStyle] = None): Boolean = {
      allCatch.opt {
        for {
          value1 <- value
          cell1 <- cell
        } yield {
          //不能设置 dataFormat，因为如果 style 被复用会乱
          style.map(style1 => {
            //style1.setDataFormat(textFormatIndex)
            cell1.setCellStyle(style1)
          })
          cell1.setCellValue(value1)
          true
        }
      }.flatten.getOrElse(false)
    }*/

    }

  implicitly[ReadableCellOperationAbs[Option[String]]]

  implicit def poiCollectionDoubleDefaultConvert: CellContentOperation[Double] =
    new CellContentOperation[Double] {

      override def contentGet(cell: CellContent): Option[Double] = {
        if (cell.isDefined) {
          cell.numericValue.fold(cell.stringValue.flatMap(s =>
            allCatch.opt(s.toDouble)))(s => Option(s.toDouble))
        } else
          None
      }
      override def notNullSet(value: Double, cell: Cell): Unit = {
        cell.setCellValue(value)
      }
    }

  implicit def poiCollectionBooleanDefaultConvert
    : CellContentOperation[Boolean] = new CellContentOperation[Boolean] {

    override def contentGet(cell: CellContent): Option[Boolean] = {
      if (cell.isDefined) {
        cell.booleanValue.fold(cell.stringValue.flatMap(s =>
          allCatch.opt(s.toBoolean)))(Option(_))
      } else
        None
    }
    override def notNullSet(value: Boolean, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionIntDefaultConvert: CellContentOperation[Int] =
    new CellContentOperation[Int] {

      override def contentGet(cell: CellContent): Option[Int] = {
        if (cell.isDefined) {
          cell.numericValue
            .flatMap(s => allCatch.opt(s.toInt))
            .fold(cell.stringValue.flatMap(s => {
              allCatch.opt(s.toInt)
            }))(Option(_))
        } else
          None
      }
      override def notNullSet(value: Int, cell: Cell): Unit = {
        cell.setCellValue(value)
  c     }
    }

  implicit def poiCollectionLongDefaultConvert: CellContentOperation[Long] =
    new CellContentOperation[Long] {

      override def contentGet(cell: CellContent): Option[Long] = {
        if (cell.isDefined) {
          cell.numericValue
            .flatMap(s => allCatch.opt(s.toLong))
            .fold(cell.stringValue.flatMap(s => allCatch.opt(s.toLong)))(
              Option(_))
        } else
          None
      }
      override def notNullSet(value: Long, cell: Cell): Unit = {
        cell.setCellValue(value)
      }
    }

  implicit def poiCollectionShortDefaultConvert: CellContentOperation[Short] =
    new CellContentOperation[Short] {

      override def contentGet(cell: CellContent): Option[Short] = {
        if (cell.isDefined) {
          cell.numericValue
            .flatMap(s => allCatch.opt(s.toShort))
            .fold(cell.stringValue.flatMap(s => allCatch.opt(s.toShort)))(
              Option(_))
        } else
          None
      }
      override def notNullSet(value: Short, cell: Cell): Unit = {
        cell.setCellValue(value)
      }
    }

  implicit def poiCollectionBigIntDefaultConvert: CellContentOperation[BigInt] =
    new CellContentOperation[BigInt] {

      override def contentGet(cell: CellContent): Option[BigInt] = {
        if (cell.isDefined) {
          cell.numericValue
            .flatMap(s => allCatch.opt(BigInt(s.toLong)))
            .fold(cell.stringValue.flatMap(s => allCatch.opt(BigInt(s))))(
              Option(_))
        } else
          None
      }
      override def notNullSet(value: BigInt, cell: Cell): Unit = {
        cell.setCellValue(value.toLong)
      }
    }

  def bigDecimalOperation: Option[Int] => CellContentOperation[BigDecimal] =
    scale =>
      new CellContentOperation[BigDecimal] {

        override def contentGet(cell: CellContent): Option[BigDecimal] = {
          if (cell.isDefined) {
            val dataOpt = cell.numericValue.fold(cell.stringValue.flatMap(s =>
              allCatch.opt(BigDecimal(s))))(Option(_))
            dataOpt.map(s => {
              scale
                .map(t => s.setScale(t, BigDecimal.RoundingMode.HALF_UP))
                .getOrElse(s)
            })
          } else
            None
        }
        override def notNullSet(value: BigDecimal, cell: Cell): Unit = {
          val valueToInsert = scale
            .map(t => value.setScale(t, BigDecimal.RoundingMode.HALF_UP))
            .getOrElse(value)
          cell.setCellValue(valueToInsert.toDouble)
        }
    }

  implicit def poiCollectionDateDefaultConvert: CellContentOperation[Date] =
    new CellContentOperation[Date] {

      override def contentGet(cell: CellContent): Option[Date] = {
        cell.dateValue
      }
      override def notNullSet(value: Date, cell: Cell): Unit = {
        cell.setCellValue(value)
      }
    }

  implicit def poiCollectionSqlDateDefaultConvert
    : CellContentOperation[java.sql.Date] =
    new CellContentOperation[java.sql.Date] {

      override def contentGet(cell: CellContent): Option[java.sql.Date] = {
        cell.dateValue.map(s => new java.sql.Date(s.getTime))
      }
      override def notNullSet(value: java.sql.Date, cell: Cell): Unit = {
        cell.setCellValue(value)
      }
    }

  implicit def poiCollectionSqlTimeDefaultConvert
    : CellContentOperation[java.sql.Time] =
    new CellContentOperation[java.sql.Time] {

      override def contentGet(cell: CellContent): Option[java.sql.Time] = {
        cell.dateValue.map(s => new java.sql.Time(s.getTime))
      }
      override def notNullSet(value: java.sql.Time, cell: Cell): Unit = {
        cell.setCellValue(value)
      }
    }

  implicit def poiCollectionSqlTimestampDefaultConvert
    : CellContentOperation[java.sql.Timestamp] =
    new CellContentOperation[java.sql.Timestamp] {

      override def contentGet(cell: CellContent): Option[java.sql.Timestamp] = {
        cell.dateValue.map(s => new java.sql.Timestamp(s.getTime))
      }
      override def notNullSet(value: java.sql.Timestamp, cell: Cell): Unit = {
        cell.setCellValue(value)
      }
    }

  implicit def poiCollectionDateTimeDefaultConvert
    : CellContentOperation[DateTime] = new CellContentOperation[DateTime] {

    override def contentGet(cell: CellContent): Option[DateTime] = {
      cell.dateValue.flatMap { s =>
        Option(s).map { t =>
          new DateTime(t.getTime)
        }

      }
    }
    override def notNullSet(value: DateTime, cell: Cell): Unit = {
      cell.setCellValue(value.toDate)
    }
  }

  implicit def poiCollectionCalendarDefaultConvert
    : CellContentOperation[Calendar] = new CellContentOperation[Calendar] {

    override def contentGet(cell: CellContent): Option[Calendar] = {
      cell.dateValue.map(s => {
        val calendar = Calendar.getInstance
        calendar.setTime(s)
        calendar
      })
    }
    override def notNullSet(value: Calendar, cell: Cell): Unit = {
      cell.setCellValue(value.getTime)
    }
  }

}
