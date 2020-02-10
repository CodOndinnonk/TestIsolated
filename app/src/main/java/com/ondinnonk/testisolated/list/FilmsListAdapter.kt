package com.ondinnonk.testisolated.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ondinnonk.testisolated.R
import com.ondinnonk.testisolated.utils.DateUtil
import com.ondinnonk.testisolated.utils.ImageCache
import kotlinx.android.synthetic.main.item_film_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmsListAdapter(val onItemSelect: (f: Film) -> Unit) :
    RecyclerView.Adapter<FilmsListAdapter.FilmListItemViewHolder>() {

    private var films: ArrayList<Film> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmListItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_film_list, parent, false)
    )

    override fun onBindViewHolder(holder: FilmListItemViewHolder, position: Int) =
        holder.bind(films[position])

    override
    fun getItemCount() = films.size

    fun setFilms(newCalls: List<Film>) {
        this.films.clear()
        this.films.addAll(newCalls)
        notifyDataSetChanged()
    }

    inner class FilmListItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(film: Film) {
            with(itemView) {
                GlobalScope.launch {
                    setImage(item_film_list_image, film.imageURL)
                }
                item_film_list_name.text = film.name
                item_film_list_date.text =
                    DateUtil.getDate(film.date, DateUtil.dd_MMMM_yyyy_HH_MM)
                setOnClickListener { onItemSelect(film) }
            }
        }

        private suspend fun setImage(view: ImageView, url: String) {
            if (ImageCache.has(url).not()) {
                val img = withContext(Dispatchers.IO) {
                    NetRepository().loadImage(url)
                }
                img?.let { ImageCache.put(url, it) }
            }

            withContext(Dispatchers.Main) {
                view.setImageBitmap(ImageCache.getCached(url))
            }
        }

    }


}

