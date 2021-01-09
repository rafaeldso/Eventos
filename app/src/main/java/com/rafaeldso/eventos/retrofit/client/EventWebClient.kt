package com.rafaeldso.eventos.retrofit.client

import android.util.Log
import com.rafaeldso.eventos.model.Checkin
import com.rafaeldso.eventos.model.Event
import com.rafaeldso.eventos.retrofit.CallbackResponse
import com.rafaeldso.eventos.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventWebClient {

    fun list(callbackResponse : CallbackResponse<List<Event>>){
        val call = RetrofitConfig().eventService().list()

        call.enqueue(object: Callback<List<Event>?> {
            override fun onResponse(call: Call<List<Event>?>?,
                                    response: Response<List<Event>?>?) {
                response?.body()?.let {
                    val events: List<Event> = it
                    callbackResponse.success(events)

                }
            }

            override fun onFailure(call: Call<List<Event>?>?,
                                   t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }

    fun insert(checkin: Checkin, callbackResponse: CallbackResponse<Int>){
        val call = RetrofitConfig().eventService().insert(checkin)
        call.enqueue(object: Callback<Any?> {
            override fun onResponse(call: Call<Any?>?, response: Response<Any?>?) {
                var isSuccess = false
                isSuccess = response?.isSuccessful()!!
                if (isSuccess){
                    callbackResponse.success(1)
                }else{
                    callbackResponse.success(0)
                }

            }

            override fun onFailure(call: Call<Any?>?, t: Throwable?) {
                callbackResponse.success(0)
            }
        })
    }
}