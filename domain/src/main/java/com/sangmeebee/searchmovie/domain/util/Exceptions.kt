package com.sangmeebee.searchmovie.domain.util

open class InCorrectQueryException : IllegalStateException()
class EmptyQueryException() : InCorrectQueryException()