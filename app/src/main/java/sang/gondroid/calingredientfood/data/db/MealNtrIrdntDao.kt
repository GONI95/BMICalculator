package sang.gondroid.calingredientfood.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import sang.gondroid.calingredientfood.data.dto.entity.MealNtrIrdntEntity

@Dao
interface MealNtrIrdntDao {
    @Query("SELECT * FROM mealNtrIrdntTable")
    fun getMealNtrIrdntList(): Flow<List<MealNtrIrdntEntity>>

    @Insert
    suspend fun insert(mealNtrIrdntEntity: MealNtrIrdntEntity)
}