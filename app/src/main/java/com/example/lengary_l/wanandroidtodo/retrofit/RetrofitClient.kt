package com.example.lengary_l.wanandroidtodo.retrofit

import com.example.lengary_l.wanandroidtodo.retrofit.cookies.AddCookieInterceptor
import com.example.lengary_l.wanandroidtodo.retrofit.cookies.GetCookieInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

        val httpClient: OkHttpClient = OkHttpClient.Builder().let {

            it.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(AddCookieInterceptor())
                    .addInterceptor(GetCookieInterceptor())


            it.build()
        }

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}