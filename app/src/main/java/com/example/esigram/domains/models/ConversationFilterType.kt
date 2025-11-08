package com.example.esigram.domains.models

import com.example.esigram.R

enum class ConversationFilterType(val resId: Int) {
    ALL(R.string.filter_all),
    UNREAD(R.string.filter_unread),
}