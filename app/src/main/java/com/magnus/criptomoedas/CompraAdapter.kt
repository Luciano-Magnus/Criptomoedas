package com.magnus.criptomoedas

import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ativos_item.view.txtmoeda
import kotlinx.android.synthetic.main.compras_item.view.*
import java.text.DecimalFormat

class CompraAdapter(private val ativos: List<Compra>) :
    RecyclerView.Adapter<CompraAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        Log.v("LOG", "onCreate")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.compras_item, parent, false)
        val vh = VH(v)


        vh.itemView.setOnClickListener {

        }
        return vh
    }

    override fun getItemCount(): Int {
        return ativos.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Log.v("LOG", "ViewHolder")
        var ativo = ativos[position]
        var mostrarItens = false

        holder.btn_drop.setOnClickListener({
            val df = DecimalFormat("#0.00")
            val compraDao = ComprasDAO(it.context)
            val qtd = compraDao.selectQtd(ativo.nome.toString())
            val valor = compraDao.selectValorInvestido(ativo.nome.toString())
            if (mostrarItens == false) {

                holder.txt_quantidade.setVisibility(View.VISIBLE)
                holder.txt_quantidade.text = "Quantidade = " + qtd.toString()
                holder.txt_valor.setVisibility(View.VISIBLE)
                holder.txt_valor.text = "Valor investido = " + df.format(valor)
                holder.txt_data.setVisibility(View.VISIBLE)
                holder.txt_data.text = "Data  da compra: " + ativo.data

                val layoutParams =
                    holder.barra.getLayoutParams()
                layoutParams.height = 430
                holder.barra.setLayoutParams(layoutParams);
                mostrarItens = true
            } else {
                holder.txt_quantidade.setVisibility(View.GONE)
                holder.txt_valor.setVisibility(View.GONE)
                holder.txt_data.setVisibility(View.GONE)
                val layoutParams =
                    holder.barra.getLayoutParams()
                layoutParams.height = 210
                holder.barra.setLayoutParams(layoutParams)
                mostrarItens = false
            }
        })

        holder.btn_delete.setOnClickListener(View.OnClickListener {
            val comprasDAO = ComprasDAO(it.context)
            val ativo = ativos[position]
            val alerta =
                AlertDialog.Builder(it.context)
            alerta.setTitle("Aviso")
            alerta
                .setIcon(R.drawable.ic_info_foreground)
                .setMessage("Deseja realmente remover o ativo.")
                .setCancelable(true)
                .setPositiveButton(
                    "Sim",
                    DialogInterface.OnClickListener({ dialogInterface, i ->
                        comprasDAO.delete(ativo)
                        Toast.makeText(it.context, "Ativo removido com sucesso", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(it.context, MainActivity::class.java)
                        it.context.startActivity(intent)

                    })
                )
                .setNegativeButton(
                    "Não",
                    DialogInterface.OnClickListener({ DialogInterface, i ->
                    })
                )


            val alertDialog = alerta.create()
            alertDialog.show()
        })

        holder.txt_moeda.text = ativo.nome.toString()
        holder.txt_data.text = ativo.data
    }

    class VH(itenView: View) : RecyclerView.ViewHolder(itenView) {

        var txt_quantidade: TextView = itenView.txt_quantidade
        var txt_moeda: TextView = itenView.txtmoeda
        var txt_valor: TextView = itemView.txt_valor
        var txt_data: TextView = itenView.txt_data
        var btn_delete: ImageView = itenView.btn_delete
        var btn_drop: ImageView = itemView.drop
        var barra: View = itemView.view3
    }
}