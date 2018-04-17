package net.scalax.cpoi

import java.util.Date

import net.scalax.cpoi.exception.CellNotExistsException
import net.scalax.cpoi.style.CPoiUtils
import org.apache.poi.ss.usermodel.Cell
import org.scalatest._

class HSSFWorkbookLawMemoryErrorTest extends FlatSpec with Matchers {

  "null cell" should "read as empty string by common string reader" in {
    import readers._
    val wrap = CPoiUtils.wrapCell(null: Cell)
    val value = wrap.tryValue[String]
    value.isRight should be(true)
    value.right.get should be("")
  }

  it should "throw exception when read by double reader" in {
    import readers._
    val wrap = CPoiUtils.wrapCell(null: Cell)
    val value = wrap.tryValue[Double]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read by boolean reader" in {
    import readers._
    val wrap = CPoiUtils.wrapCell(null: Cell)
    val value = wrap.tryValue[Boolean]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read by date reader" in {
    import readers._
    val wrap = CPoiUtils.wrapCell(null: Cell)
    val value = wrap.tryValue[Date]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "read as empty string by immutable string reader" in {
    import immutableReaders._
    val wrap = CPoiUtils.wrapCell(null: Cell)
    val value = wrap.tryValue[String]
    value.isRight should be(true)
    value.right.get should be("")
  }

  it should "throw exception when read by non blank string reader" in {
    implicit val ec = readers.nonBlankStringReader
    val wrap = CPoiUtils.wrapCell(null: Cell)
    val value = wrap.tryValue[String]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read by non empty string reader" in {
    implicit val ec = readers.nonEmptyStringReader
    val wrap = CPoiUtils.wrapCell(null: Cell)
    val value = wrap.tryValue[String]
    value.isLeft should be(true)
    value.left.get.isInstanceOf[CellNotExistsException] should be(true)
  }

}
