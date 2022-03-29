package au.com.carsales.basemodule

import android.content.Context
import au.com.carsales.basemodule.util.glide.CustomImageSizeModel
import au.com.carsales.basemodule.util.glide.CustomImageSizeUrlLoaderFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import com.caverock.androidsvg.SVG
import android.graphics.drawable.PictureDrawable
import au.com.carsales.basemodule.util.glide.svg.SvgDecoder
import au.com.carsales.basemodule.util.glide.svg.SvgDrawableTranscoder
import javax.net.ssl.*


/**
 * Created by anibalbastias on 03/07/2018
 * Add applyOptions and register Components for limit LRU cache and Disk Cache size data
 */

@GlideModule
open class BaseAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // LRU Pool size
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize

        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()

        // set disk cache size & external vs. internal
        val cacheSize100MegaBytes = 104857600

        // or any other path
//        val downloadDirectoryPath = Environment.getDownloadCacheDirectory().path

        // Set builders
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes.toLong()))
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        val builder = OkHttpClient.Builder()
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)


        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(builder.build()))
        //Unsafe Okhttp client
//        val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
//        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
        registry.replace(CustomImageSizeModel::class.java, InputStream::class.java, CustomImageSizeUrlLoaderFactory())

        // Add for load SVG files
//        try {
//            // to catch XML parser problem
//            registry.register(SVG::class.java,
//                    PictureDrawable::class.java,
//                    SvgDrawableTranscoder()).append(InputStream::class.java, SVG::class.java, SvgDecoder())
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Crashlytics.logException(e)
//        }
    }
}

object UnsafeOkHttpClient {
    // Create a trust manager that does not validate certificate chains
    // Install the all-trusting trust manager
    // Create an ssl socket factory with our all-trusting manager
    val unsafeOkHttpClient: OkHttpClient
        get() {
            try {
                val trustAllCerts = arrayOf<TrustManager>(elements = *arrayOf(object : X509TrustManager {
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                    }

                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate>? {
                        return null
                    }
                }))
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier(HostnameVerifier { hostname, session -> true})

                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
}