package com.example.bibliotecadigitalappd.presentation.screens.detail.components

import androidx.compose.foundation.clickable // Importado para hacer las estrellas clickables
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.floor

@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier, // Modificador para el Row contenedor
    maxStars: Int = 5,
    starSize: Dp = 24.dp, // Tamaño individual de cada icono de estrella
    filledColor: Color = MaterialTheme.colorScheme.primary, // Color para estrellas llenas
    emptyColor: Color = MaterialTheme.colorScheme.outline, // Color para estrellas vacías
    onRatingChanged: ((Float) -> Unit)? = null // ¡Reintroducido para interactividad!
) {
    Row(modifier = modifier) {
        val currentWholeStars = floor(rating).toInt()
        val hasHalfStar = rating - currentWholeStars >= 0.5f

        for (i in 1..maxStars) {
            val iconImageVector = when {
                i <= currentWholeStars -> Icons.Filled.Star // Estrella completa
                i == currentWholeStars + 1 && hasHalfStar -> Icons.Filled.StarHalf // Media estrella
                else -> Icons.Outlined.Star // Estrella vacía
            }

            // El color de la estrella se basa en si su posición está por debajo o igual a la calificación actual.
            val iconTint = if (i <= rating) filledColor else emptyColor

            // Modificador condicional para hacerla clickeable solo si onRatingChanged no es nulo
            val starModifier = Modifier
                .size(starSize)
                .then(
                    if (onRatingChanged != null) {
                        Modifier.clickable { onRatingChanged(i.toFloat()) }
                    } else {
                        Modifier // No se añade el modificador clickable si no hay callback
                    }
                )

            Icon(
                imageVector = iconImageVector,
                // La descripción de contenido cambia según si es interactiva o solo de visualización
                contentDescription = if (onRatingChanged != null) "Calificar con $i estrellas" else "Calificación: $rating estrellas",
                tint = iconTint,
                modifier = starModifier
            )
        }
    }
}