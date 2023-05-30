package com.dank.services.education.domain.service

import com.dank.services.education.api.UserEducation
import com.dank.services.user.api.User

interface UserEducationService {

    suspend fun createEducationHistoryRecord(userEducationHistory: UserEducation): UserEducation

    suspend fun getAllEducationHistoryRecordByUserId(userId: String): List<UserEducation>?

    suspend fun getEducationHistoryRecordById(id: String): UserEducation?

    suspend fun updateEducationHistoryRecord(id: String, userEducationHistory: UserEducation): UserEducation

    suspend fun deleteEducationHistoryRecord(id: String): Boolean

    suspend fun getUsersByInstitutionId(
        institutionId: String,
        sortBy: String?,
        sortDirection: String?,
        page: Int?,
        pageSize: Int?
    ): List<User>

}