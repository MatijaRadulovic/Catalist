package com.example.catalist.breedalbum

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.catalist.commonui.ErrorIndicator
import com.example.catalist.commonui.LoadingIndicator
import com.example.catalist.commonui.MyFigure
import com.example.catalist.commonui.MyTopBar
import com.example.catalist.commonui.TextIndicator
import com.example.catalist.db.model.CatDbPhoto

@Composable
fun CatAlbumGridScreen(
    viewModel: CatAlbumViewModel,
    onPhotoClick: (catId:String, catIndex:Int)->Unit,
    onExit: ()-> Unit,
){
    val state=viewModel.state.collectAsState().value
    CatAlbumGridScreen(state=state,onPhotoClick=onPhotoClick,onExit=onExit)

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatAlbumGridScreen(
    state: CatAlbumViewContract.AlbumUiState,
    onPhotoClick: (catId:String, catIndex:Int)->Unit,
    onExit: ()-> Unit,
){
    val model=state.data.map { it.imgUrl }
    Log.d("mojtag", "asdgaasdfadsf: $model ")

    Scaffold(
        topBar = {
            MyTopBar("Galerija slika",Icons.Default.ArrowBackIosNew,onExit)
        },
        content = { paddingValues ->
            if (state.loading) {
                LoadingIndicator()

            } else if (state.error != null) {
                TextIndicator(
                    text = "Error = ${state.error.message}",
                )
                Log.d("mojtag", "CatAlbumGridScreen: sadfsda")

            } else if (state.data.isEmpty()) {

                TextIndicator(
                    text = "404 Cat not found.",
                )
            } else {
                Log.d("mojtag", "CatAlbumGridScreen: $model ")
            BoxWithConstraints(
                modifier = Modifier,
                contentAlignment = Alignment.BottomCenter,
            ) {
                val screenWidth = this.maxWidth
                val cellSize = (screenWidth / 3) - 6.dp
                if (state.data.isEmpty() && state.loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(36.dp),
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp),
                        columns = GridCells.Fixed(3),
                        contentPadding = paddingValues,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {

                        itemsIndexed(
                            items = state.data,
                            key = { index: Int, modelId: CatDbPhoto -> modelId.photoId }) { index: Int, photo: CatDbPhoto ->
                            Card(
                                modifier = Modifier
                                    .size(cellSize)
                                    .clickable {
                                        onPhotoClick(photo.catId, index)
                                        Log.d("mojtag", "CatAlbumGridScreen: $index")
                                    },
                            ) {
                                MyFigure(photo.imgUrl)
                            }
                        }


                    }
                    }
                }
            }
        },
    )
}