package com.example.callaguy_professional.core.domain.validation

import android.util.Patterns

class ValidateEmail {

    fun execute(email : String) : ValidationResult {
        if(email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "email can't be empty"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = "Not a valid Email"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}