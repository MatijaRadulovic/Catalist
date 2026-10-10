package com.example.catalist.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.catalist.breedalbum.CatAlbumGridScreen
import com.example.catalist.breedalbum.CatAlbumViewModel
import com.example.catalist.breedalbum.CatGalleryViewScreen
import com.example.catalist.breeddetails.CatDetailsScreen
import com.example.catalist.breeddetails.CatDetailsViewModel
import com.example.catalist.breedlist.CatListScreen
import com.example.catalist.breedlist.CatViewModel

const val SEARCH_ARG="search"
const val CAT_ID_ARG="cat_id"
const val CAT_IND_ARG="cat_ind"

private fun NavController.navigateToDetails(catId: String) {
    this.navigate(route = "details/$catId")
}

private fun NavController.navigateToBreeds() {
    this.navigate(route = "breeds")
}

private fun NavController.navigateToAlbum(catId: String) {
    this.navigate(route = "details/$catId/album")
}
private fun NavController.navigateToGallery(catId: String,catIndex: Int) {
    this.navigate(route = "details/$catId/gallery/$catIndex")
}

@Composable
fun CatsNavigation(navController: NavHostController,
                   startDestination:String) {
    val navController = navController


    NavHost(
        navController=navController,
        startDestination =startDestination
    ) {
        catList(route = "breeds", navController = navController)
        catDetails(route="details/{$CAT_ID_ARG}",navController=navController,
            arguments = listOf( navArgument(name = CAT_ID_ARG) {
                type = NavType.StringType
                nullable = true
                defaultValue = ""
            }))
        catAlbum(route="details/{$CAT_ID_ARG}/album",navController=navController,
            arguments = listOf( navArgument(name = CAT_ID_ARG) {
                type = NavType.StringType
                nullable = true
                defaultValue = ""
            }))
        catGallery(route="details/{$CAT_ID_ARG}/gallery/{$CAT_IND_ARG}",navController=navController,
            arguments = listOf( navArgument(name = CAT_ID_ARG) {
                type = NavType.StringType
                nullable = true
                defaultValue = ""
            },navArgument(name = CAT_IND_ARG) {
                type = NavType.IntType
                nullable = false
                defaultValue = -1
            }))

    }
}

private fun NavGraphBuilder.catList(
    route: String,
    navController: NavController,
) = composable(
    route = route
) {
    val catListViewModel = hiltViewModel<CatViewModel>()
    CatListScreen(
        catListViewModel,
        onCatClick = {
            catId:String-> navController.navigateToDetails(catId)
        },
        onImgClick ={ catId:String-> navController.navigateToAlbum(catId = catId)}
    )
}


private fun NavGraphBuilder.catDetails(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController,
) = composable(
    route = route,
    arguments=arguments
) {
    Log.d("mojtag", "catDetails: okej")
    val catDetailViewModel=hiltViewModel<CatDetailsViewModel>()
    CatDetailsScreen(viewModel=catDetailViewModel, onExit = {navController.navigateUp()}, onPhotoClick = {navController.navigateToAlbum(it)})
}


private fun NavGraphBuilder.catAlbum(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController,
) = composable(
    route = route,
    arguments=arguments
) {
    val catAlbumViewModel=hiltViewModel<CatAlbumViewModel>()
    CatAlbumGridScreen (viewModel=catAlbumViewModel, onExit = {navController.navigateUp()}, onPhotoClick = {catId: String, catIndex: Int ->navController.navigateToGallery(catId,catIndex)})
}

private fun NavGraphBuilder.catGallery(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController,
) = composable(
    route = route,
    arguments=arguments
) {
    val catDetailViewModel=hiltViewModel<CatAlbumViewModel>()
    Log.d("mojtag", "catGallery: ${navController.currentBackStackEntry?.savedStateHandle?.get<Int>(CAT_IND_ARG)}")
    CatGalleryViewScreen (viewModel=catDetailViewModel, onExit = {navController.navigateUp()})
}