package com.example.esigram.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.example.esigram.R
import com.example.esigram.domains.models.ThemeMode


@Composable
fun ThemeSelector(
    selectedTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit
) {
    // ⚠️ On retire l'usage de R.drawable.outline_filter_list_24 pour utiliser Icons.Default.Palette
    // pour plus de cohérence Material 3 et de pertinence graphique.

    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(vertical = 12.dp), // ⬅️ Légère réduction du padding vertical pour mieux s'intégrer
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Add, // Icône de palette pour le thème
            contentDescription = stringResource(R.string.select_theme),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = stringResource(R.string.theme_setting_title),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = stringResource(selectedTheme.labelResId),
            color = MaterialTheme.colorScheme.primary
        )

        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ThemeMode.entries.forEach { themeMode ->
                DropdownMenuItem(
                    text = { Text(stringResource(themeMode.labelResId)) },
                    onClick = {
                        onThemeSelected(themeMode)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeSelectorSystemPreview() {
    MaterialTheme {
        ThemeSelector(
            selectedTheme = ThemeMode.System, // Prévisualisation du thème 'System' sélectionné
            onThemeSelected = {}
        )
    }
}