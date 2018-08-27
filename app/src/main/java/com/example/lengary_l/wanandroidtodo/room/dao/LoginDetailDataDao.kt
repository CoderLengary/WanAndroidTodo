package com.example.lengary_l.wanandroidtodo.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.example.lengary_l.wanandroidtodo.data.LoginDetailData

/**
 * Created by CoderLengary
 */
@Dao
interface LoginDetailDataDao {

    @Insert
    fun insertLoginDetailData(data: LoginDetailData)

}