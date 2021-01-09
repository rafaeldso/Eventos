package com.rafaeldso.eventos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafaeldso.eventos.R
import com.rafaeldso.eventos.model.Event
import com.rafaeldso.eventos.retrofit.CallbackResponse
import com.rafaeldso.eventos.retrofit.client.EventWebClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), EventListAdapter.OnEventListener {
    private lateinit var mEvents : List<Event>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventWebClient().list(object: CallbackResponse<List<Event>> {
            override fun success(events: List<Event>) {
                configureList(events)
            }
        })


    }

    private fun configureList(events: List<Event>) {
        val recyclerView = event_list_recyclerview
        mEvents = events
        recyclerView.adapter = EventListAdapter(events, this, this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun OnEventClick(position: Int) {
        onClickCarro(mEvents[position])
    }

    open fun onClickCarro(event: Event) {
        val intent = Intent(this, ScrollingActivity::class.java).apply {
            putExtra("eventExtras", event)
        }
        startActivity(intent)
    }
}