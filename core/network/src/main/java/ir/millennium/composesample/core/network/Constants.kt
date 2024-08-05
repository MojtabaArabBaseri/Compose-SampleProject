package ir.millennium.composesample.core.network


object Constants {

    const val BASIC_URL = "http://newsapi.org/v2/"

    const val API_KEY = "1f02e97880074860bd0aa75e83801ff4"

    const val CACHE_NAME = "SampleComposeProjectCache"

    const val HEADER_CACHE_CONTROL = "Cache-Control"

    const val HEADER_PRAGMA = "Pragma"

    const val CACHE_SIZE_FOR_RETROFIT = (5 * 1024 * 1024).toLong()

    const val MAX_AGE = 60 * 60 * 24

    const val MAX_STALE = 60 * 60 * 24 * 7

    const val SIZE_PAGE = 20

}