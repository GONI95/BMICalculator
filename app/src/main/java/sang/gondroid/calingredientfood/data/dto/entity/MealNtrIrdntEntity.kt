package sang.gondroid.calingredientfood.data.dto.entity

import android.net.Uri
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import sang.gondroid.calingredientfood.data.db.RoomTypeConverters

@androidx.room.Entity(tableName = "mealNtrIrdntTable")
data class MealNtrIrdntEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override val id: Long?,
    @TypeConverters(RoomTypeConverters::class)
    @ColumnInfo(name = "meal_image")
    val mealImage: Uri?,
    @ColumnInfo(name = "created_date")
    val createdDate: String,
    @TypeConverters(RoomTypeConverters::class)
    @SerializedName("food_ntr_irdnt_list")
    val foodNtrIrdntList: List<FoodNtrIrdntEntity>,
    @ColumnInfo(name = "total_calorie")
    val totalCalorie: Double,
    @ColumnInfo(name = "total_carbohydrate")
    val totalCarbohydrate: Double,
    @ColumnInfo(name = "total_protein")
    val totalProtein: Double,
    @ColumnInfo(name = "total_fat")
    val totalFat: Double,
    @ColumnInfo(name = "total_sugar")
    val totalSugar: Double,
    @ColumnInfo(name = "total_salt")
    val totalSalt: Double,
    @ColumnInfo(name = "total_cholesterol")
    val totalCholesterol: Double,
    @ColumnInfo(name = "total_saturated_fatty_acid")
    val totalSaturatedFattyAcid: Double,
    @ColumnInfo(name = "total_trans_fat")
    val totalTransFat: Double
) : Entity
