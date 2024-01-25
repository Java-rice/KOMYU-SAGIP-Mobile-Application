package com.mobile.komyusagip.model

data class UserModel(
    val firstName: String? = "",
    val lastName: String? = "",
    val email: String? = "",
    val phoneNumber: String? = "",
    val password: String? = "",
    val userId: String = "",
    val profileImage: String? = "",
    val username: String? = "",
    val location: String? = ""
) {
    fun toMap(): Map<String, String?> {
        return hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "password" to password,
            "phoneNumber" to phoneNumber,
            "profileImage" to profileImage,
            "username" to username,
            "location" to location,
            "userId" to userId
        )
    }
}