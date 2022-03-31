package sang.gondroid.calingredientfood.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sang.gondroid.calingredientfood.data.dto.entity.FoodNtrIrdntEntity
import sang.gondroid.calingredientfood.data.dto.entity.MealNtrIrdntEntity

/**
 * [22.03.25] : RoomDatabase를 생성하고 관리하는 DB 객체
 *              entities : 현재 DB 관련 Entity를 정의
 *              version : DB 버전 정의 / Schema 변경 시 version도 바뀌어야함
 *              exportSchema : Room에 Schema 구조를 폴더로 Export(내보내다) 할 것인지 설정
 */
@Database(
    entities = [FoodNtrIrdntEntity::class, MealNtrIrdntEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun foodNtrIrdntDAO() : FoodNtrIrdntDAO

    abstract fun mealNtrIrdntDAO() : MealNtrIrdntDAO
}