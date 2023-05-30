package com.dank.services.user.domain.repository

import com.dank.services.user.api.User

interface UserRepository {
    suspend fun findByUserId(userId: String) : User
}