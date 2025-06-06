package com.example.bibliotecadigitalappd.presentation.screens.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bibliotecadigitalappd.data.remote.dtos.ResenaDto
import com.example.bibliotecadigitalappd.presentation.screens.catalog.RatingBar

@Composable
fun ResenaProfileItem(
    resena: ResenaDto,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = resena.tituloLibro ?: "Libro desconocido",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, "Editar reseña")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, "Eliminar reseña")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // RatingBar modificado para aceptar el tamaño
            Box(modifier = Modifier.height(24.dp)) {
                RatingBar(
                    rating = resena.puntuacion.toFloat(),
                    modifier = Modifier.size(16.dp * resena.puntuacion) // Ajuste del tamaño
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = resena.comentario ?: "Sin comentario",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = resena.fechaResena ?: "Fecha desconocida",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}