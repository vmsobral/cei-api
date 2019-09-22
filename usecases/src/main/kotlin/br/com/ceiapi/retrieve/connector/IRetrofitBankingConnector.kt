package br.com.ceiapi.retrieve.connector

import org.jsoup.nodes.Document
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface IRetrofitBankingConnector {

    /** LOGIN **/

    @GET("/")
    fun ceiHomepage(): Call<Document>

    @FormUrlEncoded
    @POST("CEI_Responsivo/login.aspx")
    fun login(@FieldMap input: Map<String, String>, @Header("Cookie") cookies: String): Call<Document>

    @GET("CEI_Responsivo/home.aspx")
    fun home(@Header("Cookie") cookies: String): Call<Document>

}