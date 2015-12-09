package org.xarcher.cpoi

import org.apache.poi.ss.usermodel.Workbook

object CPoi {

  def load(workbook: Workbook) = {

    val sheets = for {
      i <- (0 until workbook.getNumberOfSheets).toSet[Int]
      sheet = workbook.getSheetAt(i) if (sheet != null)
    } yield {
      val rows = for {
        k <- (0 to sheet.getLastRowNum).toSet[Int]
        row = sheet.getRow(k) if (row != null)
      } yield {
        val cells = for {
          j <- (0 until row.getLastCellNum).toSet[Int]
          cell = row.getCell(j) if (cell != null)
        } yield {
          CCell(cell)
        }
        CRow(k, cells)

      }
      CSheet(i, sheet.getSheetName, rows)
    }

    CWorkbook(sheets)

  }

}