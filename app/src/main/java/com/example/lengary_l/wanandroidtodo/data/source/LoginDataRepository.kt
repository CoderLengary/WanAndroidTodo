package com.example.lengary_l.wanandroidtodo.data.source

import com.example.lengary_l.wanandroidtodo.data.LoginData
import com.example.lengary_l.wanandroidtodo.data.PostType

/**
 * Created by CoderLengary
 */
class LoginDataRepository private constructor(
        private val mRemote: LoginDataSource
): LoginDataSource{



    companion object {
        private var INSTANCE: LoginDataRepository ?= null
        fun getInstance(mRemote: LoginDataSource): LoginDataRepository {
            if(INSTANCE==null) {
                synchronized(LoginDataRepository::javaClass) {
                    if(INSTANCE == null) {
                        INSTANCE = LoginDataRepository(mRemote)
                    }
                }
            }
            return INSTANCE!!
        }
    }
    override suspend fun getRemoteLoginData(userName: String, password: String, type: PostType): Result<LoginData> {
        return mRemote.getRemoteLoginData(userName, password, type)

    }





}