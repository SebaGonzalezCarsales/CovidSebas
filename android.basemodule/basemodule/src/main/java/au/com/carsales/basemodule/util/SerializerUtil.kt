package au.com.carsales.basemodule.util

import com.google.gson.GsonBuilder

fun convertObjectToString (objectToConvert: Any): String{
    val gson = GsonBuilder().create()
    return gson.toJson(objectToConvert)
}

fun <T> convertStringToObject(jsonObject: String, classCast: Class<T>): Any? {
    val gson = GsonBuilder().create()
    try {
        return  gson.fromJson(jsonObject, classCast)

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}