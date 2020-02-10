package com.ondinnonk.testisolated.list

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

data class Film(
    val id: Long,
    var name: String,
    val imageURL: String,
    var description: String,
    val date: Long
) {

    companion object {
        const val KEY_ID_JSON = "itemId"
        const val KEY_NAME_JSON = "name"
        const val KEY_IMG_URL_JSON = "image"
        const val KEY_DESCRIPTION_JSON = "description"
        const val KEY_DATE_JSON = "time"


        fun create(json: JSONArray): List<Film> {
            val out = arrayListOf<Film>()
            try {
                for (i in 0 until json.length()) {
                    json.getJSONObject(i)?.let { jObj ->
                        create(jObj)?.let { film ->
                            out.add(film)
                        } ?: Log.w(
                            this::class.java.name,
                            "Json object at position $i is invalid, won't be added. JSONObject = $jObj"
                        )
                    }

                }
            } catch (je: JSONException) {
                Log.e(this::class.java.name, "Fail to parse films list.", je)
            }
            return out
        }

        fun create(json: JSONObject): Film? {
            if (with(json) {
                    has(KEY_ID_JSON) &&
                            has(KEY_NAME_JSON) &&
                            has(KEY_IMG_URL_JSON) &&
                            has(KEY_DESCRIPTION_JSON) &&
                            has(KEY_DATE_JSON)
                }) {
                val id = json.getLong(KEY_ID_JSON)
                val name = json.getString(KEY_NAME_JSON)
                val imgUrl = json.getString(KEY_IMG_URL_JSON)
                val desc = json.getString(KEY_DESCRIPTION_JSON)
                val date = json.getLong(KEY_DATE_JSON)

                when {
                    id < 0 -> {
                        Log.w(this::class.java.name, "Invalid ID ($KEY_ID_JSON) = $id")
                    }
                    date <= 0 -> {
                        Log.w(this::class.java.name, "Invalid DATE ($KEY_DATE_JSON) = $date")
                    }
                }

                //if have some strong validation rules, can set invalid fields with some default, or fail the object
                return Film(id, name, imgUrl, desc, date)
            }
            return null
        }


    }
}
