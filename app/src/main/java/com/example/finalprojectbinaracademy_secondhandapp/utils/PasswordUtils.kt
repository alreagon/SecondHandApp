package com.example.finalprojectbinaracademy_secondhandapp.utils

class PasswordUtils {

    fun validate(pass: String): Boolean {
        //min 8 character
        if (pass.length < 8) {
            return false
        }
        //min 1 upercase
        if (!pass.matches(".*[A-Z].*".toRegex())) {
            return false
        }
        //min 1 lowercas
        if (!pass.matches(".*[a-z].*".toRegex())) {
            return false
        }
        //min 1 number
        if (!pass.matches(".*[0-9].*".toRegex())) {
            return false
        }

        return true
    }

}