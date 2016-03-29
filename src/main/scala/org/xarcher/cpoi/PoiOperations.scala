package org.xarcher.cpoi

import java.util.{Calendar, Date}

import org.apache.poi.ss.usermodel.{Cell, CellStyle}
import org.joda.time.DateTime

import scala.reflect.runtime.universe._

/**
 * Created by djx314 on 15-8-22.
 */
trait PoiOperations {

  implicit class CellContentOptExtensionMethon(cellOpt: Option[CellContent]) {

    def openAlways = {
      cellOpt match {
        case Some(s) => s
        case None => new CCell(None)
      }
    }

  }

  import scala.util.control.Exception._

  implicit def optionCellOperationToNoneOptionCellOpreation[T : WriteableCellOperationAbs](implicit weakTypeTag: WeakTypeTag[Option[T]]): WriteableCellOperationAbs[Option[T]] = {
    new WriteableCellOperationAbs[Option[T]] {
      override type DataType = Option[T]
      override val typeTag = weakTypeTag
      override def set(value: Option[DataType], cell: Option[Cell], style: Option[CellStyle] = None): Boolean = {
        implicitly[WriteableCellOperationAbs[T]].set(value.flatten, cell, style)
      }
    }
  }

  implicit def poiCollectionStringDefaultConvert = new CellContentOperation[String] {

    override def contentGet(cell: CellContent): Option[String] = {
      cell.stringValue
      .fold(cell.numericValue.map(_.toString))(Option(_))
      .fold(cell.dateValue.map(_.toString))(Option(_))
      .fold(cell.booleanValue.map(_.toString))(Option(_))
      .fold(cell.richTextStringValue.map(_.toString))(Option(_))
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

  implicit def poiCollectionDoubleDefaultConvert = new CellContentOperation[Double] {

    override def contentGet(cell: CellContent): Option[Double] = {
      cell.numericValue.fold(cell.stringValue.flatMap(s => allCatch.opt(s.toDouble)))(s => Option(s.toDouble))
    }
    override def notNullSet(value: Double, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionBooleanDefaultConvert = new CellContentOperation[Boolean] {

    override def contentGet(cell: CellContent): Option[Boolean] = {
      cell.booleanValue.fold(cell.stringValue.flatMap(s => allCatch.opt(s.toBoolean)))(Option(_))
    }
    override def notNullSet(value: Boolean, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionIntDefaultConvert = new CellContentOperation[Int] {

    override def contentGet(cell: CellContent): Option[Int] = {
      cell.numericValue.flatMap(s => allCatch.opt(s.toInt)).fold(cell.stringValue.flatMap(s => {
        allCatch.opt(s.toInt)
      }))(Option(_))
    }
    override def notNullSet(value: Int, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionLongDefaultConvert = new CellContentOperation[Long] {

    override def contentGet(cell: CellContent): Option[Long] = {
      cell.numericValue.flatMap(s => allCatch.opt(s.toLong)).fold(cell.stringValue.flatMap(s => allCatch.opt(s.toLong)))(Option(_))
    }
    override def notNullSet(value: Long, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionShortDefaultConvert = new CellContentOperation[Short] {

    override def contentGet(cell: CellContent): Option[Short] = {
      cell.numericValue.flatMap(s => allCatch.opt(s.toShort)).fold(cell.stringValue.flatMap(s => allCatch.opt(s.toShort)))(Option(_))
    }
    override def notNullSet(value: Short, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionBigIntDefaultConvert = new CellContentOperation[BigInt] {

    override def contentGet(cell: CellContent): Option[BigInt] = {
      cell.numericValue.flatMap(s => allCatch.opt(BigInt(s.toLong))).fold(cell.stringValue.flatMap(s => allCatch.opt(BigInt(s))))(Option(_))
    }
    override def notNullSet(value: BigInt, cell: Cell): Unit = {
      cell.setCellValue(value.toLong)
    }
  }

  def bigDecimalOperation: Option[Int] => CellContentOperation[BigDecimal] = scale => new CellContentOperation[BigDecimal] {

    override def contentGet(cell: CellContent): Option[BigDecimal] = {
      val dataOpt = cell.numericValue.fold(cell.stringValue.flatMap(s => allCatch.opt(BigDecimal(s))))(Option(_))
      dataOpt.map(s => {
        scale
          .map(t => s.setScale(t, BigDecimal.RoundingMode.HALF_UP))
          .getOrElse(s)
      })
    }
    override def notNullSet(value: BigDecimal, cell: Cell): Unit = {
      val valueToInsert = scale
        .map(t => value.setScale(t, BigDecimal.RoundingMode.HALF_UP))
        .getOrElse(value)
      cell.setCellValue(valueToInsert.toDouble)
    }
  }

  implicit def poiCollectionDateDefaultConvert = new CellContentOperation[Date] {

    override def contentGet(cell: CellContent): Option[Date] = {
      cell.dateValue
    }
    override def notNullSet(value: Date, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionSqlDateDefaultConvert = new CellContentOperation[java.sql.Date] {

    override def contentGet(cell: CellContent): Option[java.sql.Date] = {
      cell.dateValue.map(s => new java.sql.Date(s.getTime))
    }
    override def notNullSet(value: java.sql.Date, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionSqlTimeDefaultConvert = new CellContentOperation[java.sql.Time] {

    override def contentGet(cell: CellContent): Option[java.sql.Time] = {
      cell.dateValue.map(s => new java.sql.Time(s.getTime))
    }
    override def notNullSet(value: java.sql.Time, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionSqlTimestampDefaultConvert = new CellContentOperation[java.sql.Timestamp] {

    override def contentGet(cell: CellContent): Option[java.sql.Timestamp] = {
      cell.dateValue.map(s => new java.sql.Timestamp(s.getTime))
    }
    override def notNullSet(value: java.sql.Timestamp, cell: Cell): Unit = {
      cell.setCellValue(value)
    }
  }

  implicit def poiCollectionDateTimeDefaultConvert = new CellContentOperation[DateTime] {

    override def contentGet(cell: CellContent): Option[DateTime] = {
      cell.dateValue.flatMap { s =>
        Option(s).map { t => new DateTime(t.getTime) }

      }
    }
    override def notNullSet(value: DateTime, cell: Cell): Unit = {
      cell.setCellValue(value.toDate)
    }
  }

  implicit def poiCollectionCalendarDefaultConvert = new CellContentOperation[Calendar] {

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