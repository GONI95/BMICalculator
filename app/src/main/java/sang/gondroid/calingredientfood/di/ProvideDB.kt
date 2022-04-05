package sang.gondroid.calingredientfood.di

import android.content.Context
import androidx.room.Room
import sang.gondroid.calingredientfood.data.db.ApplicationDatabase
import sang.gondroid.calingredientfood.data.util.API

fun provideDB(context: Context): ApplicationDatabase =
    Room.databaseBuilder(context, ApplicationDatabase::class.java, API.DB_NAME).build()

fun provideFoodNtrIrdntDao(database: ApplicationDatabase) = database.foodNtrIrdntDAO()

fun provideMealNtrIrdntDao(database: ApplicationDatabase) = database.mealNtrIrdntDAO()
