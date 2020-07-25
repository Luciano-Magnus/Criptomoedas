package com.magnus.criptomoedas

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class ComprasDAO(context: Context) {
    val banco = DbHelper(context)


    fun insert(compra: Compra): String {
        val db = banco.writableDatabase
        val contextValues = ContentValues()

        contextValues.put(ID_COMPRA, compra.id)
        contextValues.put(NOME_COMPRA, compra.nome)
        contextValues.put(DATA_COMPRA, compra.data)
        contextValues.put(QTD_COMPRA, compra.qtd)
        contextValues.put(VALOR_COMPRA, compra.valor)

        val resp_id = db.insert(TABELA_COMPRA, null, contextValues)
        val msg = if (resp_id != -1L) {
            "Inserido"
        } else {
            "Nao inserido"
        }
        db.close()
        return msg
    }

    fun selectNome(nome: String): ArrayList<Compra> {
        Log.v("LOG", "GetAll")
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABELA_COMPRA + " where $NOME_COMPRA like '%$nome%' "
        Log.v("LOG", "" + sql)
        val cursor = db.rawQuery(sql, null)
        val compra = ArrayList<Compra>()
        while (cursor.moveToNext()) {
            val compras = compraFromCursor(cursor)
            compra.add(compras)
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${compra.size}")
        return compra
    }

    fun selectQtd(nome: String): Double {
        Log.v("LOG", "GetAll")
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABELA_COMPRA + " where $NOME_COMPRA like '%$nome%' "
        Log.v("LOG", "" + sql)
        val cursor = db.rawQuery(sql, null)
        var qtd = 0.0
        while (cursor.moveToNext()) {
            val compras = compraFromCursor(cursor)
            qtd += compras.qtd!!
        }
        cursor.close()
        db.close()
        return qtd
    }

    fun selectTotal(): Double {
        Log.v("LOG", "GetAll")
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABELA_COMPRA
        Log.v("LOG", "" + sql)
        val cursor = db.rawQuery(sql, null)
        val compra = ArrayList<Compra>()
        var total = 0.0
        while (cursor.moveToNext()) {
            val compras = compraFromCursor(cursor)
            var qtd = compras.qtd!!
            total += qtd * compras.valor!!
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${compra.size}")
        return total
    }

    fun selectValorInvestido(nome: String): Double {
        Log.v("LOG", "GetAll")
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABELA_COMPRA + " where $NOME_COMPRA like '%$nome%' "
        Log.v("LOG", "" + sql)
        val cursor = db.rawQuery(sql, null)
        val compra = ArrayList<Compra>()
        var total = 0.0
        while (cursor.moveToNext()) {
            val compras = compraFromCursor(cursor)
            var qtd = compras.qtd!!
            total += qtd * compras.valor!!
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${compra.size}")
        return total
    }

    fun delete(compra: Compra): Int {
        val db = banco.writableDatabase
        return db.delete(TABELA_COMPRA, "id_compra =?", arrayOf(compra.id.toString()))
    }

    fun selectSoma(nome: String): Double {
        Log.v("LOG", "GetAll")
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABELA_COMPRA + " where $NOME_COMPRA like '%$nome%' "
        Log.v("LOG", "" + sql)
        val cursor = db.rawQuery(sql, null)
        val compra = ArrayList<Compra>()
        var sum = 0.0
        while (cursor.moveToNext()) {
            val compras = compraFromCursor(cursor)
            sum += compras.qtd!!
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${compra.size}")
        return sum
    }


    private fun compraFromCursor(cursor: Cursor): Compra {
        val id = cursor.getInt(cursor.getColumnIndex(ID_COMPRA))
        val nome = cursor.getString(cursor.getColumnIndex(NOME_COMPRA))
        val data = cursor.getString(cursor.getColumnIndex(DATA_COMPRA))
        val qtd = cursor.getDouble(cursor.getColumnIndex(QTD_COMPRA))
        val valor = cursor.getDouble(cursor.getColumnIndex(VALOR_COMPRA))

        return Compra(id, nome, data, qtd, valor)

    }
}
