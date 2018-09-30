package com.example.lengary_l.wanandroidtodo.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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

@Entity(tableName = "todo")
data class TodoDetailData(
        @ColumnInfo(name = "completeDate")
        @SerializedName("completeDate")
        @Expose
        val completeDate: Long,

        @ColumnInfo(name = "completeDateStr")
        @SerializedName("completeDateStr")
        @Expose
        val completeDateStr: String,

        @ColumnInfo(name = "content")
        @SerializedName("content")
        @Expose
        val content: String,

        @SerializedName("date")
        @Expose
        val date: Long,

        @ColumnInfo(name = "dateStr")
        @SerializedName("dateStr")
        @Expose
        val dateStr: String,

        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        @Expose
        val id: Int,

        @ColumnInfo(name = "status")
        @SerializedName("status")
        @Expose
        val status: Int,

        @ColumnInfo(name = "title")
        @SerializedName("title")
        @Expose
        val title: String,

        @ColumnInfo(name = "type")
        @SerializedName("type")
        @Expose
        val type: Int,

        @ColumnInfo(name = "userId")
        @SerializedName("userId")
        @Expose
        val userId: Int

)

