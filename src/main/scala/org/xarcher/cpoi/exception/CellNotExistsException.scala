package org.xarcher.cpoi

sealed trait CellReaderException extends Exception {}

class CellNotExistsException
    extends Exception("Cell not found.")
    with CellReaderException {}

sealed trait CellTypeNotConfirmException extends CellReaderException {}

class ExcepectFormulaException
    extends Exception("Excepect formula cell.")
    with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExcepectNumericCellException
    extends Exception("Excepect numeric cell.")
    with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExcepectDateException
    extends Exception("Excepect date cell.")
    with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExcepectRichTextException
    extends Exception("Excepect rich text cell.")
    with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExcepectStringCellException
    extends Exception("Excepect string cell.")
    with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExcepectBooleanCellException
    extends Exception("Excepect boolean cell.")
    with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}

class ExcepectErrorCellException
    extends Exception("Excepect error cell.")
    with CellTypeNotConfirmException {
  def this(cause: Throwable) = {
    this()
    initCause(cause)
  }
}
