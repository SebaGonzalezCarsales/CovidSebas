package au.com.carsales.basemodule.api

import android.content.Context
import au.com.carsales.basemodule.BuildConfig
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.api.data.commons.ResponseErrorData
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
/*
fun interceptChain(chain: Interceptor.Chain?, apiSecret: String?, languageAccept: String?, apiKey: String?,
                   userAgent: String? = null, authHeader: MutableMap<String, String>? = null): Response {
    val url = chain!!.request().url.toUrl().path
    val httpMethod = chain.request().method
    val query = chain.request().url.toUrl().query

    var csnUserAgent = userAgent
    if (csnUserAgent.isNullOrEmpty()) {
        csnUserAgent = "app " + BuildConfig.FLAVOR + "/" +
                BuildConfig.VERSION_NAME + " Android/" + android.os.Build.VERSION.RELEASE + " (Android)"
    }
    val requestBuilder = chain.request().newBuilder()
    authHeader?.let { it ->
        it.map {
            if (!it.key.isNullOrEmpty() && !it.value.isNullOrEmpty()) {
                requestBuilder.header(it.key, it.value)
            }
        }
    }
    languageAccept?.let { requestBuilder.header("Accept-Language", it) }
    csnUserAgent?.let { requestBuilder.header("X-CSN-USER-AGENT", it) }
    if (!apiSecret.isNullOrBlank()) {
        // data encrypted by Carsales
        val apiData = createApiDataString(url, query, httpMethod, apiSecret, Date())
        requestBuilder.addHeader("apikey", apiKey ?: "").addHeader("apidata", apiData!!)
    }

    return chain.proceed(requestBuilder.build())
}
*/
fun createApiDataString(path: String, query: String?, httpMethod: String, apiSecret: String, date: Date): String? {

    val method = httpMethod.toUpperCase()
    val timestamp = createDateFormatter(date)
    var apiDataString = "timestamp:$timestamp\nmethod:$method\nuri:$path"
    if (!query.isNullOrEmpty()) {
        apiDataString += "?$query\n"
    }

    return encryptAES(apiDataString, apiSecret)
}

// Encrypt AES ECB from Carsales
fun encryptAES(apiDataString: String, apiSecret: String): String {
    val apiSecretData = android.util.Base64.decode(apiSecret, android.util.Base64.DEFAULT)
    val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC")

    val keySpec = SecretKeySpec(apiSecretData, "AES")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec)

    val cipherString: String
    val byteBuffer = cipher.doFinal(apiDataString.toByteArray())

    cipherString = String(android.util.Base64.encode(byteBuffer, android.util.Base64.NO_WRAP),
            Charset.forName("UTF-8"))

    return cipherString
}

fun createDateFormatter(date: Date): String? {
    val formatDate = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'" //or yyyy-MM-dd'T'HH:mm

    val format = SimpleDateFormat(formatDate, Locale.ENGLISH)
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(date)
}

fun handlerResponseError(context: Context? = null, exception: Throwable): ResponseErrorData {
    return when (exception) {
        is HttpException -> {
            return try {

                Gson().fromJson<ResponseErrorData>(exception.response()?.errorBody()!!.string(),
                        ResponseErrorData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                ResponseErrorData()
            }
        }
        is UnknownHostException -> {
            ResponseErrorData(errorMsg = context?.getString(R.string.connection_error_message)
                    ?: "Error de conexión")
        }
        is ConnectException -> {
            ResponseErrorData(errorMsg = context?.getString(R.string.connection_error_message)
                    ?: "Error de conexión")
        }
        else -> {
            exception.printStackTrace()
            ResponseErrorData(errorMsg = exception.message
                    ?: "")
        }
    }
}

fun handleResponseError(response: retrofit2.Response<*>): ResponseErrorData {
    return when {
        !response.isSuccessful -> {
            Gson().fromJson<ResponseErrorData>(response.errorBody()!!.string(),
                    ResponseErrorData::class.java)
        }
        else -> ResponseErrorData()
    }
}
