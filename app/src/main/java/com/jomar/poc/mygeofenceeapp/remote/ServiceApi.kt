package com.jomar.poc.mygeofenceeapp.remote

import com.jomar.poc.mygeofenceeapp.model.request.AddressMapsApiRequest
import com.jomar.poc.mygeofenceeapp.model.response.AddresMapsResponse

interface ServiceApi {

    suspend fun getAddressLocation(request: AddressMapsApiRequest): AddresMapsResponse

}