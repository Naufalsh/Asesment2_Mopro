package com.naufalmaulanaartocarpussavero607062300078.asesment2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.Sales
import com.naufalmaulanaartocarpussavero607062300078.asesment2.model.SalesWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(sale: Sales)

    @Update
    suspend fun updateSale(sale: Sales)

    @Delete
    suspend fun deleteSale(sale: Sales)

    @Transaction
    @Query("SELECT * FROM sales ORDER BY date DESC")
    fun getAllSalesWithProduct(): Flow<List<SalesWithProduct>>
}