package com.example.catalist.breedalbum

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.example.catalist.commonui.LoadingIndicator
import com.example.catalist.commonui.MyFigure
import com.example.catalist.commonui.MyTopBar
import com.example.catalist.commonui.TextIndicator
import com.example.catalist.db.model.CatDbPhoto
import kotlin.collections.get

@Composable
fun CatGalleryViewScreen(
    viewModel: CatAlbumViewModel,
    onExit: ()-> Unit,
){
    val state=viewModel.state.collectAsState().value
    Log.d("mojtag", "CatGalleryViewScreen: $state")
    CatGalleryViewScreen(state=state,onExit=onExit, startIndex = state.catIndex)

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatGalleryViewScreen(
    state: CatAlbumViewContract.AlbumUiState,
    onExit: ()-> Unit,
    startIndex:Int
){
    val model=state.data.map { it.imgUrl }
    val pagerState = rememberPagerState(
        pageCount = {
            state.data.size
        },
        initialPage = startIndex
    )
    Log.d("mojtag", "CatGalleryViewScreen: ${state.data.size}")
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

            } else if (state.data.isEmpty()) {

                TextIndicator(
                    text = "404 Cat not found.",
                )
            } else {
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

                    HorizontalPager(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = paddingValues,
                        pageSize = PageSize.Fill,
                        pageSpacing = 16.dp,
                        state = pagerState,
                        key = {  state.data[it].photoId },
                    ) { pageIndex ->
                        val photo = state.data[pageIndex]
                            MyFigure(imgUrl =photo.imgUrl)
                    }



                    }
                }
            }
        },
    )
}