package com.example.bibliotecadigitalappd.presentation.screens.profile.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto
import com.example.bibliotecadigitalappd.presentation.screens.detail.components.RatingBar

@Composable
fun LibroFavoritoCard(
    libro: LibroDto,
    onClick: () -> Unit
) {
    if (libro.isbn.isNullOrBlank()) return // Previene crasheos por ISBN nulo o vacío

    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            // Portada del libro con manejo seguro
            val portadaUrl = libro.getSafePortadaUrl()
            if (portadaUrl != null) {
                SubcomposeAsyncImage(
                    model = portadaUrl,
                    contentDescription = libro.getSafeTitulo(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                ) {
                    when (painter.state) {
                        is coil.compose.AsyncImagePainter.State.Loading -> LoadingPlaceholder()
                        is coil.compose.AsyncImagePainter.State.Error -> ErrorPlaceholder()
                        else -> SubcomposeAsyncImageContent()
                    }
                }
            } else {
                DefaultPlaceholder()
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Título seguro
            Text(
                text = libro.getSafeTitulo(),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Rating seguro
            RatingBar(
                rating = libro.getSafeRating(),
                modifier = Modifier.padding(horizontal = 8.dp),
                starSize = 12.dp
            )
        }
    }
}

@Composable
private fun LoadingPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun ErrorPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(MaterialTheme.colorScheme.errorContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.Book, contentDescription = "Error al cargar")
    }
}

@Composable
private fun DefaultPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.Book, contentDescription = "Sin portada")
    }
}