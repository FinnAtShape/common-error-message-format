package com.shapegames.common.errors.model

/**
 * Some common error codes. Services are allowed to come up with their own error codes.
 */
object CommonErrorCodes {
    const val NOT_FOUND = "NOT_FOUND"
    const val VALIDATION_ERRORS = "VALIDATION_ERRORS"
    const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    const val INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR"
}
