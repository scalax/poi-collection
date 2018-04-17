package net.scalax.cpoi

import java.util.Date

import net.scalax.cpoi.exception.CellNotExistsException
import net.scalax.cpoi.style.CPoiUtils
import org.scalatest._

class HSSFWorkbookLawMemoryErrorTest extends FlatSpec with Matchers {

  import readers._

  "CellContent" should "throw exception when read an empty cell as string" in {
    val ccell = CPoiUtils.wrapCell(Option.empty)
    val value = ccell.tryValue[String]
    value.isRight should be(true)
    value.right.get should be("")
  }

  it should "throw exception when read an empty cell as numbric" in {
    val ccell = CPoiUtils.wrapCell(Option.empty)
    val value = ccell.tryValue[Double]
    value.isRight should be(false)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read an empty cell as boolean" in {
    val ccell = CPoiUtils.wrapCell(Option.empty)
    val value = ccell.tryValue[Boolean]
    value.isRight should be(false)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "read as date" in {
    val ccell = CPoiUtils.wrapCell(Option.empty)
    val value = ccell.tryValue[Date]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

}
