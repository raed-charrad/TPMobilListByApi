package com.example.listbyapi

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET( "/Offres")
    suspend fun  getOffers (): Response<MutableList<Offre>>

}