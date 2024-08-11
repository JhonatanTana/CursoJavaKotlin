package com.example.applistatarefas.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper( context, NOME_BANCO_DADOS, null, VERSAO ) {

    companion object {
        const val NOME_BANCO_DADOS = "ListaTarefas.db"
        const val VERSAO = 1
        const val NOME_TABELA_TAREFAS = "tarefas"
        const val ID_TAREFA = "id_tarefa"
        const val DESCRICAO = "descricao"
        const val DATA_CADASTRO = "dataCadastro"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = "CREATE TABLE IF NOT EXISTS $NOME_TABELA_TAREFAS (" +
                "  $ID_TAREFA integer not null PRIMARY KEY AUTOINCREMENT," +
                "  $DESCRICAO varchar(70) ," +
                "  $DATA_CADASTRO datetime not NULL default CURRENT_TIMESTAMP" +
                "  );"

        try {
            db?.execSQL(sql)
            Log.i("info_db", "Sucesso ao criar tabela")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.i("inf_db", "Erro ao criar tabela")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}