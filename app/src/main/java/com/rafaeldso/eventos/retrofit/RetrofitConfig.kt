package com.rafaeldso.eventos.retrofit

import com.rafaeldso.eventos.retrofit.service.EventService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    //fun init() {
     private val retrofit = Retrofit.Builder()
            .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    //}

    fun eventService() : EventService = retrofit.create(EventService::class.java)

}