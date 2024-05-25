package com.example.playlistmaker

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.Locale

class TrackAdapter(private var listTrack: ArrayList<Track>?) :
    RecyclerView.Adapter<TrackAdapter.TrackHolder>() {
    class TrackHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val trackName: TextView = itemView.findViewById(R.id.trackName)
        private val artistName: TextView = itemView.findViewById(R.id.artistName)
        private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
        private val imageUrl: ImageView = itemView.findViewById(R.id.trackImage)

        // Преобразование радиуса изгиба из dp в px
        fun dpToPx(dp: Int, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }

        fun bind(track: Track) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackTime.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            Glide.with(itemView).load(track.artworkUrl100).placeholder(R.drawable.placeholder)
                .transform(
                    RoundedCorners(dpToPx(2, itemView.context))
                ).into(imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track, parent, false)
        return TrackHolder(view)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(listTrack!![position])
        holder.itemView.setOnClickListener {
            HistorylistTrack.add(listTrack!![position])

        }


    }

    override fun getItemCount(): Int {
        return listTrack!!.size
    }
}