package com.ondinnonk.testisolated.utils

import android.util.Log
import android.view.View
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownServiceException


class NetLoader {

    fun loadImage(url: String, host: View) {}

    fun getJSON(url: String): JSONArray? {
        val obj = URL(url)
        val connection: HttpURLConnection = obj.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json")

        try {
            val bufferedReader =
                BufferedReader(InputStreamReader(connection.getInputStream()))
            var inputLine: String?
            val response = StringBuffer()

            while (bufferedReader.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            bufferedReader.close()

            return JSONArray(response.toString())
        } catch (ioe: IOException) {
            Log.e(this::class.java.name, "Failed to fetch data.", ioe)
            return null
        } catch (use: UnknownServiceException) {
            Log.e(this::class.java.name, "Failed to fetch data.", use)
            return null
        }
    }

}