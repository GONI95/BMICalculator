package sang.gondroid.calingredientfood.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import sang.gondroid.calingredientfood.data.dto.entity.MealNtrIrdntEntity

/**
 * [22.03.29] : Data Access Object(데이터에 접근할 수 있는 메서드를 정의해놓은 Interface)
 */
@Dao
interface MealNtrIrdntDAO {
    @Query("SELECT * FROM mealNtrIrdntTable")
    fun getMealNtrIrdntList(): Flow<List<MealNtrIrdntEntity>>

    @Insert
    suspend fun insert(mealNtrIrdntEntity: MealNtrIrdntEntity)

    @Query("SELECT * FROM mealNtrIrdntTable WHERE created_date BETWEEN :startDate and :currentDate ORDER BY created_date")
    fun getLastSevenDaysMealNtrIrdntList(
        startDate: String,
        currentDate: String
    ): Flow<List<MealNtrIrdntEntity>>
}
