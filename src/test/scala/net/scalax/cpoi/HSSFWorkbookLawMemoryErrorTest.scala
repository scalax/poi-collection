package net.scalax.cpoi

import net.scalax.cpoi.content.CCell
import net.scalax.cpoi.exception.CellNotExistsException
import org.scalatest._

class HSSFWorkbookLawMemoryErrorTest extends FlatSpec with Matchers {

  "CellContent" should "throw exception when read an empty cell as string" in {
    val ccell = CCell(Option.empty)
    val value = ccell.stringValue
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read an empty cell as numbric" in {
    val ccell = CCell(Option.empty)
    val value = ccell.doubleValue
    value.isRight should be(false)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read an empty cell as boolean" in {
    val ccell = CCell(Option.empty)
    val value = ccell.booleanValue
    value.isRight should be(false)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "read as date" in {
    val ccell = CCell(Option.empty)
    val value = ccell.dateValue
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

}
