package com.jomar.poc.mygeofenceeapp.remote

import com.jomar.poc.mygeofenceeapp.model.response.AddresMapsResponse

enum class ApiError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class ApiException(val error: String): Exception(
    "$error"
)

sealed class ApiResponse {
    data class Success(val data: AddresMapsResponse): ApiResponse()
    data class Failure(val error: ApiError, val exception: ApiException): ApiResponse()
}