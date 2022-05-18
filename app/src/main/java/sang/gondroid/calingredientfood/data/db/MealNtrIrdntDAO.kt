package sang.gondroid.calingredientfood.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import sang.gondroid.calingredientfood.data.dto.entity.MealNtrIrdntEntity
import sang.gondroid.calingredientfood.presentation.util.MealNtrIrdntSort

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

    @Query(
        "SELECT * FROM mealNtrIrdntTable WHERE created_date BETWEEN :firstDay AND :lastDay ORDER BY " +
            "CASE WHEN :mealNtrIrdntSort = 'INITIALIZE' THEN created_date END DESC, " +
            "CASE WHEN :mealNtrIrdntSort = 'CALORIE' THEN total_calorie END DESC, " +
            "CASE WHEN :mealNtrIrdntSort = 'CARBOHYDRATE' THEN total_carbohydrate END DESC, " +
            "CASE WHEN :mealNtrIrdntSort = 'PROTEIN' THEN total_protein END DESC, " +
            "CASE WHEN :mealNtrIrdntSort = 'FAT' THEN total_fat END DESC " +
            "LIMIT :loadSize OFFSET (:page-1) * :loadSize"
    )
    suspend fun getMealNtrIrdntListForMonth(
        firstDay: String,
        lastDay: String,
        mealNtrIrdntSort: MealNtrIrdntSort,
        page: Int,
        loadSize: Int
    ): List<MealNtrIrdntEntity>

    @Query("DELETE FROM mealNtrIrdntTable WHERE id IN (:checkedMealNtrIrdntIdSet)")
    suspend fun deleteCheckedMealNtrIrdnt(checkedMealNtrIrdntIdSet: Set<Long>)

    @Query("DELETE FROM mealNtrIrdntTable")
    suspend fun deleteAllMealNtrIrdnt()
}
