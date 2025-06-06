package com.example.bibliotecadigitalappd.presentation.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.bibliotecadigitalappd.presentation.screens.profile.components.UserInfoCard
import com.example.bibliotecadigitalappd.presentation.screens.profile.components.LibroFavoritoCard
import com.example.bibliotecadigitalappd.presentation.screens.profile.components.ResenaProfileItem

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToDetail: (isbnLibro: String) -> Unit,
    onEditResena: (resenaId: Long) -> Unit,
    onNavigateToCatalog: () -> Unit
) {
    val user by viewModel.user.collectAsState()
    val favoriteBooks by viewModel.favoriteBooks.collectAsState()
    val userResenas by viewModel.userResenas.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(onClick = onNavigateToCatalog) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            user?.let {
                UserInfoCard(user = it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Libros favoritos", style = MaterialTheme.typography.titleLarge)
            if (favoriteBooks.isEmpty()) {
                Text("No hay libros favoritos")
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    favoriteBooks.forEach { libro ->
                        LibroFavoritoCard(libro = libro) {
                            onNavigateToDetail(libro.isbn)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Reseñas", style = MaterialTheme.typography.titleLarge)
            if (userResenas.isEmpty()) {
                Text("No hay reseñas creadas")
            } else {
                userResenas.forEach { resena ->
                    ResenaProfileItem(
                        resena = resena,
                        onEdit = { onEditResena(resena.id) },
                        onDelete = { viewModel.deleteResena(resena.id) }
                    )
                }
            }
            errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}