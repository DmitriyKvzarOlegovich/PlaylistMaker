package com.example.playlistmaker

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class Track(
    var trackName: String, // Название композиции
    var artistName: String, // Имя исполнителя
    var trackTime: String, // Продолжительность трека
    var artworkUrl100: String,
) // Ссылка на изображение обложки



class ListTrack {
    fun filinglistTrack(): MutableList<Track> {
        var listTrack: MutableList<Track> = mutableListOf()
        with(listTrack) {
            var track = Track(
                "Smells Like Teen Spirit", "Nirvana", "5:01",
                "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
            )
            add(track)
            track = Track(
                "Billie Jean",
                "Michael Jackson",
                "4:35",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
            )
            add(track)
            track = Track(
                "Stayin' Alive",
                "Bee Gees",
                "4:10",
                "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
            )
            add(track)
            track = Track(
                "Whole Lotta Love", "Led Zeppelin", "5:33",
                "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
            )
            add(track)
            track =
                Track("Sweet Child O'Mine", "Guns N' Roses", "5:03", "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg")
            add(track)
        }
        return listTrack
    }
}

class TrackAdapter(var listTrack: MutableList<Track>) :
    RecyclerView.Adapter<TrackAdapter.TrackHolder>() {

    class TrackHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val trackName: TextView = itemView.findViewById(R.id.trackName)
        private val artistName: TextView = itemView.findViewById(R.id.artistName)
        private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
        private val imageUrl: ImageView = itemView.findViewById(R.id.trackImage)

        // Преобразование радиуса изгиба из dp в px
        var context: Context = item.getContext()
        var px: Int=dpToPx(2, context)
        fun dpToPx(dp: Int, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                context.resources.displayMetrics).toInt()
        }

        fun bind(track: Track) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackTime.text = track.trackTime

            Glide.with(itemView).load(track.artworkUrl100).placeholder(R.drawable.placeholder).transform(RoundedCorners(px)).into(imageUrl)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track, parent, false)
        return TrackHolder(view)
    }
    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(listTrack[position])
    }
    override fun getItemCount(): Int {
        return listTrack.size
    }
}

