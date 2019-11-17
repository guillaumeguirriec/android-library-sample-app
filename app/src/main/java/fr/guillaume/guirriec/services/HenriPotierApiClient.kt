package fr.guillaume.guirriec.services

import fr.guillaume.guirriec.data.Book
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface HenriPotierApiClient {
    @GET("books") fun getBooks(): Deferred<Response<List<Book>>>
}