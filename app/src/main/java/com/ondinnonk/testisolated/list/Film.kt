package com.ondinnonk.testisolated.list

import org.json.JSONArray

data class Film(
    val id: Long,
    var name: String,
    val imageURL: String,
    var description: String,
    val date: Long
) {

    companion object {

        fun create(json: JSONArray): List<Film> {

            //TODO TEST
            return arrayListOf(
                Film(0, "0", "", "000", 1457018867393),
                Film(1, "1", "", "111", 1457018867344),
                Film(2, "2", "", "222", 1457018867553),
                Film(3, "3", "", "333", 1457012224546),
                Film(4, "4", "", "444", 1457018861212),
                Film(5, "5", "", "555", 1457018867421)
            )
        }
    }
}
