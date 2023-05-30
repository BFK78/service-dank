package com.dank.services.user.domain.service

import com.dank.services.user.api.User

interface UserService {
    suspend fun getByUserId(userId: String) : User
}