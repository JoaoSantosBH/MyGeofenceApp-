package com.jomar.poc.mygeofenceeapp.ui

import android.content.Context
import com.google.gson.Gson
import com.jomar.poc.mygeofenceeapp.GEO_LIST_KEY
import com.jomar.poc.mygeofenceeapp.SHARED_PREFS
import com.jomar.poc.mygeofenceeapp.geofenceJsonModel
import com.jomar.poc.mygeofenceeapp.model.GeofenceJsonItemResponse
import org.json.JSONException


object SharedPreferencesUtil {

    fun getJsonFromSharedPreferences(context: Context, key: String?): String? {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString(key, null)
        if (jsonString != null) {
            try {
                return jsonString
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun saveJsonToSharedPreferences(context: Context, key: String?, jsonString: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    //pegar lista no shared
    fun addNewGeofenceToSharedPrefs(context: Context, newGeofence: GeofenceJsonItemResponse) {
        val result = getJsonFromSharedPreferences(context, GEO_LIST_KEY)

        //pegar lista do JSON e tranformar em objeto
        val mList = geofenceJsonModel(result)

        //tratar se ja existe
        val existOne = mList?.filter {
            it.latitude == newGeofence.latitude &&
                    it.longitude == newGeofence.longitude
        }

        if (existOne?.isEmpty() == true) {
            mList.add(newGeofence)
            val gson = Gson()
            val json = gson.toJson(mList)
            saveJsonToSharedPreferences(context, GEO_LIST_KEY, json)
        }
    }



}
