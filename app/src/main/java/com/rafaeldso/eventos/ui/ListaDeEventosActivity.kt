package com.rafaeldso.eventos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafaeldso.eventos.R
import com.rafaeldso.eventos.model.Event
import com.rafaeldso.eventos.retrofit.CallbackResponse
import com.rafaeldso.eventos.retrofit.client.EventWebClient
import kotlinx.android.synthetic.main.activity_lista_de_eventos.*

class ListaDeEventosActivity: AppCompatActivity(), ListaDeEventosAdapter.OnEventListener {
    private lateinit var mEvents : List<Event>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_eventos)

        EventWebClient().list(object: CallbackResponse<List<Event>> {
            override fun success(events: List<Event>) {
                configureList(events)
            }
        })


    }

    private fun configureList(events: List<Event>) {
        val recyclerView = event_list_recyclerview
        mEvents = events
        recyclerView.adapter = ListaDeEventosAdapter(events, this, this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun OnEventClick(position: Int) {
        onClickEvent(mEvents[position])
    }

    open fun onClickEvent(event: Event) {
        val intent = Intent(this, EventoActivity::class.java).apply {
            putExtra("eventExtras", event)
        }
        startActivity(intent)
    }
}