package com.jomar.poc.mygeofenceeapp.remote

import com.jomar.poc.mygeofenceeapp.model.response.AddresMapsResponse

enum class ApiError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class ApiException(val error: ApiError): Exception(
    "An error occurred when fetching daata: $error"
)

sealed class ApiResponse {
    data class Success(val data: AddresMapsResponse): ApiResponse()
    data class Failure(val error: ApiError): ApiResponse()
}