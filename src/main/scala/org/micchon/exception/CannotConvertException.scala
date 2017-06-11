package org.micchon.exception

class CannotConvertException(
  message: String = null,
  throwable: Throwable = null
) extends RuntimeException(message, throwable)