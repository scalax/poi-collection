package net.scalax.cpoi.exception

sealed trait CellReaderException extends Exception {}

trait CellNotConfirmException extends CellReaderException {}

class CellNotExistsException extends Exception("Cell not found.") with CellReaderException {}

sealed trait CellTypeNotConfirmException extends CellReaderException {}

class ExpectFormulaException extends Exception("Expect formula cell.") with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExpectNumericCellException extends Exception("Expect numeric cell.") with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExpectDateException extends Exception("Expect date cell.") with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExpectRichTextException extends Exception("Expect rich text cell.") with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExpectStringCellException extends Exception("Expect string cell.") with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExpectBooleanCellException extends Exception("Expect boolean cell.") with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExpectErrorCellException extends Exception("Expect error cell.") with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}
