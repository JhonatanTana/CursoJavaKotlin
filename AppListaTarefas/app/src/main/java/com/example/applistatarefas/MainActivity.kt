package com.example.applistatarefas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applistatarefas.adapter.TarefaAdapter
import com.example.applistatarefas.database.TarefaDAO
import com.example.applistatarefas.databinding.ActivityMainBinding
import com.example.applistatarefas.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val biding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(biding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.primaria, theme)
        }

        biding.fabAdicionar.setOnClickListener{

            val intent = Intent(this, AdicionarTarefaActivity::class.java)
            startActivity( intent )
        }

        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExlcusao(id) },
            { tarefa -> editar(tarefa) }
        )
        biding.rvTarefas.adapter = tarefaAdapter
        biding.rvTarefas.layoutManager = LinearLayoutManager(this)

    }

    private fun editar(tarefa: Tarefa) {

        val intent = Intent(this, AdicionarTarefaActivity:: class.java)
        intent.putExtra("tarefa", tarefa)

        startActivity(intent)

    }

    private fun confirmarExlcusao(id: Int) {

        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Confirmar Exclusão")
        alertBuilder.setMessage("Deseja realmente excluir a terafa ?")

        alertBuilder.setPositiveButton("Sim") { _,_, ->

            val tarefaDAO = TarefaDAO(this)
            if (tarefaDAO.remover(id)) {
                atualizarListaTarefas()
                Toast.makeText(
                    this,
                    "Sucesso ao remover Tarefa", Toast.LENGTH_SHORT
                ).show()
            }else {
                Toast.makeText(
                    this,
                    "Erro ao remover Tarefa", Toast.LENGTH_SHORT
                ).show()
            }
        }
        alertBuilder.setNegativeButton("Não") { _,_, -> }
        alertBuilder.create().show()
    }

    private fun  atualizarListaTarefas() {

        val tarefaDAO = TarefaDAO(this)
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)
    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()
    }

}