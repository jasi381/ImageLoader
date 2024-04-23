package com.jasmeet.imageloader

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.jasmeet.imageloader.ui.theme.ImageLoaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageLoaderTheme {
                Surface(Modifier.fillMaxSize()) {
                    ImageComponent(url = "https://images.unsplash.com/3/jerry-adney.jpg?q=80&w=1176&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                }
            }
        }
    }
}

@Composable
fun ImageComponent(modifier: Modifier = Modifier, url: String) {

    var isLoading by remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    if (bitmap == null && isLoading) {
        ImageLoader2.loadImageFromUrl(
            url,
            onSuccess = {
                bitmap = it
                isLoading = false
            },
            onError = {error ->
                isLoading = false
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

            },
            onLoading = {
                Log.d("Main","Loading")
            }
        )
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            bitmap?.let { loadedBitmap ->
                Image(
                    bitmap = loadedBitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier= Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

    }

}



