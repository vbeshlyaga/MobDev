package ru.valentin.api_app

import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("v1/images/search")
    fun getCat(): Call<List<CatResponse>>
}