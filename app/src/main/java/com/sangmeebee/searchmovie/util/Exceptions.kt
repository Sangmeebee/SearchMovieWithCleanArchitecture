package com.sangmeebee.searchmovie.util

open class InCorrectQueryException : IllegalStateException()
class EmptyQueryException() : InCorrectQueryException()