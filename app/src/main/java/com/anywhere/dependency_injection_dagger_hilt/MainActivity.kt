package com.anywhere.dependency_injection_dagger_hilt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.anywhere.dependency_injection_dagger_hilt.products.presentation.productSearch.ProductSearchScreen
import com.anywhere.dependency_injection_dagger_hilt.products.presentation.productSearch.SearchViewModel
//import com.anywhere.dependency_injection_dagger_hilt.presentation.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

    /*        val navController = rememberNavController()
            NavGraph(navController)*/

            val viewModel: SearchViewModel = hiltViewModel()
            ProductSearchScreen(viewModel)
        }
    }
}






