package com.example.sqlite

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqlite.database.DatabaseHelper
import com.example.sqlite.database.ProdutoDAO
import com.example.sqlite.databinding.ActivityMainBinding
import com.example.sqlite.model.Produto

class MainActivity : AppCompatActivity() {

    private val biding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val bancoDados by lazy {
        DatabaseHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(biding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with (biding) {

            btnSalvar.setOnClickListener {
                salvar()
            }
            btnListar.setOnClickListener {
                listar()
            }
            btnAtualizar.setOnClickListener {
                atualizar()
            }
            btnRemover.setOnClickListener {
                remover()
            }
        }
    }

    private fun remover() {

        val produtoDAO = ProdutoDAO(this)
        produtoDAO.remover(2)
    }

    private fun atualizar() {

        val produtoDAO = ProdutoDAO(this)
        val titulo = biding.editProduto.text.toString()
        val produto = Produto (
            -1,
            titulo,
            "Descricao ..."
        )
        produtoDAO.atualizar(produto)
    }

    private fun listar() {

        val produtoDAO = ProdutoDAO(this)
        val listaProduto = produtoDAO.listar()
        var texto = ""

        if (listaProduto.isNotEmpty()) {
            listaProduto.forEach{produto->
                texto += "${produto.idProduto}: ${produto.titulo} - ${produto.descricao}\n"
            }
            biding.textResultado.text = texto
        }
    }

    private fun salvar() {

        val produtoDAO = ProdutoDAO(this)
        val titulo = biding.editProduto.text.toString()
        val produto = Produto (
            -1,
            titulo,
            "Descricao ..."
        )
        produtoDAO.salvar(produto)
    }
}