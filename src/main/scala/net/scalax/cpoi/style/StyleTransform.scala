package net.scalax.cpoi.style

import org.apache.poi.ss.usermodel.{CellStyle, Workbook}

trait StyleTransform {

  def operation(workbook: Workbook, cellStyle: CellStyle): CellStyle

}
