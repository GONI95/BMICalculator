package sang.gondroid.calingredientfood.data.repository

import android.annotation.SuppressLint
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import sang.gondroid.calingredientfood.data.data_source.MealNtrIrdntPagingSource
import sang.gondroid.calingredientfood.data.db.MealNtrIrdntDAO
import sang.gondroid.calingredientfood.domain.mapper.MealNtrIrdntMapper
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import java.text.SimpleDateFormat
import java.util.Calendar

class MealNtrIrdntRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val mealNtrIrdntDAO: MealNtrIrdntDAO,
    private val mealNtrIrdntMapper: MealNtrIrdntMapper
) : MealNtrIrdntRepository {
    override fun getMealNtrIrdntList(): Flow<List<MealNtrIrdntModel>> = flow {
        mealNtrIrdntDAO.getMealNtrIrdntList()
            .map { mealNtrIrdntEntityList ->

                mealNtrIrdntEntityList.map {
                    mealNtrIrdntMapper.toModel(it, ViewType.MEAL_NTR_IRDNT)
                }
            }
            .collect {
                emit(it)
            }
    }

    override suspend fun insertMealNtrIrdnt(mealNtrIrdntModel: MealNtrIrdntModel) =
        withContext(ioDispatcher) {
            val mealNtrIrdntEntity = mealNtrIrdntMapper.toEntity(mealNtrIrdntModel)
                .also {
                    DebugLog.i("$it")
                }

            mealNtrIrdntDAO.insert(mealNtrIrdntEntity)
        }

    @SuppressLint("SimpleDateFormat")
    override fun getLastSevenDaysMealNtrIrdntList(): Flow<List<MealNtrIrdntModel>> = flow {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val today = CalendarDay.today()
        today.calendar.add(Calendar.DATE, +1)
        val lastDay = dateFormat.format(today.calendar.time)
        today.calendar.add(Calendar.DATE, -7)
        val startDay = dateFormat.format(today.calendar.time)

        DebugLog.d(lastDay)
        DebugLog.d(startDay)

        mealNtrIrdntDAO.getLastSevenDaysMealNtrIrdntList(startDay, lastDay)
            .map { mealNtrIrdntEntityList ->

                mealNtrIrdntEntityList.map {
                    mealNtrIrdntMapper.toModel(it, ViewType.MEAL_NTR_IRDNT)
                }
            }
            .collect { list ->
                emit(list)
            }
    }

    /**
     * Gon [22.04.26] : 매개변수로 주어진 firstDay, lastDay을 통해 해당 월에 대한 식단 정보 가져오기
     *
     *                  Pager : PagingSource 객체 및 PadingConfig 구성 객체를 바탕으로 PagingData
     *                          인스턴스를 구성하는 반응형 스트림을 생성
     *                  PagingConfig - pageSize(필수) : 각 페이지에 Load할 데이터의 수를 의미
     *                  PagingSourceFactory : PagingSource 인스턴스 생성
     */
    override fun getMealNtrIrdntListForMonth(
        firstDay: String,
        lastDay: String
    ): Flow<PagingData<MealNtrIrdntModel>> =
        Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MealNtrIrdntPagingSource(mealNtrIrdntDAO, firstDay, lastDay)
            }
        ).flow.map { pagingData ->
            pagingData.map { item ->
                mealNtrIrdntMapper.toModel(item, ViewType.MEAL_NTR_IRDNT)
            }
        }
}
