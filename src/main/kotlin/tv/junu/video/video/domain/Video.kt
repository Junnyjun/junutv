package tv.junu.video.video.domain

data class Video(
    val videoId: String,
    val title: String,
    val description: String,
    val thumbnail: String,
){
    init {
        require(videoId.isNotBlank()) { IllegalArgumentException("videoId must not be blank")  }
        require(title.isNotBlank()) { IllegalArgumentException("title must not be blank")  }
    }
}
