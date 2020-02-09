package com.ondinnonk.testisolated.list

data class Film(
    val id: Long,
    var name: String,
    val imageURL: String,
    var description: String,
    val date: Long
)