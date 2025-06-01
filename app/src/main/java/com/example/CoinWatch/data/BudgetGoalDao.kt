package com.example.CoinWatch.data

import androidx.room.*

@Dao
interface BudgetGoalDao {

    @Query("SELECT * FROM budget_goals LIMIT 1")
    suspend fun getBudgetGoal(): BudgetGoal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: BudgetGoal)

    @Query("SELECT * FROM budget_goals WHERE user_id = :userId AND month = :month LIMIT 1")
    suspend fun getBudgetForUserAndMonth(userId: Int, month: String): BudgetGoal?

    @Query("SELECT * FROM budget_goals WHERE user_id = :userId")
    suspend fun getGoalsByUser(userId: Int): List<BudgetGoal>

    @Query("DELETE FROM budget_goals WHERE month = :month")
    suspend fun deleteGoalsByMonth(month: String)

    @Query("DELETE FROM budget_goals WHERE user_id = :userId")
    suspend fun deleteGoalsByUser(userId: Int)

    @Update
    suspend fun updateGoal(goal: BudgetGoal)

    @Delete
    suspend fun deleteGoal(goal: BudgetGoal)
}
