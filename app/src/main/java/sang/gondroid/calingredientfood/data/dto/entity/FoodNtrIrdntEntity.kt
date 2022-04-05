package sang.gondroid.calingredientfood.data.dto.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

/**
 * [22.03.25] : Database의 Table 역할을 하는 FoodNtrIrdntEntity
 *              객체를 테이블로 매핑해 객체와 테이블의 관계를 바탕으로 SQL문을 자동으로 생성해 불일치를 해결해
 *              불일치에 대한 SQL문 없이도 객체를 통해 간접적으로 데이터베이스의 데이터를 다룰 수 있습니다.
 */
@androidx.room.Entity(tableName = "foodNtrIrdntTable")
data class FoodNtrIrdntEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override val id: Long?,
    @ColumnInfo(name = "company")
    val company: String,
    @ColumnInfo(name = "begin_year")
    val beginYear: String,
    @ColumnInfo(name = "description_kor")
    val descriptionKOR: String,
    @ColumnInfo(name = "calorie")
    val calorie: Double,
    @ColumnInfo(name = "carbohydrate")
    val carbohydrate: Double,
    @ColumnInfo(name = "protein")
    val protein: Double,
    @ColumnInfo(name = "fat")
    val fat: Double,
    @ColumnInfo(name = "sugar")
    val sugar: Double,
    @ColumnInfo(name = "salt")
    val salt: Double,
    @ColumnInfo(name = "cholesterol")
    val cholesterol: Double,
    @ColumnInfo(name = "saturated_fatty_acid")
    val saturatedFattyAcid: Double,
    @ColumnInfo(name = "trans_fat")
    val transFat: Double,
    @ColumnInfo(name = "serving_weight")
    val servingWeight: Int,
    @ColumnInfo(name = "serving_count")
    val servingCount: Int
) : Entity
