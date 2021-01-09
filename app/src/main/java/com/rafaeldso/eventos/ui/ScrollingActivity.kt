package com.rafaeldso.eventos.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.rafaeldso.eventos.R
import com.rafaeldso.eventos.extensions.loadUrl
import com.rafaeldso.eventos.model.Checkin
import com.rafaeldso.eventos.model.Event
import com.rafaeldso.eventos.retrofit.CallbackResponse
import com.rafaeldso.eventos.retrofit.client.EventWebClient
import kotlinx.android.synthetic.main.activity_event_contents.*
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.form_checkin.view.*


class ScrollingActivity : AppCompatActivity() {

    // Inicializa a variável quando a mesma for utilizada pela primeira vez
    private val event: Event? by lazy {
        intent.getParcelableExtra<Event>("eventExtras")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = event?.title
        toolbar_layout.setExpandedTitleTextAppearance(R.style.TituloToolbar)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        initViews()

        fab.setOnClickListener {
                onClickChekin(event)
        }
    }

    private fun onClickChekin(event: Event?) {
        val createdView = LayoutInflater.from(this).inflate(R.layout.form_checkin,
            window.decorView as ViewGroup,
            false)

        AlertDialog.Builder(this)
            .setTitle("Checkin")
            .setView(createdView)
            .setPositiveButton("Confirmar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val nome = createdView.form_checkin_nome.text.toString()
                    val email = createdView.form_checkin_email.text.toString()
                    val checkin = event?.id?.let { Checkin(it,nome, email) }
                    checkin?.let {
                        EventWebClient().insert(it, object : CallbackResponse<Int> {
                            override fun success(inteiro: Int) {
                                var text = ""
                                if(inteiro == 1){
                                     text = "Checkin feito com sucesso"
                                } else{
                                     text = "Ocorreu um problema ao enviar o Checkin, por favor, tente novamente mais tarde!"
                                }
                                val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
                                toast.show()
                            }
                        })
                    }
                }
            })
            .show()
    }

    private fun initViews() {
        // Variáveis do xml geradas automaticamente pelo Koltin Extensions (veja import)
        descricao_carro_contents.text = event?.description.toString()
        data_event_contents.text = event?.date.toString()
        preco_event_contents.text = event?.price.toString()
        latitude_longitude_event_contents.text = event?.latitude.toString()+"/"+event?.longitude.toString()
        appBarImg.loadUrl(event?.urlImagem())

    }
}