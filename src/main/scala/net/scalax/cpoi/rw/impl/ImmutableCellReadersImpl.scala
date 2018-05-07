package net.scalax.cpoi.rw

import net.scalax.cpoi.exception._
import org.apache.poi.ss.usermodel.{Cell, CellType}

trait ImmutableCellReadersImpl extends CellReadersImpl {

  override def stringReader: CellReader[String] = new CellReader[String] {
    self =>

    override def get(cell: Option[Cell]): CellReader.CellReadResult[String] = {
      cell match {
        case Some(c) =>
          c.getCellTypeEnum match {
            case CellType.BLANK =>
              Right(c.getStringCellValue)
            case CellType.STRING =>
              Right(c.getStringCellValue)
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

}
