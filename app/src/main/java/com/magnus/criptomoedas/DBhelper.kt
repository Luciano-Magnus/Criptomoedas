package com.magnus.criptomoedas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


private val DB_NAME = "Portfolio.db"
private val DB_VERSION = 9

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE $TABELA_ATIVO($ID_COLUMN  INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, $NOME_COLUMN TEXT,$CODIGO_COLUMN TEXT, $QTD_COLUMN DOUBLE);"
        db.execSQL(sql)
        val sql2 = " CREATE TABLE $TABELA_COMPRA($ID_COMPRA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$NOME_COMPRA TEXT, $DATA_COMPRA TEXT, $QTD_COMPRA DOUBLE, $VALOR_COMPRA DOUBLE)"
        db.execSQL(sql2)

        Log.e("LOG", "Criando")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ATIVO)
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_COMPRA)


        onCreate(db)
    }

}

