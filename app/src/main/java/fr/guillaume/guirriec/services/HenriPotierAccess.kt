package fr.guillaume.guirriec.services

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HenriPotierAccess {
    val apiAddress = "http://henri-potier.xebia.fr/"
    val henriPotierApiClient : HenriPotierApiClient by lazy {
        Log.d("HenriPotierAccess", "Creating Retrofit client")
        val retrofit = Retrofit.Builder()
            .baseUrl(apiAddress)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return@lazy retrofit.create(HenriPotierApiClient::class.java)
    }
}
