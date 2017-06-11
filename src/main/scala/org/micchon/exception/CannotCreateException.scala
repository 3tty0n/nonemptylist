package org.micchon.exception

class CannotCreateException(
  message: String = null,
  throwable: Throwable = null
) extends RuntimeException(message, throwable)