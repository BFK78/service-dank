package com.dank.services.user.routing

import com.dank.services.education.api.UserEducation
import com.dank.services.education.domain.service.UserEducationService
import com.dank.services.user.api.User
import org.springframework.http.MediaType
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/education")
class UserEducationController(
    private val userEducationService: UserEducationService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    suspend fun createEducationRecord(@RequestBody education: MultiValueMap<String, String>): UserEducation {
        val userEducation = UserEducation(
            publicId = education.getFirst("publicId") ?: "",
            userId = education.getFirst("userId") ?: "",
            institutionId = education.getFirst("institutionId") ?: "",
            degree = education.getFirst("degree") ?: ""
        )
        return userEducationService.createEducationHistoryRecord(userEducation)
    }

    @GetMapping("user/{userId}")
    suspend fun getAllEducationRecordsByUserId(@PathVariable userId: String): List<UserEducation>? {
        return userEducationService.getAllEducationHistoryRecordByUserId(userId)
    }

    @GetMapping("{id}")
    suspend fun getEducationRecordById(@PathVariable id: String): UserEducation? {
        return userEducationService.getEducationHistoryRecordById(id)
    }

    @PutMapping("{id}", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    suspend fun updateEducationRecord(
        @PathVariable id: String,
        @RequestBody education: MultiValueMap<String, String>
    ): UserEducation {
        val userEducation = UserEducation(
            publicId = education.getFirst("publicId") ?: "",
            userId = education.getFirst("userId") ?: "",
            institutionId = education.getFirst("institutionId") ?: "",
            degree = education.getFirst("degree") ?: ""
        )
        return userEducationService.updateEducationHistoryRecord(id, userEducation)
    }

    @DeleteMapping("{id}")
    suspend fun deleteEducationRecord(@PathVariable id: String): Boolean {
        return userEducationService.deleteEducationHistoryRecord(id)
    }

    @GetMapping("/api/education/users")
    suspend fun getUsersByInstitutionId(
        @RequestParam("institutionId") institutionId: String,
        @RequestParam("sortBy", required = false) sortBy: String?,
        @RequestParam("sortDirection", defaultValue = "asc") sortDirection: String?,
        @RequestParam("page", required = false) page: Int?,
        @RequestParam("pageSize", required = false) pageSize: Int?
    ): List<User> {
        return userEducationService.getUsersByInstitutionId(institutionId, sortBy, sortDirection, page, pageSize)
    }

    
}