import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto

@Composable
fun LibroDialog(
    libro: LibroDto?,
    onDismiss: () -> Unit,
    onGuardar: (LibroDto) -> Unit
) {
    var isbn by remember { mutableStateOf(libro?.isbn ?: "") }
    var titulo by remember { mutableStateOf(libro?.titulo ?: "") }
    var autor by remember { mutableStateOf(libro?.autor ?: "") }
    var anioPublicacionText by remember { mutableStateOf(libro?.anioPublicacion?.toString() ?: "") }
    var genero by remember { mutableStateOf(libro?.genero ?: "") }
    var sinopsis by remember { mutableStateOf(libro?.sinopsis ?: "") }
    var portadaUrl by remember { mutableStateOf(libro?.portadaUrl ?: "") }  // <-- NUEVO campo

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (libro == null) "Crear Libro" else "Editar Libro") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = isbn,
                    onValueChange = { isbn = it },
                    label = { Text("ISBN") },
                    enabled = libro == null,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = titulo ?: "",
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = autor,
                    onValueChange = { autor = it },
                    label = { Text("Autor") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = anioPublicacionText,
                    onValueChange = { anioPublicacionText = it.filter { c -> c.isDigit() } },
                    label = { Text("Año de publicación") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = genero ?: "",
                    onValueChange = { genero = it },
                    label = { Text("Género") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = sinopsis ?: "",
                    onValueChange = { sinopsis = it },
                    label = { Text("Sinopsis") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                OutlinedTextField(                           // <-- NUEVO campo TextField
                    value = portadaUrl,
                    onValueChange = { portadaUrl = it },
                    label = { Text("URL Portada") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val anio = anioPublicacionText.toIntOrNull() ?: 0
                if (isbn.isNotBlank() && autor.isNotBlank()) {
                    onGuardar(
                        LibroDto(
                            isbn = isbn,
                            titulo = titulo,
                            autor = autor,
                            anioPublicacion = anio,
                            genero = if (genero.isBlank()) null else genero,
                            sinopsis = if (sinopsis.isBlank()) null else sinopsis,
                            puntuacionPromedio = null,
                            portadaUrl = if (portadaUrl.isBlank()) null else portadaUrl   // <-- guardar la URL o null
                        )
                    )
                }
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}