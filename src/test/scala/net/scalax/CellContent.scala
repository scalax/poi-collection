package org.xarcher.cpoi

import java.util.Date

import org.apache.poi.ss.usermodel.{Cell, CellStyle, CellType, RichTextString}

import scala.util.Try
import org.scalatest._

class ExampleSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    1 should be (2)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
  }

}