package com.example.lengary_l.wanandroidtodo.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.lengary_l.wanandroidtodo.data.LoginDetailData

/**
 * Created by CoderLengary
 */
@Dao
interface oginDetailDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoginDetailData(data: LoginDetailData)

    @Query("SELECT * FROM login WHERE id = :id")
    fun queryLoginDetailDataById(id: Int): LoginDetailData?

}