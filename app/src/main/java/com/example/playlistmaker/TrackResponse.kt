package com.example.playlistmaker

data class TrackResponse(val resultCount: Int,
                                val results: ArrayList<Track>){
   data class Track(
        var trackName: String, // Название композиции
        var artistName: String, // Имя исполнителя
        var trackTimeMillis: Long, // Продолжительность трека
        var artworkUrl100: String,

        )
}
