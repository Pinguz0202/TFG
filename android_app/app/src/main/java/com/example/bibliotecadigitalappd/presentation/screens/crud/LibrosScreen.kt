package com.example.bibliotecadigitalappd.presentation.screens.crud

import LibroDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto

@Composable
fun LibrosScreen(
    viewModel: LibroViewModel,
    onVolverCatalogo: () -> Unit  // Parámetro para la acción de volver
) {
    val libros by viewModel.libros.collectAsState()
    val error by viewModel.error.collectAsState()

    var libroDialogVisible by remember { mutableStateOf(false) }
    var libroSeleccionado by remember { mutableStateOf<LibroDto?>(null) }

    LaunchedEffect(Unit) {
        viewModel.cargarLibros()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Libros") },
                navigationIcon = {
                    IconButton(onClick = onVolverCatalogo) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver al catálogo")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                libroSeleccionado = null
                libroDialogVisible = true
            }) {
                Icon(Icons.Default.Edit, contentDescription = "Agregar Libro")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (error != null) {
                Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(libros, key = { it.isbn }) { libro ->
                    LibroItem(
                        libro = libro,
                        onEditar = {
                            libroSeleccionado = it
                            libroDialogVisible = true
                        },
                        onEliminar = {
                            viewModel.eliminarLibro(it.isbn)
                        }
                    )
                }
            }
        }
    }

    if (libroDialogVisible) {
        LibroDialog(
            libro = libroSeleccionado,
            onDismiss = { libroDialogVisible = false },
            onGuardar = { libro ->
                if (libroSeleccionado == null) {
                    viewModel.crearLibro(libro) {
                        libroDialogVisible = false
                    }
                } else {
                    viewModel.editarLibro(libro.isbn, libro) {
                        libroDialogVisible = false
                    }
                }
            }
        )
    }
}