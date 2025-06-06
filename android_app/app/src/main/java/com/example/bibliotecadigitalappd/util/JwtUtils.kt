package com.example.bibliotecadigitalappd.util

import android.util.Base64
import org.json.JSONObject

object JwtUtils {

    fun getRolFromToken(token: String): String? {
        try {
            // El token tiene 3 partes separadas por '.'
            val parts = token.split(".")
            if (parts.size < 2) return null

            // La segunda parte es el payload en Base64
            val payloadEncoded = parts[1]

            // Decodifica Base64 URL Safe
            val decodedBytes = Base64.decode(payloadEncoded, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            val payloadJson = String(decodedBytes, Charsets.UTF_8)

            // Parsear JSON para extraer "rol"
            val jsonObject = JSONObject(payloadJson)
            return jsonObject.optString("rol", null)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
