package hr.algebra.eventmanagement.data

import android.annotation.SuppressLint
import hr.algebra.eventmanagement.BuildConfig
import hr.algebra.eventmanagement.model.Comments
import hr.algebra.eventmanagement.model.Event
import hr.algebra.eventmanagement.model.Ticket
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface EventManagementApi {

    @GET("Event")
    suspend fun getAllEvents(): List<Event>

    @GET("Event/ByUser/{id}")
    suspend fun getEventByUserId(
        @Path("id") id: Int
    ): List<Event>

    @GET("Event/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): Event

    @GET("Ticket/ByEvent/{id}")
    suspend fun getAllTickets(@Path("id") id: Int): List<Ticket>

    @GET("Comment/ByEvent/{id}")
    suspend fun getComments(@Path("id") id: Int): List<Comments>

    @POST("Comment")
    suspend fun insertNewComment(@Body requestBody: RequestBody): Response<ResponseBody>

    @PUT("Ticket/Purchase/{id}/{ticketType}/{email}")
    suspend fun ticketPurchased(
        @Path("id") id: Int,
        @Path("ticketType") ticketType: Int,
        @Path("email") email: String
    ): Response<Unit?>

    companion object {
        var apiService: EventManagementApi? = null

        fun getInstance(): EventManagementApi {
            if (apiService == null) {
                val retrofit = getUnsafeOkHttpClient()?.let {
                    Retrofit.Builder().baseUrl(BuildConfig.API_URL).client(it)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                }
                apiService = retrofit?.create(EventManagementApi::class.java)
            }
            return apiService!!
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient? {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(
                    @SuppressLint("CustomX509TrustManager")
                    object : X509TrustManager {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { _, _ -> true }
                builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    }
}