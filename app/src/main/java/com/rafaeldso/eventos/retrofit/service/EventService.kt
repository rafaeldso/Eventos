package com.rafaeldso.eventos.retrofit.service

import com.rafaeldso.eventos.model.Checkin
import com.rafaeldso.eventos.model.Event
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventService {
    @GET("events")
    fun list() : Call<List<Event>>

    @POST("checkin")
    fun insert(@Body checkin: Checkin) : Call<Any?>
}