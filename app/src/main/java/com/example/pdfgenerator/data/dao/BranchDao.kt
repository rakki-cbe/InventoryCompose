package com.example.pdfgenerator.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pdfgenerator.data.model.Profile

@Dao
interface BranchDao {
    @Query("SELECT * FROM Profile")
    fun getAll(): List<Profile>

    @Query("SELECT * FROM Profile WHERE profileId IN (:custId)")
    fun loadAllByIds(custId: IntArray): List<Profile>

    @Query("SELECT * FROM Profile WHERE company_name LIKE :name")
    fun findByName(name: String): Profile

    @Insert
    fun insertAll(vararg branches: Profile)

    @Delete
    fun delete(branches: Profile)
}