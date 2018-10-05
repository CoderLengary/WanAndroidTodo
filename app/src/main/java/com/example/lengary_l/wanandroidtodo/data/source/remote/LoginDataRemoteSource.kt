package com.example.lengary_l.wanandroidtodo.data.source.remote

import com.example.lengary_l.wanandroidtodo.data.LoginData
import com.example.lengary_l.wanandroidtodo.data.LoginDetailData
import com.example.lengary_l.wanandroidtodo.data.PostType
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
                    if(INSTANCE == null){
                        INSTANCE = LoginDataRemoteSource(mAppExecutors)
                    }
                }
            }
            return INSTANCE!!
        }


    }

    private val mLoginService: RetrofitService.LoginService by lazy {
        val retrofit = RetrofitClient.getInstance()

        retrofit.create(RetrofitService.LoginService::class.java)
    }

    override suspend fun getRemoteLoginData(userName: String, password: String, type: PostType): Result<LoginData> = withContext(mAppExecutors.ioContext) {
        try {
            val response = when(type) {
                PostType.TYPE_LOGIN -> mLoginService.login(userName, password).execute()
                PostType.TYPE_REGISTER -> mLoginService.register(userName, password, password).execute()
            }


            if (response.isSuccessful) {
                response.body()?.let {

                    if (it.errorCode == 0) {
                        Result.Success(it)
                    }else {
                        Result.Error(RemoteException(it.errorMsg))
                    }
                } ?: run {
                    Result.Error(RemoteException())
                }
            } else {
                Result.Error(RemoteException())
            }

        } catch (e: Exception) {
            Result.Error(RemoteException())
        }


    }


    override suspend fun getLocalLoginDataById(id: Int): Result<LoginDetailData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveLoginData(data: LoginDetailData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}