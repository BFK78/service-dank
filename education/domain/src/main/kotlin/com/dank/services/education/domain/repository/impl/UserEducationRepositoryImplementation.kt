package com.dank.services.education.domain.repository.impl

import com.dank.services.education.api.UserEducation
import com.dank.services.education.domain.repository.UserEducationRepository
import com.dank.services.education.domain.repository.model.UserEducationDbDocument
import com.dank.services.user.api.User
import com.dank.services.user.domain.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo

class UserEducationRepositoryImplementation(
    private val mongoOperations: ReactiveMongoOperations,
    private val userRepository: UserRepository
) : UserEducationRepository {

    override suspend fun createEducationHistoryRecord(userEducationHistory: UserEducationDbDocument): UserEducation {
        return this.mongoOperations.save(userEducationHistory).awaitSingle().toUserEducation()
    }

    override suspend fun getAllEducationHistoryRecordByUserId(userId: String): List<UserEducation>? {
        val query = Query(Criteria.where("userId").isEqualTo(userId))
        return mongoOperations.find(query, UserEducationDbDocument::class.java).collectList().awaitSingle()
            .map { it.toUserEducation() }
    }

    override suspend fun getEducationHistoryRecordById(id: String): UserEducation? {

        return mongoOperations.findById(id, UserEducationDbDocument::class.java).awaitSingle().toUserEducation()
    }

    override suspend fun updateEducationHistoryRecord(
        id: String,
        userEducationHistory: UserEducationDbDocument
    ): UserEducation {
        return mongoOperations.save(userEducationHistory).awaitSingle().toUserEducation()
    }

    override suspend fun deleteEducationHistoryRecord(id: String): Boolean {
        val query = Query(Criteria.where("id").isEqualTo(id))
        return mongoOperations.remove(query, UserEducationDbDocument::class.java).awaitFirst().wasAcknowledged()
    }

    override suspend fun getUsersByInstitutionId(
        institutionId: String,
        sortBy: String?,
        sortDirection: String?,
        page: Int?,
        pageSize: Int?
    ): List<User> {
        val query = Query(Criteria.where("institutionId").isEqualTo(institutionId))
        val sort = createSort(sortBy, sortDirection)
        query.with(sort)

        if (page != null && pageSize != null) {
            val skip = (page - 1) * pageSize
            query.skip(skip.toLong()).limit(pageSize)
        }

        val distinctUserIds =
            mongoOperations.findDistinct(query, "userId", UserEducationDbDocument::class.java, String::class.java)
                .collectList()
                .awaitSingle()

        return distinctUserIds.mapNotNull { userId ->
            userRepository.findByUserId(userId)
        }
    }

    private fun createSort(sortBy: String?, sortDirection: String?): Sort {
        val direction = if (sortDirection == "desc") Sort.Direction.DESC else Sort.Direction.ASC
        return Sort.by(direction, sortBy ?: "name")
    }

}