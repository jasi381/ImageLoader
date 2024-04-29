# ImageLoader2

### A simple image loading utility for loading images from URLs in Android applications without using any third party libraries like `coil/glide`.

## Usage
### Load Image from URL
`To load an image from a URL, use the loadImageFromUrl function:`

## Kotlin
```
ImageLoader2.loadImageFromUrl(
    url = "https://example.com/image.jpg",
    onSuccess = { bitmap ->
        // Handle successful image loading
    },
    onError = { error ->
        // Handle error in image loading
    },
    onLoading = {
        // Handle image loading in progress
    }
)
```
## Dependencies
### This utility requires the following dependencies:

* #### Handler and Looper from the Android SDK for handling threads and posting actions to the main thread.
* #### BitmapFactory for decoding the input stream into a Bitmap.
* #### HttpURLConnection for establishing a connection to the image URL.

## Note
### Make sure to add the following permissions in your AndroidManifest.xml file: 

```
<uses-permission android:name="android.permission.INTERNET"/>
```

## Demo 
### Code snippet for `composable function`

```
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
```

## Utility in Action
![WhatsApp Video 2024-04-23 at 6 24 12 PM](https://github.com/jasi381/ImageLoader/assets/60892009/b1c1d898-a292-4496-8928-b84ddd4b27bb)
