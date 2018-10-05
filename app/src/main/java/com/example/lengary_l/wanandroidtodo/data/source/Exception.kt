package com.example.lengary_l.wanandroidtodo.data.source

/**
 * Created by CoderLengary
 */

open class DataSourceException(message: String): Exception(message)
class RemoteException constructor(message: String = "Something wrong, check your network") : DataSourceException(message)
