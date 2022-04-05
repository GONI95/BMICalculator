package sang.gondroid.calingredientfood.data.db

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sang.gondroid.calingredientfood.data.dto.entity.FoodNtrIrdntEntity

class RoomTypeConverters {
    @TypeConverter
    fun fromFoodNtrIrdntList(list: List<FoodNtrIrdntEntity>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toFoodNtrIrdntList(value: String): List<FoodNtrIrdntEntity> {
        val listType = object : TypeToken<List<FoodNtrIrdntEntity>>() {}.type
        return Gson().fromJson<List<FoodNtrIrdntEntity>>(value, listType)
    }

    @TypeConverter
    fun toUri(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }
}
