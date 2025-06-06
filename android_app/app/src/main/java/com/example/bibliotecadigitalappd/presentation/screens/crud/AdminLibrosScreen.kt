package com.example.bibliotecadigitalappd.presentation.screens.crud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto

@Composable
fun LibroItem(
    libro: LibroDto,
    onEditar: (LibroDto) -> Unit,
    onEliminar: (LibroDto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "ISBN: ${libro.isbn}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Título: ${libro.titulo ?: "-"}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Autor: ${libro.autor}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Año: ${libro.anioPublicacion}", style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                IconButton(onClick = { onEditar(libro) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { onEliminar(libro) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}