package org.xarcher.cpoi

import scala.language.existentials
import scala.language.implicitConversions

sealed trait CellReaderException {
  self: Throwable =>
}

class CellNotExistsException
    extends Exception("Cell not found.")
    with CellReaderException {}

sealed trait CellTypeNotConfirmException extends CellReaderException {
  self: Throwable =>
}

class ExcepectFormulaException(cause: Throwable)
    extends Exception("Excepect formula cell.", cause)
    with CellTypeNotConfirmException {}

class ExcepectNumericCellException(cause: Throwable)
    extends Exception("Excepect numeric cell.", cause)
    with CellTypeNotConfirmException {}

class ExcepectDateException(cause: Throwable)
    extends Exception("Excepect date cell.", cause)
    with CellTypeNotConfirmException {}

class ExcepectRichTextException(cause: Throwable)
    extends Exception("Excepect rich text cell.", cause)
    with CellTypeNotConfirmException {}

class ExcepectStringCellException(cause: Throwable)
    extends Exception("Excepect string cell.", cause)
    with CellTypeNotConfirmException {}

class ExcepectBooleanCellException(cause: Throwable)
    extends Exception("Excepect boolean cell.", cause)
    with CellTypeNotConfirmException {}

class ExcepectErrorCellException(cause: Throwable)
    extends Exception("Excepect error cell.", cause)
    with CellTypeNotConfirmException {}
