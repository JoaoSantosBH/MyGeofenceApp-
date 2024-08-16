package com.jomar.poc.mygeofenceeapp

import com.google.gson.Gson
import com.jomar.poc.mygeofenceeapp.model.GeofenceJsonResponse

fun geofenceJsonModel(result: String?): GeofenceJsonResponse? {
val gson = Gson()
val mList = gson.fromJson(result, GeofenceJsonResponse::class.java)
return mList
}



