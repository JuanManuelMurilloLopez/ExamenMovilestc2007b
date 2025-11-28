package com.example.examenmoviles.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.examenmoviles.domain.model.DailyInfo

@Composable
fun CountryDetailContent(
    date: String,
    info: DailyInfo?,
    hasPrev: Boolean,
    hasNext: Boolean,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(24.dp)
    ) {

        Text(
            text = date,
            style = MaterialTheme.typography.headlineSmall
        )

        if (info == null) {
            Text("Al parecer no hubo casos ese día!")
        } else if (info.total == 0 && info.new == 0) {
            Text("Al parecer no hubo casos ese día!")
        } else {
            Text("Caosos totales: ${info.total}")
            Text("Casos nuevos: ${info.new}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

            Button(onClick = onPrev, enabled = hasPrev) {
                Text("Anterior")
            }

            Button(onClick = onNext, enabled = hasNext) {
                Text("Siguiente")
            }
        }
    }
}
