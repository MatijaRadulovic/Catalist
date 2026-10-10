package com.example.catalist.breedlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.catalist.breedlist.CatListViewContract.ListUiEvent
import com.example.catalist.commonui.ErrorIcon
import com.example.catalist.commonui.LoadIcon
import com.example.catalist.commonui.MySearchBar
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.catalist.commonui.LoadingIndicator
import com.example.catalist.commonui.MyFigure
import com.example.catalist.commonui.TextIndicator


@Composable
fun CatListScreen(
    viewModel: CatViewModel,
    onCatClick: (String) -> Unit,
    onImgClick: (String) -> Unit
) {
    val state = viewModel.state.collectAsState()
   CatListScreen(state=state.value,eventPublisher= { viewModel.setEvent(it) } ,onCatClick=onCatClick,onImgClick=onImgClick)


}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun CatListScreen(
    state: CatListViewContract.ListUiState,
    eventPublisher: (ListUiEvent.SearchData) -> Unit,
    onCatClick: (id: String) -> Unit,
    onImgClick: (String)->Unit
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Catalist") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            stickyHeader(contentType = "Sticky Header") {
                MySearchBar(onSearch = { query ->
                    eventPublisher(ListUiEvent.SearchData(query))
                })
            }

            when {
                state.loading -> {
                    item {
                        LoadingIndicator()
                    }
                }

                state.error != null -> {
                    item {
                        TextIndicator(text = "Error: ${state.error.message}")
                    }
                }

                state.data.isEmpty() -> {
                    item {
                        TextIndicator(text = "There are no cats.")
                    }
                }

                else -> {
                    items(state.data, key = { it.id }) { model ->
                        CatListItem(
                            model = model,
                            onCatClick = onCatClick,
                            onImgClick=onImgClick
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun CatListItem(
    model: CatListModel,
    onCatClick: (String) -> Unit,
    onImgClick: (String) -> Unit,
) {


    Column(
    modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .clickable { onCatClick(model.id) }
        .padding(8.dp)
        .fillMaxWidth()
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyFigure(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = {onImgClick(model.id)})
            ,
            imgUrl = model.imageURL,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = model.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = model.altNames,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = model.description,
                fontSize = 12.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    Spacer(modifier = Modifier.height(6.dp))

    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp),

    ) {
        model.temperament.forEach { chip ->
            SuggestionChip(
                label = { Text(text = chip, fontSize = 10.sp) },
                onClick = {},
                modifier = Modifier
                    .defaultMinSize(minHeight = 24.dp)
            )
        }
    }
}
}
