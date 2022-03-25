package sang.gondroid.calingredientfood.data.db
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import sang.gondroid.calingredientfood.data.dto.entity.FoodNtrIrdntEntity

/**
 * [22.03.25] : Data Access Object(데이터에 접근할 수 있는 메서드를 정의해놓은 Interface)
 */
@Dao
interface FoodNtrIrdntDao {
    @Query("SELECT * FROM foodNtrIrdntTable")
    fun getAllFoodNtrIrdnt(): List<FoodNtrIrdntEntity>

    @Insert
    suspend fun insertFoodNtrIrdnt(foodNtrIrdntEntity: FoodNtrIrdntEntity)
}