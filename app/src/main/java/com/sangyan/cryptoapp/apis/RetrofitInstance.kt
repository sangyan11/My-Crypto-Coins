package com.sangyan.cryptoapp.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
     fun getInstance() : Retrofit  {
         return  Retrofit.Builder()
             .baseUrl("https://api.coinmarketcap.com/")
             .addConverterFactory(GsonConverterFactory.create())
             .build()

     }
}