package com.dank.services.education.domain.service.impl

import com.dank.services.education.api.UserEducation
import com.dank.services.education.domain.repository.UserEducationRepository
import com.dank.services.education.domain.repository.model.UserEducationDbDocument
import com.dank.services.education.domain.service.UserEducationService
import com.dank.services.user.api.User

class UserEducationServiceImplementation(
    private val userEducationRepository: UserEducationRepository
) : UserEducationService {

    override suspend fun createEducationHistoryRecord(userEducationHistory: UserEducation): UserEducation {
        val userEducationDbDocument = UserEducationDbDocument(
            publicId = userEducationHistory.publicId,
            userId = userEducationHistory.userId,
            institutionId = userEducationHistory.institutionId,
            degree = userEducationHistory.degree
        )
        return userEducationRepository.createEducationHistoryRecord(userEducationHistory = userEducationDbDocument)
    }

    override suspend fun getAllEducationHistoryRecordByUserId(userId: String): List<UserEducation>? {
        return userEducationRepository.getAllEducationHistoryRecordByUserId(userId = userId)
    }

    override suspend fun getEducationHistoryRecordById(id: String): UserEducation? {
        return userEducationRepository.getEducationHistoryRecordById(id = id)
    }

    override suspend fun updateEducationHistoryRecord(id: String, userEducationHistory: UserEducation): UserEducation {
        val userEducationDbDocument = UserEducationDbDocument(
            publicId = userEducationHistory.publicId,
            userId = userEducationHistory.userId,
            institutionId = userEducationHistory.institutionId,
            degree = userEducationHistory.degree
        )
        return userEducationRepository.updateEducationHistoryRecord(
            id = id,
            userEducationHistory = userEducationDbDocument
        )
    }

    override suspend fun deleteEducationHistoryRecord(id: String): Boolean {
        return userEducationRepository.deleteEducationHistoryRecord(id = id)
    }

    override suspend fun getUsersByInstitutionId(
        institutionId: String,
        sortBy: String?,
        sortDirection: String?,
        page: Int?,
        pageSize: Int?
    ): List<User> {
        return userEducationRepository.getUsersByInstitutionId(institutionId, sortBy, sortDirection, page, pageSize)
    }
}