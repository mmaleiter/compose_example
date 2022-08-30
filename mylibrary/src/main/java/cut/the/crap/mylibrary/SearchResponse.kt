package cut.the.crap.mylibrary

data class SearchResponse (
    val total: Long,
    val totalHits: Long,
    val hits: List<PixaBayItem>
)