package com.example.lengary_l.wanandroidtodo.data.source

/**
 * Created by CoderLengary
 */

open class DataSourceException(message: String): Exception(message)
class RemoteException : DataSourceException("Data not found in remote data source")