package com.example.lengary_l.wanandroidtodo.data.source

import com.example.lengary_l.wanandroidtodo.data.LoginData
import com.example.lengary_l.wanandroidtodo.data.LoginDetailData
import com.example.lengary_l.wanandroidtodo.data.PostType

/**
 * Created by CoderLengary
 */
class LoginDataRepository private constructor(
        private val mRemote: LoginDataSource,
        private val mLocal: LoginDataSource
): LoginDataSource{



    companion object {
        private var INSTANCE: LoginDataRepository ?= null
        fun getInstance(mRemote: LoginDataSource, mLocal: LoginDataSource): LoginDataRepository {
            if(INSTANCE==null){
                synchronized(LoginDataRepository::javaClass){
                    if(INSTANCE == null){
                        INSTANCE = LoginDataRepository(mRemote, mLocal)
                    }
                }
            }
            return INSTANCE!!
        }
    }
    override suspend fun getRemoteLoginData(userName: String, password: String, type: PostType): Result<LoginData> {
        val remoteResult = mRemote.getRemoteLoginData(userName, password, type)


        if(remoteResult is Result.Success && remoteResult.data.errorCode != -1) {
            saveLoginData(remoteResult.data.data)
        }

        return remoteResult

    }

    override suspend fun getLocalLoginData(): Result<LoginData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveLoginData(data: LoginDetailData) {
        mLocal.saveLoginData(data)
    }

}