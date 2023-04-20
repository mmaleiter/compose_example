package cut.the.crap.mylibrary.models

data class SearchResponse (
    val total: Long,
    val totalHits: Long,
    val hits: List<PixaBayItem>
)
