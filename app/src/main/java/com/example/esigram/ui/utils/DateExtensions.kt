package com.example.esigram.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.esigram.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun formatConversationDate(timestamp: Instant): String {
    val context = LocalContext.current
    val now = LocalDate.now(ZoneId.systemDefault())
    val messageDate = timestamp.atZone(ZoneId.systemDefault()).toLocalDate()

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    return when {
        messageDate.isEqual(now) -> timeFormatter.format(timestamp.atZone(ZoneId.systemDefault()))
        messageDate.isEqual(now.minusDays(1)) -> context.getString(R.string.yesterday)
        else -> dateFormatter.format(timestamp.atZone(ZoneId.systemDefault()))
    }
}