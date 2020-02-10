package com.ondinnonk.testisolated.list

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.ondinnonk.testisolated.Config
import com.ondinnonk.testisolated.Config.UPDATE_TIME_INTERVAL_SEC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownServiceException
import java.util.*


class NetRepository {

    private var serverJson: JSONArray? = null
    private var lastServerJsonUpdate: Long = 0

    fun loadImage(url: String): Bitmap? {
        return try {
            val connection = URL(url)
                .openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * @param forceServerRequest false -> request server with interval in [Config.UPDATE_TIME_INTERVAL_SEC] seconds
     * [<p>]                     true -> take data from server
     */
    suspend fun getFilmsList(forceServerRequest: Boolean = false): List<Film> {
        serverJson?.let {
            if (forceServerRequest.not() &&
                (Date().time - lastServerJsonUpdate) < UPDATE_TIME_INTERVAL_SEC * 1000
            ) {
                return Film.create(it)
            }
        }
        withContext(Dispatchers.IO) {
            getJSON(Config.SOURCE_URL)
        }?.let { return Film.create(it) } ?: return emptyList()
    }

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

            serverJson = JSONArray(response.toString())
            lastServerJsonUpdate = Date().time
            return serverJson
        } catch (ioe: IOException) {
            Log.e(this::class.java.name, "Failed to fetch data.", ioe)
            return null
        } catch (use: UnknownServiceException) {
            Log.e(this::class.java.name, "Failed to fetch data.", use)
            return null
        }
    }

}