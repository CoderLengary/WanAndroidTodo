package com.example.lengary_l.wanandroidtodo.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by CoderLengary
 */
class RetrofitClient private constructor(){

    companion object {
        fun getInstance() = ClientHolder.retrofit
    }


    private object ClientHolder {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}