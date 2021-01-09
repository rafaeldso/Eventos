package com.rafaeldso.eventos.retrofit

import com.rafaeldso.eventos.model.Event

interface CallbackResponse<T> {
    fun success(response: T)
}