package net.scalax.cpoi.rw

import java.util.Date

import cats.ApplicativeError
import net.scalax.cpoi.exception._
import net.scalax.cpoi._
import org.apache.poi.ss.usermodel.{Cell, CellType}
import cats.implicits._

trait ImmutableCellReadersImpl extends CellReadersImpl {

  override def stringReader: CellReader[String] = new CellReader[String] {
    override def get(cell: Option[Cell]): CellReadResult[String] = {
      cell match {
        case Some(c) =>
          c.getCellTypeEnum match {
            case CellType.BLANK =>
              Right(c.getStringCellValue)
            case CellType.STRING =>
              Right(c.getStringCellValue)
            case _ =>
              Left(new ExcepectStringCellException())
          }
        case _ =>
          //read null as empty cell
          Right("")
      }
    }
  }

}
