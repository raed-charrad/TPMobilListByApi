package com.example.listbyapi

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET( "/Offres")
    suspend fun  getOffers (): Response<MutableList<Offre>>
    @DELETE("/Offres/{id}")
    suspend fun deleteOffer(@Path("id") id : Int)
    @POST("/Offres")
    suspend fun addOffer(@Body offer : Offre)
    @PUT("/Offres/{id}")
    suspend fun updateOffer(@Path("id") id : Int, @Body offer : Offre)

}