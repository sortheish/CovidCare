package com.org.covidcare.model

import com.google.firebase.database.Exclude

data class Status(
	val username: String? = null,
	val employeeId: String? = null,
	val phoneNumber: String? = null,
	val status: String? = null,
	val email: String? = null,
	val timestamp: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
			"username" to username,
			"employeeId" to employeeId,
			"phoneNumber" to phoneNumber,
			"status" to status,
			"email" to email,
			"timestamp" to timestamp
		)
    }
}