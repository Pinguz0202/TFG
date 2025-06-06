package com.example.bibliotecadigitalappd.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.util.Log
import com.example.bibliotecadigitalappd.presentation.screens.auth.LoginScreen
import com.example.bibliotecadigitalappd.presentation.screens.auth.RegisterScreen
import com.example.bibliotecadigitalappd.presentation.screens.catalog.CatalogoScreen
import com.example.bibliotecadigitalappd.presentation.screens.crud.LibroViewModel
import com.example.bibliotecadigitalappd.presentation.screens.crud.LibrosScreen
import com.example.bibliotecadigitalappd.presentation.screens.detail.DetalleLibroScreen
import com.example.bibliotecadigitalappd.presentation.screens.profile.ProfileScreen
import com.example.bibliotecadigitalappd.presentation.screens.profile.ProfileViewModel
import com.example.bibliotecadigitalappd.presentation.screens.profile.screens.EditarResenaScreen
import com.example.bibliotecadigitalappd.presentation.viewmodel.AuthViewModel
import com.example.bibliotecadigitalappd.presentation.viewmodel.CatalogoViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel = hiltViewModel<AuthViewModel>()

    val userRole by authViewModel.userRole.collectAsState(initial = "")

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("catalogo") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate("catalogo") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        composable("catalogo") {
            val catalogoViewModel = hiltViewModel<CatalogoViewModel>()
            CatalogoScreen(
                navController = navController,
                viewModel = catalogoViewModel,
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("catalogo") { inclusive = true }
                    }
                },
                onGestionLibros = {
                    Log.d("AppNavigation", "Intento de acceso a admin con rol: $userRole")  // LOG añadido
                    if (userRole.equals("ADMIN", ignoreCase = true)) {
                        navController.navigate("libros")
                    } else {
                        Log.d("AppNavigation", "Acceso denegado. Rol insuficiente: $userRole")  // LOG añadido
                        // Aquí puedes añadir Toast/Snackbar para avisar al usuario
                    }
                }
            )
        }

        composable("libros") {
            if (userRole == "ADMIN") {
                val libroViewModel = hiltViewModel<LibroViewModel>()
                LibrosScreen(
                    viewModel = libroViewModel,
                    onVolverCatalogo = {
                        navController.navigate("catalogo") {
                            popUpTo("libros") { inclusive = true }
                        }
                    }
                )
            } else {
                navController.navigate("catalogo") {
                    popUpTo("libros") { inclusive = true }
                }
            }
        }

        composable(
            route = "detalle/{libroId}",
            arguments = listOf(navArgument("libroId") { type = NavType.StringType })
        ) { backStackEntry ->
            val libroId = backStackEntry.arguments?.getString("libroId") ?: ""
            DetalleLibroScreen(
                libroId = libroId,
                navController = navController
            )
        }

        composable("perfil") {
            val profileViewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                viewModel = profileViewModel,
                onNavigateToDetail = { libroIsbn ->
                    navController.navigate("detalle/$libroIsbn")
                },
                onEditResena = { resenaId ->
                    navController.navigate("editarResena/$resenaId")
                },
                onNavigateToCatalog = {
                    navController.navigate("catalogo") {
                        popUpTo("perfil") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "editarResena/{resenaId}",
            arguments = listOf(navArgument("resenaId") { type = NavType.LongType })
        ) { backStackEntry ->
            val resenaId = backStackEntry.arguments?.getLong("resenaId") ?: 0L
            EditarResenaScreen(
                resenaId = resenaId,
                navController = navController
            )
        }
    }
}