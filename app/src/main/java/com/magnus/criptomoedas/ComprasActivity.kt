package com.magnus.criptomoedas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.magnus.criptomoedas.Obejtos.Nome
import kotlinx.android.synthetic.main.activity_compras.*
import kotlinx.android.synthetic.main.activity_main.fab_add
import kotlinx.android.synthetic.main.activity_main.rv_dados
import kotlinx.android.synthetic.main.activity_main.txtMsg

class ComprasActivity : AppCompatActivity() {
    private var AtivoList = mutableListOf<Compra>()
    var nome = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)
        val ativo = intent.getParcelableExtra<Ativo>("ativos")
        nome = ativo.nome.toString()


        fab_add.setOnClickListener(View.OnClickListener {


            var enviarNome = Nome(ativo.nome)
            val it = Intent(this, SaveComprasActivity::class.java)
            it.putExtra("nomeAtivo", enviarNome)
            startActivity(it)
        })
        initRecyclerView()

    }

    private fun initRecyclerView() {
        Log.v("LOG", "Inicia RecyclerView")
        val adapter2 = CompraAdapter(AtivoList)
        rv_dados.adapter = adapter2
        val layout = LinearLayoutManager(this)
        rv_dados.layoutManager = layout
    }

    private fun update() {
        val compraDao = ComprasDAO(this)
        AtivoList.clear()
        AtivoList = compraDao.selectNome(nome)
        txt_codigo.text =
            resources.getString(R.string.qtd_ativos) + " = " + compraDao.selectSoma(nome).toString()

        if (AtivoList.isEmpty()) {
            rv_dados.setVisibility(View.GONE)
            txtMsg.setVisibility(View.VISIBLE)
            txtMsg.text = "Nenhum ativo adicionado"
        } else {
            rv_dados.setVisibility(View.VISIBLE)
            txtMsg.setVisibility(View.GONE)
        }
        rv_dados.adapter?.notifyDataSetChanged()
    }

    override fun onRestart() {
        super.onRestart()
        update()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        update()
        initRecyclerView()
    }
}

