package com.example.lengary_l.wanandroidtodo.data

import android.arch.persistence.room.TypeConverters
import com.example.lengary_l.wanandroidtodo.room.converter.IntTypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by CoderLengary
 */

data class LoginData(
        @SerializedName("data")
        @Expose
        val data: LoginDetailData,

        @SerializedName("errorCode")
        @Expose
        val errorCode: Int,

        @SerializedName("errorMsg")
        @Expose
        val errorMsg: String
)


@TypeConverters(IntTypeConverter::class)
data class LoginDetailData(

        @SerializedName("collectIds")
        @Expose
        val collectIds: List<Int>,


        @SerializedName("email")
        @Expose
        val email: String,


        @SerializedName("icon")
        @Expose
        val icon: String,


        @SerializedName("id")
        @Expose
        val id: Int,


        @SerializedName("password")
        @Expose
        val password: String,


        @SerializedName("token")
        @Expose
        val token: String,


        @SerializedName("type")
        @Expose
        val type: Int,


        @SerializedName("username")
        @Expose
        val username: String
)