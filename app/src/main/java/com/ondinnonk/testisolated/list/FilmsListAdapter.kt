package com.ondinnonk.testisolated.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ondinnonk.testisolated.R
import com.ondinnonk.testisolated.utils.DateUtil
import com.ondinnonk.testisolated.utils.NetLoader
import kotlinx.android.synthetic.main.item_film_list.view.*

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
                NetLoader().loadImage(film.imageURL, item_film_list_image)
                item_film_list_name.text = film.name
                item_film_list_date.text = DateUtil.getDate(film.date, DateUtil.dd_MMMM_yyyy_HH_MM)
                setOnClickListener { onItemSelect(film) }
            }
        }
    }


}