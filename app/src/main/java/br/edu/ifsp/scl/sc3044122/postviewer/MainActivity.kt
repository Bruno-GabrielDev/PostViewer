package br.edu.ifsp.scl.sc3044122.postviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.ifsp.scl.sc3044122.postviewer.di.AppModule
import br.edu.ifsp.scl.sc3044122.postviewer.presentation.detail.DetailScreen
import br.edu.ifsp.scl.sc3044122.postviewer.presentation.detail.DetailViewModel
import br.edu.ifsp.scl.sc3044122.postviewer.presentation.posts.PostsScreen
import br.edu.ifsp.scl.sc3044122.postviewer.presentation.posts.PostsViewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cria o repositório com o contexto da aplicação
        val repository = AppModule.provideRepository(applicationContext)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "posts"
                ) {
                    // Tela 1 — Lista de Posts
                    composable("posts") {
                        val viewModel: PostsViewModel = viewModel(
                            factory = PostsViewModel.Factory(repository)
                        )
                        PostsScreen(
                            viewModel = viewModel,
                            onPostClick = { post ->
                                navController.navigate("detail/${post.id}/${post.title.take(30)}")
                            }
                        )
                    }

                    // Tela 2 — Detalhes do Post
                    composable(
                        route = "detail/{postId}/{postTitle}",
                        arguments = listOf(
                            navArgument("postId")    { type = NavType.IntType },
                            navArgument("postTitle") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val postId    = backStackEntry.arguments?.getInt("postId") ?: 0
                        val postTitle = backStackEntry.arguments?.getString("postTitle") ?: ""

                        val viewModel: DetailViewModel = viewModel(
                            factory = DetailViewModel.Factory(repository, postId)
                        )
                        DetailScreen(
                            postTitle = postTitle,
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}