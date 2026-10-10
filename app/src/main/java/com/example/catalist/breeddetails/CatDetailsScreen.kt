package com.example.catalist.breeddetails

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.SubcomposeAsyncImage
import com.example.catalist.commonui.ErrorIcon
import com.example.catalist.commonui.LoadIcon
import com.example.catalist.commonui.LoadingIndicator
import com.example.catalist.commonui.MyFigure
import com.example.catalist.commonui.MyTopBar
import com.example.catalist.commonui.TextIndicator


@Composable
public fun CatDetailsScreen(
    viewModel: CatDetailsViewModel,
    onExit: ()-> Unit,
    onPhotoClick: (catId:String)-> Unit
){
    val context=LocalContext.current
    val state = viewModel.state.collectAsState()
    CatDetailsScreen(state=state.value,eventPublisher= { viewModel.setEvent(it) } ,onExit=onExit, onPhotoClick=onPhotoClick)
    LaunchedEffect(state.value.wikiUrl) {
        viewModel.effect.collect {
            when (it) {
                CatDetailsViewContract.CatDetailsEffect.LaunchWikiEffect -> {
                    val intent = Intent(Intent.ACTION_VIEW, state.value.wikiUrl?.toUri())
                    context.startActivity(intent)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
private fun CatDetailsScreen(
    state: CatDetailsViewContract.CatDetailsState,
    eventPublisher: (CatDetailsViewContract.CatDetailsEvent)->Unit,
    onExit: ()->Unit,
    onPhotoClick: (catId:String) -> Unit
){
    val model=state.data
    if(model!=null) {
        Scaffold(
            topBar = {
                MyTopBar("Detaljni podaci",Icons.Default.ArrowBackIosNew,onExit)
            }
        ) { padding ->

            if (state.loading) {
                LoadingIndicator()
            } else if (state.error != null) {
                TextIndicator(
                    text = "Error = ${state.error.message}",
                )
            } else if (state.data == null) {
                TextIndicator(
                    text = "404 Cat not found.",
                )
            } else {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(padding)
                .padding(all = 15.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.clickable(onClick ={ onPhotoClick(state.catId)})){
                    MyFigure(modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp)), imgUrl =model.imageURL)

                }
                Spacer(modifier = Modifier.width(20.dp))

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
                    Spacer(modifier = Modifier.height(20.dp))
                    model.formatInfo().forEach {
                        Text(
                            text = "• $it",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }


                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp),

                ) {
                model.formatTraits().forEach { chip ->
                    SuggestionChip(
                        label = { Text(text = chip, fontSize = 10.sp) },
                        onClick = {},
                        modifier = Modifier
                            .defaultMinSize(minHeight = 24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = model.description,
                fontSize = 12.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
            Button(
                onClick = { eventPublisher(CatDetailsViewContract.CatDetailsEvent.ViewWikiEvent)}
            ) {
                Text(text = "Open wiki")
            }}

        }

            }
        }
    }
}