package com.shapegames.common.errors.model

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
        Type(value = SimpleTextErrorMessage::class, name = "simple_message")
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
data class EmptyErrorMessage(
    override val timestamp: OffsetDateTime = nowInUtc(),
    override val errorCode: String
) : BaseErrorMessage()

/**
 * A simple message.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SimpleTextErrorMessage(
    override val timestamp: OffsetDateTime = nowInUtc(),
    override val errorCode: String,
    @field:JsonProperty("message") val message: String
) : BaseErrorMessage()
