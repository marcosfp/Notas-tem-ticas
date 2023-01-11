package com.example.notastematicas.rest.quotes

import com.google.gson.annotations.SerializedName

data class JokeResponse(
    @SerializedName("type") var type: String,
    @SerializedName("setup") var setup: String,
    @SerializedName("punchline") var punchline: String,
    @SerializedName("id") var id: Int
)

