package com.example.lengary_l.wanandroidtodo.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by CoderLengary
 */
data class DoOrUndoListData(
        @SerializedName("data")
        @Expose
        val data: InnerData,

        @SerializedName("errorCode")
        @Expose
        val errorCode: Int,

        @SerializedName("errorMsg")
        @Expose
        val errorMsg: String
)

data class InnerData(
        @SerializedName("curPage")
        @Expose
        val curPage: Int,

        @SerializedName("datas")
        @Expose
        val datas: List<TodoDetailData>,

        @SerializedName("offset")
        @Expose
        val offset: Int,

        @SerializedName("over")
        @Expose
        val over: Boolean,

        @SerializedName("pageCount")
        @Expose
        val pageCount: Int,

        @SerializedName("size")
        @Expose
        val size: Int,

        @SerializedName("total")
        @Expose
        val total: Int
)