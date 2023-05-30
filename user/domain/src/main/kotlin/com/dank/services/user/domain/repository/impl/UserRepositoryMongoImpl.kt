package com.dank.services.user.domain.repository.impl

import com.dank.services.user.api.User
import com.dank.services.user.domain.repository.UserRepository
import com.dank.services.user.domain.repository.model.UserDbDocument
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo

class UserRepositoryMongoImpl(
    private val mongoOperations: ReactiveMongoOperations,
) : UserRepository {
    override suspend fun findByUserId(userId: String): User {
        val query = Query(UserDbDocument::userId isEqualTo userId)
        return this.mongoOperations
            .findOne(query, UserDbDocument::class.java)
            .map { it.toUser() }
            .awaitLast()
    }
}