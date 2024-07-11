package com.jomar.poc.mygeofenceeapp.remote

import com.jomar.poc.mygeofenceeapp.MAPS_BASE_URL
import com.jomar.poc.mygeofenceeapp.model.request.AddressMapsApiRequest
import com.jomar.poc.mygeofenceeapp.model.response.AddresMapsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json

class KtorClient {

   companion object {
       //val customer: Customer = client.get("http://localhost:8080/customer/3").body()
       suspend fun getAddressLocation(request: AddressMapsApiRequest): AddresMapsResponse {
           val client = HttpClient(CIO) {

               install(ContentNegotiation) {
                   register(
                       ContentType.Any, KotlinxSerializationConverter(
                           Json {
                               prettyPrint = true
                               isLenient = true
                               ignoreUnknownKeys = true
                           }
                       )
                   )
               }
           }
           val response: HttpResponse = client.get(MAPS_BASE_URL) {
               url {
                   parameters.append(request.addressParam, request.address)
                   parameters.append(request.sensorParam, request.sensor.toString())
                   parameters.append(request.keyParam, request.key)
               }
           }
           println(response.status)
           client.close()
            return response.body<AddresMapsResponse>()

       }
   }
}