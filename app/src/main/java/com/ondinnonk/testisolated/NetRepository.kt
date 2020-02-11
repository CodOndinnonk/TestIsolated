package com.ondinnonk.testisolated

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.ondinnonk.testisolated.Config.DATA_UPDATE_TIME_INTERVAL_SEC
import com.ondinnonk.testisolated.Config.RESOURCES_UPDATE_TIME_INTERVAL_SEC
import com.ondinnonk.testisolated.list.Film
import com.ondinnonk.testisolated.utils.ImageCache
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


class NetRepository private constructor() {

    private var serverJson: JSONArray? = null
    private var lastServerJsonUpdate: Long = 0
    private var lastImagesUpdate: Long = 0

    companion object {
        val instance by lazy { NetRepository() }
    }

    /**
     * @param forceImageLoad false -> update image with interval in [Config.RESOURCES_UPDATE_TIME_INTERVAL_SEC] seconds
     * [<p>]                     true -> download image again
     */
    suspend fun getFilmImage(url: String, forceImageLoad: Boolean = false): Bitmap? {
        if (forceImageLoad ||
            ImageCache.has(url).not() ||
            (Date().time - lastImagesUpdate) > RESOURCES_UPDATE_TIME_INTERVAL_SEC * 1000
        ) {
            val img = withContext(Dispatchers.IO) {
                loadImage(url)
            }
            img?.let { ImageCache.put(url, it) }
            return img
        } else {
            return ImageCache.getCached(url)
        }
    }

    /**
     * @param forceServerRequest false -> request server with interval in [Config.DATA_UPDATE_TIME_INTERVAL_SEC] seconds
     * [<p>]                     true -> take data from server
     */
    suspend fun getFilmsList(forceServerRequest: Boolean = false): List<Film> {
        serverJson?.let {
            if (forceServerRequest.not() &&
                (Date().time - lastServerJsonUpdate) < DATA_UPDATE_TIME_INTERVAL_SEC * 1000
            ) {
                return Film.create(it)
            }
        }
        withContext(Dispatchers.IO) {
            getJSON(Config.SOURCE_URL)
        }?.let { return Film.create(it) } ?: return emptyList()
    }

    private fun loadImage(url: String): Bitmap? {
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

    private fun getJSON(url: String): JSONArray? {
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