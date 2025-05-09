package com.naufalmaulanaartocarpussavero607062300078.asesment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.naufalmaulanaartocarpussavero607062300078.asesment2.navigation.SetupNavGraph
import com.naufalmaulanaartocarpussavero607062300078.asesment2.ui.theme.Asesment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SetupNavGraph()
        }
    }
}