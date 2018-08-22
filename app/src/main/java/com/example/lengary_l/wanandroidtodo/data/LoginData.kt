package com.example.lengary_l.wanandroidtodo.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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

@Entity( tableName = "login")
data class LoginDetailData(
        @ColumnInfo(name = "collectIds")
        @SerializedName("collectIds")
        @Expose
        val collectIds: List<Int>,

        @ColumnInfo(name = "email")
        @SerializedName("email")
        @Expose
        val email: String,

        @ColumnInfo(name = "icon")
        @SerializedName("icon")
        @Expose
        val icon: String,

        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        @Expose
        val id: Int,

        @ColumnInfo(name = "password")
        @SerializedName("password")
        @Expose
        val password: String,

        @ColumnInfo(name = "token")
        @SerializedName("token")
        @Expose
        val token: String,

        @ColumnInfo(name = "type")
        @SerializedName("type")
        @Expose
        val type: Int,

        @ColumnInfo(name = "username")
        @SerializedName("username")
        @Expose
        val username: String
)