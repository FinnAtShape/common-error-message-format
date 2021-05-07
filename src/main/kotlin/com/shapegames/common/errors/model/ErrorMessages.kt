package com.shapegames.common.errors.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import java.time.OffsetDateTime
import java.time.ZoneOffset

private fun nowInUtc(): OffsetDateTime = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC)

/**
 * Base class for error messages.
 */
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes(
    value = [
        Type(value = EmptyErrorMessage::class, name = "empty"),
        Type(value = SimpleTextErrorMessage::class, name = "simple_message"),
        Type(value = ValidationErrorMessage::class, name = "validation_error"),
        Type(value = ServiceCallErrorMessage::class, name = "service_call")
    ]
)
@JsonInclude(JsonInclude.Include.NON_NULL)
sealed class BaseErrorMessage {
    @get:JsonProperty("timestamp") abstract val timestamp: OffsetDateTime
    @get:JsonProperty("error_code") abstract val errorCode: String
}

/**
 * When the is no additional error information besides the error code.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmptyErrorMessage @JsonCreator constructor(
    override val timestamp: OffsetDateTime = nowInUtc(),
    override val errorCode: String
) : BaseErrorMessage()

/**
 * A simple message.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SimpleTextErrorMessage @JsonCreator constructor(
    override val timestamp: OffsetDateTime = nowInUtc(),
    override val errorCode: String,
    @field:JsonProperty("message") val message: String
) : BaseErrorMessage()

/**
 * Validation errors.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ValidationErrorMessage @JsonCreator constructor(
    override val timestamp: OffsetDateTime = nowInUtc(),
    override val errorCode: String = CommonErrorCodes.VALIDATION_ERRORS,
    val message: String? = null,
    val cause: String? = null, // name of the exception that is the cause of the errors
    val errors: List<ValidationError>
) : BaseErrorMessage() {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class ValidationError @JsonCreator constructor(
        val field: String,
        val code: String? = null,
        val type: String? = null,
        val message: String
    )
}

/**
 * Calls to internal service.
 */
data class ServiceCallErrorMessage(
    override val timestamp: OffsetDateTime = nowInUtc(),
    override val errorCode: String = CommonErrorCodes.VALIDATION_ERRORS,
    val serviceName: String,
    val nestedError: BaseErrorMessage
) : BaseErrorMessage()
