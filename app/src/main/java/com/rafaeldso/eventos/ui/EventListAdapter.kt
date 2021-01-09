package com.rafaeldso.eventos.ui


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.rafaeldso.eventos.R
import com.rafaeldso.eventos.extensions.loadUrl
import com.rafaeldso.eventos.model.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_item.view.*
import kotlinx.android.synthetic.main.material_list_item_single_line.view.*

class EventListAdapter(private val events: List<Event>,
                       private val context: Context,
                        private val mOnEventListener: OnEventListener) : Adapter<EventListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View,
                     private val onEventListener: OnEventListener)  : RecyclerView.ViewHolder(itemView), View.OnClickListener {



        fun bindView(event: Event) {
            val picasso = Picasso.get()
            val title = itemView.mtrl_list_item_text
            val image = itemView.mtrl_list_item_icon
            title.text = event.title

            /*picasso.load(urlImage)
                .resize(64, 64)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(image)*/
            itemView.mtrl_list_item_icon.loadUrl(event.urlImagem())
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onEventListener.OnEventClick(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.material_list_item_single_line, parent, false)
        return ViewHolder(view,mOnEventListener)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder?.let {
            it.bindView(event)

        }

    }

    interface OnEventListener{
        fun OnEventClick(position: Int)
    }

/*    // Ao clicar no carro vamos navegar para a tela de detalhes
    open fun onClickCarro(event: Event) {
        MainActivity?.startActivity<ScrollingActivity>("eventExtras" to event)
    }*/
}
