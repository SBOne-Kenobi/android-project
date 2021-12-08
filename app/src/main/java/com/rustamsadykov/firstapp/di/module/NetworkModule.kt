package com.rustamsadykov.firstapp.di.module

import android.os.Build
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.rustamsadykov.firstapp.BuildConfig
import com.rustamsadykov.firstapp.data.network.Api
import com.rustamsadykov.firstapp.data.network.MockApi
import com.rustamsadykov.firstapp.data.network.interceptor.AuthorizationInterceptor
import com.rustamsadykov.firstapp.data.network.interceptor.FirstAppAuthenticator
import com.rustamsadykov.firstapp.data.network.interceptor.UserAgentInterceptor
import com.rustamsadykov.firstapp.repository.AuthRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.tls.HandshakeCertificates
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // [application id]/[application version] ([os name]; [os version])
    private val userAgent =
        "${BuildConfig.APPLICATION_ID}/${BuildConfig.VERSION_NAME} (Android; ${Build.VERSION.SDK_INT} SDK ${Build.VERSION.RELEASE})"

    private val clientCertificates =
        HandshakeCertificates.Builder()
            .addPlatformTrustedCertificates()
            // TODO: Добавить наш тестовый сервер.
            .addInsecureHost("")
            .build()

    @Provides
    @Singleton
    fun provideOkhttpClient(authRepository: AuthRepository): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                readTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)
                addNetworkInterceptor(UserAgentInterceptor(userAgent))
                addNetworkInterceptor(AuthorizationInterceptor(authRepository))
                authenticator(FirstAppAuthenticator(authRepository))
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(
                        HttpLoggingInterceptor { message ->
                            Timber.d(message)
                        }.setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    sslSocketFactory(
                        clientCertificates.sslSocketFactory(),
                        clientCertificates.trustManager
                    )
                }
            }
            .build()

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient, moshi: Moshi): Api =
        if (BuildConfig.USE_MOCK_BACKEND_API) {
            MockApi()
        } else {
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BACKEND_API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .build()
                .create(Api::class.java)
        }
}