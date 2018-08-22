package com.example.lengary_l.wanandroidtodo.retrofit

import com.example.lengary_l.wanandroidtodo.data.DoOrUndoListData
import com.example.lengary_l.wanandroidtodo.data.LoginData
import com.example.lengary_l.wanandroidtodo.data.Status
import com.example.lengary_l.wanandroidtodo.data.TodoData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by CoderLengary
 */
interface RetrofitService {

    interface LoginService {
        @FormUrlEncoded
        @POST(Api.LOGIN)
        fun login(@Field("username") userName: String,
                  @Field("password") password: String ): Call<LoginData>

        @FormUrlEncoded
        @POST(Api.REGISTER)
        fun register(@Field("username") userName: String,
                     @Field("password") password: String,
                     @Field("repassword") repassword: String): Call<LoginData>
    }

    interface TodoService {
        @POST(Api.GET_ALL_TODO + "{type}/json")
        fun getAllTodo(@Path("type") type: Int): Call<TodoData>

        @POST(Api.GET_UNDO_TODO + "{type}/json/{page}")
        fun getUndoTodo(@Path("type") type: Int,
                        @Path("page") page: Int): Call<DoOrUndoListData>

        @POST(Api.GET_DONE_TODO + "{type}/json/{page}")
        fun getDoneTodo(@Path("type") type: Int,
                        @Path("page") page: Int): Call<DoOrUndoListData>


        @FormUrlEncoded
        @POST(Api.ADD_TODO)
        fun addTodo(@Field("title") title: String,
                    @Field("content") content: String,
                    @Field("date") date: String,
                    @Field("type") type: Int): Call<Status>

        @FormUrlEncoded
        @POST(Api.UPDATE_TODO + "{id}/json")
        fun updateTodo(@Path("id") id: Int,
                       @Field("title") title: String,
                       @Field("content") content: String,
                       @Field("date") date: String,
                       @Field("status") status: Int,
                       @Field("type") type: Int): Call<Status>

        @POST(Api.DELETE_TODO + "{id}/json")
        fun deleteTodo(@Path("id") id: Int): Call<Status>

        @FormUrlEncoded
        @POST(Api.UPDATE_DONE_TODO + "{id}/json")
        fun updateDoneTodo(@Path("id") id: Int,
                           @Field("status") status: Int): Call<Status>

    }



}