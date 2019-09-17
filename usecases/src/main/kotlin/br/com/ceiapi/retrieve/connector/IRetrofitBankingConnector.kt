package br.com.ceiapi.retrieve.connector

import retrofit2.Call
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface IRetrofitBankingConnector {

    /** LOGIN **/

    @POST("servico/ServicoLogin/login")
    fun loginService(
        @QueryMap queryMap: Map<String, String>,
        @HeaderMap header: Map<String, String>
    ): Call<LoginServiceResponse>
}