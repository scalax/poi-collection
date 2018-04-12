package org.xarcher.cpoi

import java.util.Date

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.scalatest._

class LawMemoryErrorTest extends FlatSpec with Matchers {

  object PoiOperations extends PoiOperations

  import PoiOperations._

  "CellContent" should "throw exception when read an empty cell as string" in {
    val ccell = CCell(Option.empty)
    val value = ccell.stringValue
    value.isLeft should be (true)
    value.left.get.isInstanceOf[CellNotExistsException] should be (true)
  }

  it should "throw exception when read an empty cell as numbric" in {
    val ccell = CCell(Option.empty)
    val value = ccell.doubleValue
    value.isRight should be(false)
    value.left.get.isInstanceOf[CellNotExistsException] should be (true)
  }

  it should "throw exception when read an empty cell as boolean" in {
    val ccell = CCell(Option.empty)
    val value = ccell.booleanValue
    value.isRight should be(false)
    value.left.get.isInstanceOf[CellNotExistsException] should be (true)
  }

  it should "read as date" in {
    val ccell = CCell(Option.empty)
    val value = ccell.dateValue
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be (true)
  }

}
