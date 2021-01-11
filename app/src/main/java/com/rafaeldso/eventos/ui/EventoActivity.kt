package com.rafaeldso.eventos.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.rafaeldso.eventos.R
import com.rafaeldso.eventos.extensions.conversorDeCoordenadasEmEndereco
import com.rafaeldso.eventos.extensions.loadUrl
import com.rafaeldso.eventos.model.Checkin
import com.rafaeldso.eventos.model.Event
import com.rafaeldso.eventos.retrofit.CallbackResponse
import com.rafaeldso.eventos.retrofit.client.EventWebClient
import kotlinx.android.synthetic.main.activity_event_contents.*
import kotlinx.android.synthetic.main.activity_evento.*
import kotlinx.android.synthetic.main.form_checkin.view.*


class EventoActivity : AppCompatActivity() {

    // Inicializa a variável quando a mesma for utilizada pela primeira vez
    private val event: Event? by lazy {
        intent.getParcelableExtra<Event>("eventExtras")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento)
        setSupportActionBar(findViewById(R.id.toolbar))

        configToolbar()

        initViews()

        fab.setOnClickListener {
                onClickChekin(event)
        }
    }

    private fun configToolbar() {
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = event?.title
        toolbar_layout.setExpandedTitleTextAppearance(R.style.TituloToolbar)
    }

    private fun onClickChekin(event: Event?) {
        val createdView = LayoutInflater.from(this).inflate(R.layout.form_checkin,
            window.decorView as ViewGroup,
            false)

        AlertDialog.Builder(this)
            .setTitle("Checkin")
            .setView(createdView)
            .setPositiveButton(getString(R.string.confirmar), object : DialogInterface.OnClickListener {
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
        data_event_contents.text = event?.dataFormatada()
        preco_event_contents.text = event?.precoFormatado()
        latitude_longitude_event_contents.text =
            event?.latitude?.let { event?.longitude?.let { it1 ->
                conversorDeCoordenadasEmEndereco(it,
                    it1, this)
            } } //event?.latitude.toString()+"/"+event?.longitude.toString()
        appBarImg.loadUrl(event?.urlImagem())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.opcaoCompartillhar -> {
                compartilhar()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun compartilhar() {
        var mensagem = "Evento: ${event?.title} \nPreço: ${event?.precoFormatado()} \n" +
                "Data: ${event?.dataFormatada()} \n" +
                "Endereço: ${event?.latitude?.let { event?.longitude?.let { it1 ->
                    conversorDeCoordenadasEmEndereco(it,
                        it1, this)
                } }}"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, mensagem)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }
}