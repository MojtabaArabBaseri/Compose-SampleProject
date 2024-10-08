package ir.millennium.composesample.core.model

enum class TypeLanguage(val typeLanguage: String) {
    ENGLISH("en"), PERSIAN("fa");

    companion object {
        fun create(value: String): TypeLanguage {
            return when (value) {
                "en" -> ENGLISH
                "fa" -> PERSIAN
                else -> throw IllegalStateException()
            }
        }
    }
}