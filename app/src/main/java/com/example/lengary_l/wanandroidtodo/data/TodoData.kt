package com.example.lengary_l.wanandroidtodo.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by CoderLengary
 */


data class TodoData(

        @SerializedName("data")
        @Expose
        val data: Data,

        @SerializedName("errorCode")
        @Expose
        val errorCode: Int,

        @SerializedName("errorMsg")
        @Expose
        val errorMsg: String

)

data class Data(
        @SerializedName("doneList")
        @Expose
        val allDoneList: List<TodoListData>,

        @SerializedName("todoList")
        @Expose
        val allLiveList: List<TodoListData>,

        @SerializedName("type")
        @Expose
        val type: Int
)

data class TodoListData(
        @SerializedName("date")
        @Expose
        val date: Long,

        @SerializedName("todoList")
        @Expose
        val list: List<TodoDetailData>
)

data class TodoDetailData(
        @SerializedName("completeDate")
        @Expose
        val completeDate: String,

        @SerializedName("completeDateStr")
        @Expose
        val completeDateStr: String,

        @SerializedName("content")
        @Expose
        val content: String,

        @SerializedName("date")
        @Expose
        val date: Long,

        @SerializedName("dateStr")
        @Expose
        val dateStr: String,

        @SerializedName("id")
        @Expose
        val id: Int,

        @SerializedName("status")
        @Expose
        val status: Int,

        @SerializedName("title")
        @Expose
        val title: String,

        @SerializedName("type")
        @Expose
        val type: Int,

        @SerializedName("userId")
        @Expose
        val userId: Int

)

