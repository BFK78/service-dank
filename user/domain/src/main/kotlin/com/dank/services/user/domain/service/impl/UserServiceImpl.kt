package com.dank.services.user.domain.service.impl

import com.dank.services.user.api.User
import com.dank.services.user.domain.repository.UserRepository
import com.dank.services.user.domain.service.UserService

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService{
    override suspend fun getByUserId(userId: String): User {
        return this.userRepository.findByUserId(userId = userId)
    }
}