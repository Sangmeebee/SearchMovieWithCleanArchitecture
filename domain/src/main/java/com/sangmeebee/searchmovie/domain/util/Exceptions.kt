package com.sangmeebee.searchmovie.domain.util

open class InCorrectQueryException : IllegalStateException()
class EmptyQueryException() : InCorrectQueryException()

class HttpConnectionException : IllegalStateException()

class GetBookmarkException : IllegalStateException()
class BookmarkException : IllegalStateException()
class UnBookmarkException : IllegalStateException()