package com.example.moodup.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moodup.MainActivity
import com.example.moodup.R
import com.example.moodup.data.Comic
import kotlin.math.log

class ComicsAdapter(
    private var comics: List<Comic>,
    private val loadMoreListener: (() -> Unit)? = null
) : RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
        return ComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comic = comics[position]
        holder.bind(comic)

        if (position == comics.size - 1) {
            loadMoreListener?.invoke()
        }
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    fun updateComics(newComics: List<Comic>) {
        comics = newComics
        notifyDataSetChanged()
    }


    class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val comicThumbnail: ImageView = itemView.findViewById(R.id.comicThumbnail)
        private val comicTitle: TextView = itemView.findViewById(R.id.comicTitle)
        private val comicAuthor: TextView = itemView.findViewById(R.id.comicAuthor)
        private val comicDescription: TextView = itemView.findViewById(R.id.comicDescription)

        @SuppressLint("SetTextI18n")
        fun bind(comic: Comic) {
            comic.title?.takeIf { it.isNotBlank() }?.let {
                comicTitle.text = it
            } ?: run {
                comicTitle.text = "No Title"
            }

            comic.description?.takeIf { it.isNotEmpty() }?.let {
                comicDescription.text = it
            } ?: run {
                comicDescription.text = "No Description"
            }
            comic.creators?.items?.takeIf { it.isNotEmpty() }?.joinToString(", ") { it.name.toString() }?.let {
                comicAuthor.text = "written by $it\n"
            } ?: run {
                comicAuthor.text = "Unknown Authors\n"
            }
            comic.thumbnail?.url?.let {
                val part1 = it.substring(0, 4)
                val part2 = it.substring(4)
                val charToAdd = 's'
                comicThumbnail.load("$part1$charToAdd$part2")
            }
            itemView.setOnClickListener {
                val activity = itemView.context as MainActivity
                activity.showComicDetail(comic)
            }
        }

    }
}
