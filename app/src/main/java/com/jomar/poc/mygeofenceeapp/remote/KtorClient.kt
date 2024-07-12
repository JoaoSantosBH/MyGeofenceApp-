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


    override suspend fun getAddressLocation(request: AddressMapsApiRequest): AddresMapsResponse {
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
            throw ApiException(ApiError.SERVICE_UNAVAILABLE)
        }

        when(result.status.value){
            in 200..299 -> Unit
            500 -> throw ApiException(ApiError.SERVER_ERROR)
            in 400..499 -> throw ApiException(ApiError.CLIENT_ERROR)
            else -> throw ApiException(ApiError.UNKNOWN_ERROR)
        }

        return try {
            result.body<AddresMapsResponse>()
        } catch(e: Exception) {
            throw ApiException(ApiError.SERVER_ERROR)
        }
        finally {
            client.close()
        }
    }

}