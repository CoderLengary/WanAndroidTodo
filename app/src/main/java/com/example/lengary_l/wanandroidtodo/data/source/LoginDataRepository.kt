package com.example.lengary_l.wanandroidtodo.data.source

import com.example.lengary_l.wanandroidtodo.data.LoginData

/**
 * Created by CoderLengary
 */
class LoginDataRepository private constructor(
        private val remote: LoginDataSource,
        private val local: LoginDataSource
): LoginDataSource{

    override suspend fun getRemoteLoginData(userName: String, password: String): Result<LoginData> {
        val remoteResult = remote.getRemoteLoginData(userName, password);
        return if (remoteResult is Result.Success) {
            remoteResult
        } else {
            local.getLocalLoginData().also {
                if (it is Result.Success){
                    it
                }
            }
        }
    }

    override suspend fun getLocalLoginData(): Result<LoginData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun test() {
        val one = "one"
        val two = "two"

            if (true) one.toString() else two.toString()
    }

}