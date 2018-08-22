package com.example.lengary_l.wanandroidtodo.data.source

import com.example.lengary_l.wanandroidtodo.data.LoginData

/**
 * Created by CoderLengary
 */
interface LoginDataSource {

    suspend fun getRemoteLoginData( userName: String, password: String): Result<LoginData>

    suspend fun getLocalLoginData(): Result<LoginData>

}