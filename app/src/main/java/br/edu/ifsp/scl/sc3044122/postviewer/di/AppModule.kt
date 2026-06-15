package br.edu.ifsp.scl.sc3044122.postviewer.di

import android.content.Context
import androidx.room.Room
import br.edu.ifsp.scl.sc3044122.postviewer.data.PostRepository
import br.edu.ifsp.scl.sc3044122.postviewer.data.local.AppDatabase
import br.edu.ifsp.scl.sc3044122.postviewer.data.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Módulo de injeção de dependências manual (sem Hilt/Koin).
 * Cria e fornece as instâncias de Retrofit, Room e Repository.
 */
object AppModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "postviewer_db"
        ).build()

    fun provideRepository(context: Context): PostRepository =
        PostRepository(
            api = apiService,
            db = provideDatabase(context)
        )
}