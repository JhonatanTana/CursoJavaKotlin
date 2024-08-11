package com.example.applistatarefas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.applistatarefas.database.TarefaDAO
import com.example.applistatarefas.databinding.ActivityAdicionarTarefaBinding
import com.example.applistatarefas.databinding.ActivityMainBinding
import com.example.applistatarefas.model.Tarefa

class AdicionarTarefaActivity : AppCompatActivity() {

    private val biding by lazy {
        ActivityAdicionarTarefaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(biding.root)

        var tarefa: Tarefa? = null
        val bundle = intent.extras

        if (bundle != null) {
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            biding.editTarefa.setText(tarefa.descricao)
        }

        biding.btnSalvar.setOnClickListener {
            if (tarefa != null) {
                editar(tarefa)
            }else {
                salvar()
            }
        }
    }

    private fun editar(tarefa: Tarefa) {

        val descricao = biding.editTarefa.text.toString()
        val tarefaAtualizada = Tarefa(
            tarefa.idTarefa, descricao, "default"
        )
        val tarefaDAO = TarefaDAO(this)

        if (tarefaDAO.atualizar(tarefaAtualizada)) {
            Toast.makeText(
                this,
                "Tarefa atualizada com sucesso",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun salvar() {

        if( biding.editTarefa.text.isNotEmpty() ) {
            val descricao = biding.editTarefa.text.toString()
            val tarefa = Tarefa(
                -1,
                descricao,
                "default"
            )
            val tarefaDAO = TarefaDAO(this)
            if (tarefaDAO.salvar(tarefa)) {
                Toast.makeText(
                    this,
                    "Tarefa cadastrada com sucesso",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }else {
            Toast.makeText(this, "Informe uma Tarefa", Toast.LENGTH_SHORT).show()
        }
    }
}