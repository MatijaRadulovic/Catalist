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
import com.example.catalist.profile.ProfileScreen
import com.example.catalist.profile.ProfileViewModel


private fun NavController.navigateToBreeds() {
    this.navigate(route = "breeds")
}
@Composable
fun StartNavigation(
                   startDestination:String) {
    val navController = rememberNavController()


    NavHost(
        navController=navController,
        startDestination =startDestination
    ) {
        loggedIn(route="breeds",navController=navController,)
        logIn(route = "profile", navController = navController)

    }
}

private fun NavGraphBuilder.loggedIn(
    route: String,
    navController: NavController,
) = composable(
    route = route
) {
    AppNavigation()
}




private fun NavGraphBuilder.logIn(
    route: String,
    navController: NavController,
) = composable(
    route = route
) {
    val profileModel = hiltViewModel<ProfileViewModel>()
    ProfileScreen(
        profileModel,
        {navController.navigateToBreeds()}
    )
}

