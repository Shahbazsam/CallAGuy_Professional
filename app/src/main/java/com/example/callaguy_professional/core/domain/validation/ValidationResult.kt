package com.example.callaguy_professional.core.domain.validation

data class ValidationResult(
    val successful : Boolean,
    val errorMessage : String? = null
)
