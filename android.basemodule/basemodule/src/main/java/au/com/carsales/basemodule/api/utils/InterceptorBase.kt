package au.com.carsales.basemodule.api.utils

import okhttp3.Interceptor

abstract class InterceptorBase : Interceptor {

    var headersMap: MutableMap<String, String> = mutableMapOf()

    fun addHeaders(headers: Map<String, String>) {
        this.headersMap.putAll(headers)

    }

    fun addHeader(name: String, value: String) {
        headersMap[name] = value

    }

    fun removeHeader(name: String){
        headersMap.remove(name)
    }
}