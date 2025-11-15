package com.example.esigram.domains.models.responses

data class PageModel<T>(
    val data: List<T>,
    val totalPages: Int,
    val page: Int,
    val count: Int
) {
    companion object {
        fun <T> createEmptyPageModel(): PageModel<T> {
            return PageModel(
                data = emptyList(),
                totalPages = 0,
                page = 0,
                count = 0
            )
        }
    }
}