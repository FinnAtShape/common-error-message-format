package com.shapegames.common.errors.model

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import java.time.OffsetDateTime
import java.time.ZoneOffset

private fun nowInUtc(): OffsetDateTime = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC)

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
sealed class BaseErrorMessage {
    abstract val timestamp: OffsetDateTime
    abstract val errorCode: String
}

data class SimpleErrorMessage(
    override val timestamp: OffsetDateTime = nowInUtc(),
    override val errorCode: String
) : BaseErrorMessage()
