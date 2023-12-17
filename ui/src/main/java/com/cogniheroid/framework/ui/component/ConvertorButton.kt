package com.cogniheroid.framework.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConvertorButton(modifier: Modifier = Modifier, label: String, onClick: () -> Unit) {
    val buttonColors = ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
    TextButton(
        shape = RoundedCornerShape(8.dp),
        colors = buttonColors, onClick = {
            onClick()
        },
        modifier = modifier.padding(vertical = 16.dp, horizontal = 32.dp)
    ) {
        androidx.compose.material.Text(
            color = MaterialTheme.colorScheme.onSecondary,
            text = label,
            fontSize = 16.sp
        )
    }
}