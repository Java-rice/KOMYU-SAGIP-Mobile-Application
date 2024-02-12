package com.mobile.komyusagip.model

data class UserModel(
    val firstName: String? = "",
    val lastName: String? = "",
    val email: String? = "",
    val phoneNumber: String? = "",
    val password: String? = "",
    val userId: String? = "",
    val username: String? = "",
    val location: String? = "",
    val profileImage: String? = ""
) {
    fun toMap(): Map<String, String?> {
        return hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "password" to password,
            "phoneNumber" to phoneNumber,
            "userId" to userId,
            "username" to username,
            "location" to location,
            "profileImage" to profileImage
        )
    }
}