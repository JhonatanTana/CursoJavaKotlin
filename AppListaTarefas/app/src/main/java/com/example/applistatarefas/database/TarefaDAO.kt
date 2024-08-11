package com.example.applistatarefas.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.applistatarefas.model.Tarefa

class TarefaDAO(context: Context): ITarefaDAO {

    val escrita = DatabaseHelper(context).writableDatabase
    val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {

        val conteudo = ContentValues()
        conteudo.put("${DatabaseHelper.DESCRICAO}", tarefa.descricao)

        try {
            escrita.insert(DatabaseHelper.NOME_TABELA_TAREFAS, null, conteudo)
            Log.i("info_db", "Tarefa salva com sucesso")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao salvar tarefa")
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {

        val args = arrayOf( tarefa.idTarefa.toString() )
        val conteudo = ContentValues()
        conteudo.put("${DatabaseHelper.DESCRICAO}", tarefa.descricao)


        try {
            escrita.update(
                DatabaseHelper.NOME_TABELA_TAREFAS, //Tabela
                conteudo, //Conteudo atualizado
                "${DatabaseHelper.ID_TAREFA} = ? ", //Condição where
                args //Valor do where
            )
            Log.i("info_db", "Sucesso ao atualizar Tarefa")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao atualizar Tarefa")
            return false
        }
        return true
    }

    override fun remover(idTarefa: Int): Boolean {

        val args = arrayOf( idTarefa.toString() )

        try {
            escrita.delete(
                DatabaseHelper.NOME_TABELA_TAREFAS, //Tabela
                "${DatabaseHelper.ID_TAREFA} = ? ", //Condição where
                args //Valor do where
            )
            Log.i("info_db", "Sucesso ao remover Tarefa")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover Tarefa")
            return false
        }
        return true
    }

    override fun listar(): List<Tarefa> {

        val listaTarefa = mutableListOf<Tarefa>()

        val sql = "SELECT ${DatabaseHelper.ID_TAREFA},${DatabaseHelper.DESCRICAO},${DatabaseHelper.DATA_CADASTRO} FROM tarefas "

        val cursor = leitura.rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex(DatabaseHelper.ID_TAREFA)
        val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.DESCRICAO)
        val indiceData = cursor.getColumnIndex(DatabaseHelper.DATA_CADASTRO)

        while (cursor.moveToNext()) {
            val idTarefa = cursor.getInt(indiceId)
            val descricao = cursor.getString(indiceDescricao)
            val data = cursor.getString(indiceData)

            listaTarefa.add(Tarefa(idTarefa, descricao, data))
        }

        return listaTarefa
    }
}