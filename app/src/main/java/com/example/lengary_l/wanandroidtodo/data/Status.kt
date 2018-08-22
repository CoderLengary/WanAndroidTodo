package com.example.lengary_l.wanandroidtodo.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by CoderLengary
 */
data class Status (

        @SerializedName("data")
        @Expose
        val data: TodoDetailData,

        @SerializedName("errorCode")
        @Expose
        val errorCode: Int,

        @SerializedName("errorMsg")
        @Expose
        val errorMsg: String


)