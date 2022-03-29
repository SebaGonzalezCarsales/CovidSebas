package au.com.carsales.basemodule.dependency.baseconcreate.module

import au.com.carsales.basemodule.api.data.baseconcrete.BaseConcreteApiService
import au.com.carsales.basemodule.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
internal class BaseConcreteAPIModule {

    companion object {
        private const val CONNECT_TIMEOUT = 120L
        private const val READ_TIMEOUT = 120L
        private const val WRITE_TIMEOUT = 120L
    }


    @Provides
    @Singleton
    @Named("provideBaseConcreteHttpClient")
    fun provideHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()



        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    @Named("provideBaseConcreteRetrofit")
    fun provideRetrofit(@Named("provideBaseConcreteHttpClient") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl("http://www.fakeurl.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(makeGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private fun makeGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    @Provides
    fun provideBaseConcreteAPI(@Named("provideBaseConcreteRetrofit") retrofit: Retrofit): BaseConcreteApiService = retrofit.create(BaseConcreteApiService::class.java)
}