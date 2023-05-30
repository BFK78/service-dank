package com.dank.services.education.domain.repository.model

import com.dank.services.education.api.UserEducation
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "education")
data class UserEducationDbDocument(
        @Id
        val publicId: String,
        val userId: String,
        val institutionId: String,
        val degree: String
) {

    fun toUserEducation(): UserEducation {
        return UserEducation(
                publicId = publicId,
                userId = userId,
                institutionId = institutionId,
                degree = degree
        )
    }

}
