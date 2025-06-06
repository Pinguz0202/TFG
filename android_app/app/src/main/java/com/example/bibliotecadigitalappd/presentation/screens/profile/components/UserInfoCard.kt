package com.example.bibliotecadigitalappd.presentation.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bibliotecadigitalappd.data.remote.dtos.UsuarioDto

@Composable
fun UserInfoCard(user: UsuarioDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${user.nombre}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Rol: ${user.rol}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Registrado desde: ${user.fechaRegistro}", style = MaterialTheme.typography.bodySmall)
        }
    }
}