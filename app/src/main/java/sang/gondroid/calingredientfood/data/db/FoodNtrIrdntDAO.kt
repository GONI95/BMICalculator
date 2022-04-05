package sang.gondroid.calingredientfood.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import sang.gondroid.calingredientfood.data.dto.entity.FoodNtrIrdntEntity

/**
 * [22.03.25] : Data Access Object(데이터에 접근할 수 있는 메서드를 정의해놓은 Interface)
 */
@Dao
interface FoodNtrIrdntDAO {
    @Query("SELECT * FROM foodNtrIrdntTable WHERE description_kor LIKE :value")
    suspend fun getSearchList(value: String): List<FoodNtrIrdntEntity>

    @Insert
    suspend fun insert(foodNtrIrdntEntity: FoodNtrIrdntEntity)
}
