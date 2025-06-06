package com.example.bibliotecadigitalappd.presentation.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bibliotecadigitalappd.presentation.screens.detail.components.RatingBar
import com.example.bibliotecadigitalappd.presentation.screens.detail.components.ResenaItem
import com.example.bibliotecadigitalappd.presentation.screens.detail.viewmodel.DetalleViewModel
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLibroScreen(
    libroId: String,
    viewModel: DetalleViewModel = hiltViewModel(),
    navController: NavController
) {
    val libro by viewModel.libro
    val reseñas = viewModel.reseñas
    val isLoading by viewModel.isLoading
    val error by viewModel.error
    val esFavorito by viewModel.esFavorito

    var comentario by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0f) }

    LaunchedEffect(libroId) {
        viewModel.cargarDetalleLibro(libroId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Libro") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("perfil") }) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil de usuario")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = error ?: "Error desconocido",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.cargarDetalleLibro(libroId) }) {
                            Text("Reintentar")
                        }
                    }
                }

                libro == null -> {
                    Text(
                        text = "Libro no encontrado",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    val libroNoNulo = libro ?: return@Scaffold

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = libroNoNulo.portadaUrl,
                            contentDescription = "Portada de ${libroNoNulo.titulo}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = libroNoNulo.titulo ?: "Título desconocido",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "por ${libroNoNulo.autor ?: "Autor desconocido"}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        // Botón de favoritos alineado a la izquierda
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            IconButton(onClick = { viewModel.toggleFavorito(libroId) }) {
                                Icon(
                                    imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = if (esFavorito) "Quitar de favoritos" else "Añadir a favoritos",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = if (esFavorito) "Quitar de favoritos" else "Añadir a favoritos",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        val puntuacion = libroNoNulo.puntuacionPromedio?.toFloat() ?: 0f

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RatingBar(rating = puntuacion, starSize = 24.dp)
                            Text(
                                text = "(${puntuacion.roundToDecimals(1)})",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Publicado en el año: ${libroNoNulo.anioPublicacion}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Género: ${libroNoNulo.genero ?: "Desconocido"}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Sinopsis",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = libroNoNulo.sinopsis ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Reseñas (${reseñas.size})",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        if (reseñas.isEmpty()) {
                            Text("No hay reseñas aún")
                        } else {
                            Column {
                                reseñas.forEach { reseña ->
                                    ResenaItem(reseña = reseña)
                                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(text = "Tu Reseña", style = MaterialTheme.typography.titleMedium)

                        RatingBar(
                            rating = rating,
                            onRatingChanged = { rating = it },
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        OutlinedTextField(
                            value = comentario,
                            onValueChange = { comentario = it },
                            label = { Text("Comentario") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )

                        Button(
                            onClick = {
                                viewModel.enviarResena(libroId, comentario, rating.toInt())
                                comentario = ""
                                rating = 0f
                            },
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .align(Alignment.End),
                            enabled = comentario.isNotBlank() && rating > 0
                        ) {
                            Text("Enviar Reseña")
                        }
                    }
                }
            }
        }
    }
}

fun Float.roundToDecimals(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}