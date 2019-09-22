package br.com.ceiapi.retrieve.connector

import br.com.ceiapi.config.propertiesconfig.ConfigService
import com.github.slashrootv200.retrofithtmlconverter.HtmlConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

@Component
class ConnectorFactory(val configService: ConfigService) {

    fun create(): IRetrofitBankingConnector {
        val interceptor = Interceptor {
            return@Interceptor try {
                val request = it.request().newBuilder().build()
                it.proceed(request)
            } catch (e: IOException) {
                throw RuntimeException("Unable to connect to CEI", e)
            }
        }

        val client = OkHttpClient.Builder()
                .connectTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)

//        if (proxyData != null) client.proxy(convertDefaultToProxy(proxyData))

        val baseUrl = configService.getRequiredString("cei.url")
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(HtmlConverterFactory.create(baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IRetrofitBankingConnector::class.java)
    }

    companion object {
        private const val PAGE_LOAD_TIMEOUT = 20L
    }
}

/*
data class ConnectionContext(
        val requestId: String,
        val credentials: BBCredentials,
        val connector: IRetrofitBankingConnector,
        val header: HeaderRequest
) {
    val deviceId = CryptographyService.generateHashHex(credentials.branch, credentials.account)
    val deviceRme = deviceId.substring(0, 16)

    data class HeaderRequest(
            @Expose @SerializedName(value = "accept") val accept: String = "application/json",
            @Expose @SerializedName(value = "User-Agent") val userAgent: String = "Genymotion;Samsung Galaxy S7;Android;7.1.1;vbox86p-userdebug 7.1.1 NMF26Q 234 test-keys;mov-android-app;7.7.0.2;en_US;cpu=0|clock=|ram=4048928 kB|espacoSDInterno=13GB|espacoSDExterno=13GB|isSmartphone=true|nfc=false|camera=true|cameraFrontal=true|root=true|reconhecimentoVoz=true|resolucao=1440_2560|densidade=4.0|densidade=4.0|imei=000000000000000|soID=f5e709967694591a|",
            @Expose @SerializedName(value = "X-CNL-Ticket") val ticket: String = "002000091qF2gAxbiWQ1BSBF00000000",
            @Expose @SerializedName(value = "Device-Info") val deviceInfo: String = deviceInfoContent(),
            @Expose @SerializedName(value = "Content-Type") val contentType: String = "application/x-www-form-urlencoded",
            @Expose @SerializedName(value = "Host") val host: String = "mobi2.bb.com.br",
            @Expose @SerializedName(value = "Cookie") var cookie: String?
    )
}*/
