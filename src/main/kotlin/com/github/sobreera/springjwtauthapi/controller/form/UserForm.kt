package com.github.sobreera.springjwtauthapi.controller.form

import org.springframework.security.crypto.password.PasswordEncoder

class UserForm {
    var userId: String? = null
    var password: String? = null

    fun encrypt(encoder: PasswordEncoder) {
        password = encoder.encode(password)
    }
}