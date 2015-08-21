package org.xarcher

import java.util.{Calendar, Date}
import org.joda.time.DateTime

/**
 * Created by djx314 on 15-8-22.
 */
package object poic {

  import scala.util.control.Exception._

  implicit object poiCollectionStringDefaultConvert extends DefaultCellConvert[String] {
    override val convert: CCellAbs => Option[String] = cell => cell.stringValue
  }

  implicit object poiCollectionDoubleDefaultConvert extends DefaultCellConvert[Double] {
    override val convert: CCellAbs => Option[Double] = cell => {
      cell.numericValue.fold(cell.stringValue.flatMap(s => allCatch.opt(s.toDouble)))(Option(_))
    }
  }

  implicit object poiCollectionBooleanDefaultConvert extends DefaultCellConvert[Boolean] {
    override val convert: CCellAbs => Option[Boolean] = cell => {
      cell.booleanValue.fold(cell.stringValue.flatMap(s => allCatch.opt(s.toBoolean)))(Option(_))
    }
  }

  implicit object poiCollectionIntDefaultConvert extends DefaultCellConvert[Int] {
    override val convert: CCellAbs => Option[Int] = cell => {
      cell.numericValue.flatMap(s => allCatch.opt(s.toInt)).fold(cell.stringValue.flatMap(s => allCatch.opt(s.toInt)))(Option(_))
    }
  }

  implicit object poiCollectionLongDefaultConvert extends DefaultCellConvert[Long] {
    override val convert: CCellAbs => Option[Long] = cell => {
      cell.numericValue.flatMap(s => allCatch.opt(s.toLong)).fold(cell.stringValue.flatMap(s => allCatch.opt(s.toLong)))(Option(_))
    }
  }

  implicit object poiCollectionShortDefaultConvert extends DefaultCellConvert[Short] {
    override val convert: CCellAbs => Option[Short] = cell => {
      cell.numericValue.flatMap(s => allCatch.opt(s.toShort)).fold(cell.stringValue.flatMap(s => allCatch.opt(s.toShort)))(Option(_))
    }
  }

  implicit object poiCollectionBigIntDefaultConvert extends DefaultCellConvert[BigInt] {
    override val convert: CCellAbs => Option[BigInt] = cell => {
      cell.numericValue.flatMap(s => allCatch.opt(BigInt(s.toLong))).fold(cell.stringValue.flatMap(s => allCatch.opt(BigInt(s))))(Option(_))
    }
  }

  implicit object poiCollectionBigDecimalDefaultConvert extends DefaultCellConvert[BigDecimal] {
    override val convert: CCellAbs => Option[BigDecimal] = cell => {
      cell.numericValue.flatMap(s => allCatch.opt(BigDecimal(s))).fold(cell.stringValue.flatMap(s => allCatch.opt(BigDecimal(s))))(Option(_))
    }
  }

  implicit object poiCollectionDateDefaultConvert extends DefaultCellConvert[Date] {
    override val convert: CCellAbs => Option[Date] = cell => {
      cell.dateValue
    }
  }

  implicit object poiCollectionSqlDateDefaultConvert extends DefaultCellConvert[java.sql.Date] {
    override val convert: CCellAbs => Option[java.sql.Date] = cell => {
      cell.dateValue.map(s => new java.sql.Date(s.getTime))
    }
  }

  implicit object poiCollectionSqlTimeDefaultConvert extends DefaultCellConvert[java.sql.Time] {
    override val convert: CCellAbs => Option[java.sql.Time] = cell => {
      cell.dateValue.map(s => new java.sql.Time(s.getTime))
    }
  }

  implicit object poiCollectionSqlTimestampDefaultConvert extends DefaultCellConvert[java.sql.Timestamp] {
    override val convert: CCellAbs => Option[java.sql.Timestamp] = cell => {
      cell.dateValue.map(s => new java.sql.Timestamp(s.getTime))
    }
  }

  implicit object poiCollectionDateTimeDefaultConvert extends DefaultCellConvert[DateTime] {
    override val convert: CCellAbs => Option[DateTime] = cell => {
      cell.dateValue.map(s => new DateTime(s.getTime))
    }
  }

  implicit object poiCollectionCalendarDefaultConvert extends DefaultCellConvert[Calendar] {
    override val convert: CCellAbs => Option[Calendar] = cell => {
      cell.dateValue.map(s => {
        val calendar = Calendar.getInstance
        calendar.setTime(s)
        calendar
      })
    }
  }

}
