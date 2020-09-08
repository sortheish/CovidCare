package com.org.covidcare.model

import com.google.firebase.database.Exclude

data class Query(
	val username: String? = null,
	val employeeId: String? = null,
	val phoneNumber: String? = null,
	val query: String? = null,
	val email: String? = null,
	val timestamp: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
			"username" to username,
			"employeeId" to employeeId,
			"phoneNumber" to phoneNumber,
			"query" to query,
			"email" to email,
			"timestamp" to timestamp
		)
    }
}