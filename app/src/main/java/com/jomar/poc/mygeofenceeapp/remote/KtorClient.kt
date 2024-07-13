package com.jomar.poc.mygeofenceeapp.remote

import com.jomar.poc.mygeofenceeapp.MAPS_BASE_URL
import com.jomar.poc.mygeofenceeapp.PARAM_KTOR_CLIENT
import com.jomar.poc.mygeofenceeapp.model.request.AddressMapsApiRequest
import com.jomar.poc.mygeofenceeapp.model.response.AddresMapsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import java.io.IOException

open class KtorClient : ServiceApi {


    override suspend fun getAddressLocation(request: AddressMapsApiRequest): ApiResponse { // sealed class com states
        val client = getClient()
        val result = try {
            client.get(MAPS_BASE_URL) {
                url {
                    parameters.append(request.addressParam, request.address)
                    parameters.append(request.sensorParam, request.sensor.toString())
                    parameters.append(request.keyParam, request.key)
                }
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json)
                    append(HttpHeaders.UserAgent, PARAM_KTOR_CLIENT)
                }
            }
        } catch (e: IOException) {
            return ApiResponse.Failure(ApiError.SERVICE_UNAVAILABLE)
        }
        when(result.status.value){
            in 200..299 -> Unit
            500 -> return ApiResponse.Failure(ApiError.SERVER_ERROR)
            in 400..499 -> return ApiResponse.Failure(ApiError.CLIENT_ERROR)
            else -> return ApiResponse.Failure(ApiError.UNKNOWN_ERROR)
        }

        return try {
            ApiResponse.Success(result.body<AddresMapsResponse>())
        } catch (e: Exception) {
            ApiResponse.Failure(ApiError.SERVER_ERROR)
        } catch (e: IOException) {
            ApiResponse.Failure(ApiError.SERVICE_UNAVAILABLE)
        }
        finally {
            client.close()
        }
    }

}