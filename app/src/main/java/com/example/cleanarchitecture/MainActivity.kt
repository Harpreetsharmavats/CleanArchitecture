package com.example.cleanarchitecture

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleanarchitecture.presentation.ui.ViewModel
import com.example.cleanarchitecture.presentation.ui.theme.CleanArchitectureTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanArchitectureTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                var query by rememberSaveable() { mutableStateOf("") }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TextField(modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = query,
                            onValueChange = {
                                query = it
                                viewModel.updateQuery(query.toInt())
                            }
                        )
                    }
                ) { innerPadding ->
                    if(uiState.isLoading){
                        Box(modifier = Modifier.padding(innerPadding),
                            contentAlignment = Alignment.Center){
                            CircularProgressIndicator()
                        }
                    }
                    if (uiState.error.isNotEmpty()){
                        Box(modifier = Modifier.padding(innerPadding),
                            contentAlignment = Alignment.BottomCenter){
                            Text(uiState.error)
                        }
                    }
                    uiState.result.let { result ->
                        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                            items(result){

                            }
                        }
                    }

                }
            }
        }
    }
}
