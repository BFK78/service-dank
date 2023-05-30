package com.dank.services.user.routing

import com.dank.services.user.api.User
import com.dank.services.user.domain.service.UserService
import kotlinx.coroutines.reactor.mono
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1/users")
class UserRoutingController(
    private val userService: UserService
) {
    @GetMapping("{userId}")
    fun getByUserId(
        @PathVariable userId: String,
    ): Mono<User> = mono {
        userService.getByUserId(userId)
    }
}