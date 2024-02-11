package com.mobile.komyusagip.model

data class UserModel2(
    val username: String? = "",
    val location: String? = ""
){
    fun toMap(): Map<String, String?> {
        return hashMapOf(
            "username" to username,
            "location" to location,
        )
    }
}
