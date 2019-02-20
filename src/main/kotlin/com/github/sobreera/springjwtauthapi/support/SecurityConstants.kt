package com.github.sobreera.springjwtauthapi.support

object SecurityConstants {
    const val SECRET = "myappsecret"
    const val EXPIRATION_TIME = 28_800_000 // 8hour
    const val TOKEN_PREFIX = "Bearer "
    const val AUTH_HEADER = "Authorization"
    const val LOGIN_URL = "/login"    // デフォルト
    const val LOGIN_ID = "username"   // デフォルト
    const val PASSWORD = "password"   // デフォルト
}