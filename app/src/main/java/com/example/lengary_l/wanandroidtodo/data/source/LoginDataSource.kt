package com.example.lengary_l.wanandroidtodo.data.source

import com.example.lengary_l.wanandroidtodo.data.LoginData
import com.example.lengary_l.wanandroidtodo.data.LoginDetailData
import com.example.lengary_l.wanandroidtodo.data.PostType

/**
 * Created by CoderLengary
 */
interface LoginDataSource {

    suspend fun getRemoteLoginData(userName: String, password: String, type: PostType): Result<LoginData>

    suspend fun saveLoginData(data: LoginDetailData)

    suspend fun getLocalLoginData(): Result<LoginData>

}