package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName

data class TVShowDetails(
    var id: Int,
    @SerializedName(value = "original_name")
    var originalName: String,
    var overview: String,
    @SerializedName(value = "vote_average")
    var vote: Float,
    var seasons: List<Season>
)

data class Season(
    var id: Int,
    @SerializedName(value = "air_date")
    var airDate: String,
    @SerializedName(value = "episode_count")
    var episodeCount: Int,
    @SerializedName(value = "poster_path")
    var posterPath: String,
    @SerializedName(value = "season_number")
    var seasonNumber: Int
)



