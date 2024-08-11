package com.example.sqlite.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper( context, "loja.db", null, 2 ) {

    companion object {
        const val TABELA_PRODUTOS = "produtos"
        const val ID_PRODUTO = "id_produto"
        const val TITULO = "titulo"
        const val DESCRICAO = "descricao"
    }

    override fun onCreate(p0: SQLiteDatabase?) {

        Log.i("info_db", "Executou o onCreate")
        val sql =
            "create table if not EXISTS $TABELA_PRODUTOS (" +
            "$ID_PRODUTO integer not NULL PRIMARY KEY AUTOINCREMENT," +
            "$TITULO varchar(100)," +
            "$DESCRICAO text" +
            ");"

        try {
            p0?.execSQL( sql )
            Log.i("info_db", "Sucesso ao criar o banco")
        }catch (e:Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar o banco")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        Log.i("info_db", "Executou o onUpdate")
    }

}