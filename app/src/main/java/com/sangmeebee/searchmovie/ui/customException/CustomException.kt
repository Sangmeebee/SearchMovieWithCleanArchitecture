package com.sangmeebee.searchmovie.ui.customException

open class InCorrectQueryException : IllegalStateException()
class EmptyQueryException() : InCorrectQueryException()