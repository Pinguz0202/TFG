package com.example.bibliotecadigitalappd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.bibliotecadigitalappd.presentation.navigation.AppNavigation
import com.example.bibliotecadigitalappd.presentation.viewmodel.AuthViewModel
import com.example.bibliotecadigitalappd.ui.theme.BibliotecaDigitalAppDTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaDigitalAppDTheme {
                AppNavigation()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val authViewModel: AuthViewModel by viewModels()
        authViewModel.logout()
    }
}