package com.example.esigram.ui.components.utils

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.esigram.R

@Composable
fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 2,
    modifier: Modifier = Modifier
) {
    var isClickable by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(R.color.textPrimary),
        textAlign = TextAlign.Start,
        maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = {
            textLayoutResult ->
            if((!isExpanded && textLayoutResult.hasVisualOverflow) || (isExpanded && textLayoutResult.lineCount > minimizedMaxLines)) {
                isClickable = true
            }
        },
        modifier = modifier
            .then(if (isClickable) Modifier.clickable { isExpanded = !isExpanded } else Modifier)
            .animateContentSize()
    )

}