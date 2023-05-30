package com.dank.services.webserver

import com.dank.services.education.domain.repository.UserEducationRepository
import com.dank.services.education.domain.repository.impl.UserEducationRepositoryImplementation
import com.dank.services.education.domain.service.UserEducationService
import com.dank.services.education.domain.service.impl.UserEducationServiceImplementation
import com.dank.services.user.domain.repository.UserRepository
import com.dank.services.user.domain.repository.impl.UserRepositoryMongoImpl
import com.dank.services.user.domain.service.UserService
import com.dank.services.user.domain.service.impl.UserServiceImpl
import com.dank.services.user.routing.UserEducationController
import com.dank.services.user.routing.UserRoutingController
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

@SpringBootApplication
class ServicesApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ServicesApplication>(*args)
        }
    }

    /* ***********************************************
    *
    * Infra
    *
    * ***********************************************/

    @Bean
    fun getPrimaryMongoClient(
    ): MongoClient {
        val connectionString = "mongodb://localhost:27017"
        val mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(ConnectionString(connectionString))
                .build()
        return MongoClients.create(mongoClientSettings)
    }

    @Bean
    fun getPrimaryMongoUserOperations(
            @Autowired mongoClient: MongoClient
    ): ReactiveMongoOperations {
        return ReactiveMongoTemplate(
                mongoClient,
                "dankUserDev"
        )
    }

    /* ***********************************************
    *
    * Repositories
    *
    * ***********************************************/

    @Bean
    fun getUserRepository(
            @Autowired mongoOperations: ReactiveMongoOperations
    ): UserRepository {
        return UserRepositoryMongoImpl(
                mongoOperations = mongoOperations
        )
    }

    @Bean
    fun getUserEducationRepository(
            @Autowired mongoOperations: ReactiveMongoOperations
    ): UserEducationRepository {
        return UserEducationRepositoryImplementation(
                mongoOperations = mongoOperations
        )
    }

    /* ***********************************************
    *
    * Services
    *
    * ***********************************************/

    @Bean
    fun getUserService(
            @Autowired userRepository: UserRepository
    ): UserService {
        return UserServiceImpl(
                userRepository = userRepository
        )
    }

    @Bean
    fun getUserEducationService(
            @Autowired userEducationRepository: UserEducationRepository
    ): UserEducationService {
        return UserEducationServiceImplementation(userEducationRepository = userEducationRepository)
    }

    /* ***********************************************
    *
    * Controllers
    *
    * ***********************************************/

    @Bean
    fun getUserRoutingController(
            @Autowired userService: UserService
    ): UserRoutingController {
        return UserRoutingController(
                userService = userService
        )
    }

    @Bean
    fun getUserEducationRoutingController(
            @Autowired userEducationService: UserEducationService
    ): UserEducationController {
        return UserEducationController(
                userEducationService = userEducationService
        )
    }
}






