package com.magnus.criptomoedas

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class AtivosDAO(context: Context) {
    val banco = DbHelper(context)

    fun insert(ativo: Ativo): String {
        val db = banco.writableDatabase
        val contextValues = ContentValues()

        contextValues.put(ID_COLUMN, ativo.id)
        contextValues.put(NOME_COLUMN, ativo.nome)
        contextValues.put(CODIGO_COLUMN, ativo.codigo)
        contextValues.put(QTD_COLUMN, ativo.qtd)

        val resp_id = db.insert(TABELA_ATIVO, null, contextValues)
        val msg = if (resp_id != -1L) {
            "Inserido"
        } else {
            "Erro ao inserir"
        }
        db.close()
        return msg
    }

    fun delete(ativo: Ativo): Int {
        val db = banco.writableDatabase
        return db.delete(TABELA_ATIVO, "id_ativos =?", arrayOf(ativo.id.toString()))
    }

    fun select(): ArrayList<Ativo> {
        Log.v("LOG", "GetAll")
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABELA_ATIVO
        val cursor = db.rawQuery(sql, null)
        val ativo = ArrayList<Ativo>()
        while (cursor.moveToNext()) {
            val moeda = ativoFromCursor(cursor)
            ativo.add(moeda)
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${ativo.size}")
        return ativo
    }

    fun pegaId(id_ativo: Int?): Boolean {
        val db = banco.writableDatabase
        var id = 0

        val sql = "SELECT * from " + TABELA_ATIVO
        val cursor = db.rawQuery(sql, null)
        val ativo = ArrayList<Ativo>()
        while (cursor.moveToNext()) {
            val moeda = ativoFromCursor(cursor)
            id = moeda.id!!
            if (id_ativo == id) {
                return false
            }
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${ativo.size}")
        return true
    }


    private fun ativoFromCursor(cursor: Cursor): Ativo {
        val id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN))
        val codigo = cursor.getString(cursor.getColumnIndex(CODIGO_COLUMN))
        val nome = cursor.getString(cursor.getColumnIndex(NOME_COLUMN))
        val qtd = cursor.getDouble(cursor.getColumnIndex(QTD_COLUMN))

        return Ativo(id, nome, codigo, qtd)

    }
}