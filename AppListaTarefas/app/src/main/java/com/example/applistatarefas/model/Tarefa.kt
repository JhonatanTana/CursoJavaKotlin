package com.example.applistatarefas.model

import java.io.Serializable

data class Tarefa (
    val idTarefa: Int,
    val descricao: String,
    val dataCadastr: String
): Serializable