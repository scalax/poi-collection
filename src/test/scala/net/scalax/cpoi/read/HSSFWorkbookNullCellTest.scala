package net.scalax.cpoi.test

import java.util.Date

import net.scalax.cpoi.api._
import net.scalax.cpoi.exception.CellNotExistsException
import org.apache.poi.ss.usermodel.Cell
import org.scalatest._

class HSSFWorkbookNullCellTest extends FlatSpec with Matchers {

  "null cell" should "read as empty string by common string reader" in {
    import readers._
    val wrap  = CPoi.wrapCell(null: Cell)
    val value = wrap.tryValue[String]
    value.isRight should be(true)
    value.getOrElse(throw new Exception("Test not pass")) should be("")
  }

  it should "throw exception when read by double reader" in {
    import readers._
    val wrap  = CPoi.wrapCell(null: Cell)
    val value = wrap.tryValue[Double]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read by boolean reader" in {
    import readers._
    val wrap  = CPoi.wrapCell(null: Cell)
    val value = wrap.tryValue[Boolean]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read by date reader" in {
    import readers._
    val wrap  = CPoi.wrapCell(null: Cell)
    val value = wrap.tryValue[Date]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "read as empty string by immutable string reader" in {
    import immutableReaders._
    val wrap  = CPoi.wrapCell(null: Cell)
    val value = wrap.tryValue[String]
    value.isRight should be(true)
    value.getOrElse(throw new Exception("Test not pass")) should be("")
  }

  it should "throw exception when read by non empty string reader" in {
    implicit val ec = readers.nonEmptyStringReader
    val wrap        = CPoi.wrapCell(null: Cell)
    val value       = wrap.tryValue[String]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[CellNotExistsException] should be(true)
  }

  it should "throw exception when read by non blank string reader" in {
    implicit val ec = readers.nonBlankStringReader
    val wrap        = CPoi.wrapCell(null: Cell)
    val value       = wrap.tryValue[String]
    value.isLeft should be(true)
    value.left.getOrElse(throw new Exception("Test not pass")).isInstanceOf[CellNotExistsException] should be(true)
  }

}
