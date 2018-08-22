package com.example.lengary_l.wanandroidtodo.data.source.remote

import com.example.lengary_l.wanandroidtodo.data.LoginData
import com.example.lengary_l.wanandroidtodo.data.source.LoginDataSource
import com.example.lengary_l.wanandroidtodo.data.source.RemoteException
import com.example.lengary_l.wanandroidtodo.data.source.Result
import com.example.lengary_l.wanandroidtodo.retrofit.RetrofitClient
import com.example.lengary_l.wanandroidtodo.retrofit.RetrofitService
import com.example.lengary_l.wanandroidtodo.util.AppExecutors
import kotlinx.coroutines.experimental.withContext

/**
 * Created by CoderLengary
 */
class LoginDataRemoteSource private constructor(
        private val mAppExecutors: AppExecutors): LoginDataSource{

    companion object {
        private var INSTANCE: LoginDataRemoteSource ?= null

        fun getInstance(mAppExecutors: AppExecutors): LoginDataRemoteSource{
            if(INSTANCE == null){
                synchronized(LoginDataRemoteSource::javaClass){
                    INSTANCE = LoginDataRemoteSource(mAppExecutors)
                }
            }
            return INSTANCE!!
        }


    }

    private val mLoginService: RetrofitService.LoginService by lazy {
        val retrofit = RetrofitClient.getInstance()

        retrofit.create(RetrofitService.LoginService::class.java)
    }

    override suspend fun getRemoteLoginData(userName: String, password: String): Result<LoginData> = withContext(mAppExecutors.ioContext) {
        try {
            val response = mLoginService.login(userName, password).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                }?: run {
                    Result.Error(RemoteException())
                }
            } else{
                Result.Error(RemoteException())
            }

        } catch (e: Exception){
            Result.Error(RemoteException())
        }
    }

    override suspend fun getLocalLoginData(): Result<LoginData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}