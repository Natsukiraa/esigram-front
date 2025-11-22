package com.example.esigram.ui.components.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.R

@Composable
fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    label: String,
    maxLines: Int = 1
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            color = colorResource(id = R.color.textPrimary),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = colorResource(id = R.color.textPrimary),
                disabledBorderColor = colorResource(id = R.color.textSecondary),
                disabledContainerColor = colorResource(id = R.color.disabledColor)
            ),
            maxLines = maxLines
        )
    }

}