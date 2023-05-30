package com.dank.services.user.domain.repository.model

import com.dank.services.user.api.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("user")
data class UserDbDocument(
    @Id val userId: String
) {
    fun toUser() = User(userId = this.userId)
}