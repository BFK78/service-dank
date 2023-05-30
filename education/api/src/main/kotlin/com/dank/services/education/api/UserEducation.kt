package com.dank.services.education.api

data class UserEducation(
        val publicId: String,
        val userId: String,
        val institutionId: String,
        val degree: String
)
