package com.sangmeebee.searchmovie.remote.util

open class InCorrectQueryException : IllegalStateException()
class EmptyQueryException : InCorrectQueryException()

class HttpConnectionException : IllegalStateException()