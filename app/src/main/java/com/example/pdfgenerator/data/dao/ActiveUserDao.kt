package com.example.pdfgenerator.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pdfgenerator.data.model.CurrentUserData

@Dao
interface ActiveUserDao {
    @Query("SELECT * FROM CurrentUserData")
    fun getAll(): List<CurrentUserData>

    @Query("SELECT * FROM CurrentUserData WHERE userName LIKE :userName")
    fun findByName(userName: String): CurrentUserData

    @Insert
    fun insertAll(vararg currentUserData: CurrentUserData)

    @Delete
    fun delete(currentUserData: CurrentUserData)

    @Query("DELETE FROM CurrentUserData")
    fun deleteAll()
}