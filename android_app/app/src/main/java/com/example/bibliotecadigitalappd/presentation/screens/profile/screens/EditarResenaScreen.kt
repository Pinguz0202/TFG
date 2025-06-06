package com.example.bibliotecadigitalappd.presentation.screens.profile.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun EditarResenaScreen(
    resenaId: Long,
    navController: NavController,
    viewModel: EditarResenaViewModel = hiltViewModel()
) {
    val puntuacion by viewModel.puntuacion.collectAsState()
    val comentario by viewModel.comentario.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadResena(resenaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar reseña") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        content = { padding ->
            if (loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(padding)
                ) {
                    Text(text = "Puntuación: ${puntuacion.toInt()}", style = MaterialTheme.typography.titleMedium)
                    Slider(
                        value = puntuacion,
                        onValueChange = { viewModel.onPuntuacionChange(it) },
                        valueRange = 0f..5f,
                        steps = 4,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = comentario,
                        onValueChange = { viewModel.onComentarioChange(it) },
                        label = { Text("Comentario") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.guardarCambios(
                                id = resenaId,
                                onSuccess = {
                                    navController.popBackStack()
                                },
                                onError = { error ->
                                    // Puedes manejar aquí el error
                                }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar cambios")
                    }
                }
            }
        }
    )
}