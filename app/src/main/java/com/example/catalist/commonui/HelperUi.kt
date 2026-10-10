package com.example.catalist.commonui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue


import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage


@Composable
fun LoadIcon(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(36.dp),
        )
    }
}

@Composable
fun ErrorIcon(){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                imageVector = Icons.Default.Error,
                contentDescription = null
            )
        }
    }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(onSearch: (query: String?) -> Unit, modifier: Modifier=Modifier) {
    var query by rememberSaveable { mutableStateOf("") }
    val xIcon by remember {derivedStateOf {  query.isNotEmpty()}}

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(52.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick ={ onSearch(query); query=""},
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
        TextField(
            value = query,
            onValueChange = { query = it; onSearch(query) },
            placeholder = {Text("Search...")},
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
                .fillMaxWidth()
            ,
//            keyboardOptions = KeyboardOptions.Default.copy(
//                imeAction = ImeAction.Done
//            ),
//            keyboardActions = KeyboardActions(
//                onDone = {
//                    onSearch(query)
//                    query = "";
//                }
//            )
        )
        if(xIcon) {
            IconButton(
                onClick = { query = ""; onSearch(query) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Search"
                )
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun MyTopBar(
    text: String,
    navigationIcon: ImageVector? = null,
    navigationOnClick: (() -> Unit)? = null,
    modifier: Modifier=Modifier
) {
    CenterAlignedTopAppBar(
        modifier=modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(
                    onClick = {navigationOnClick?.invoke()} ,
                ) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "",
                    )
                }
            }
        },
        title = {
            Text(text = text)
        },

        )
}


@Composable
fun TextIndicator(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
        )
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorIndicator(error: Exception)
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = error.message
                    ?: "Unable to load error.",
                textAlign = TextAlign.Center,
            )
        }
}

@Composable
fun MyFigure(imgUrl:String, modifier: Modifier=Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = { LoadingIndicator() },
            error = { ErrorIndicator(it.result.throwable as Exception) },
        )
    }
}