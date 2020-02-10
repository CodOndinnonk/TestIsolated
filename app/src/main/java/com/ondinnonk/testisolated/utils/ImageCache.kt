package com.ondinnonk.testisolated.utils

import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache


object ImageCache {
    private var cacheSize: Int = 4 * 1024 * 1024 // 4MiB
    private var cache = LruCache<String, Bitmap>(cacheSize)

    /**
     * true -> if has NonNull value
     */
    fun has(url: String): Boolean {
        cache.get(url)?.let { return true } ?: return false
    }

    fun getCached(url: String): Bitmap? {
        return cache.get(url)
    }

    fun put(url: String, img: Bitmap) {
        if (url.isEmpty()) {
            Log.w(this::class.java.name, "Put cache with empty url.")
        }
        cache.put(url, img)
    }

}