package sang.gondroid.calingredientfood.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import sang.gondroid.calingredientfood.data.db.MealNtrIrdntDAO
import sang.gondroid.calingredientfood.data.dto.entity.MealNtrIrdntEntity
import sang.gondroid.calingredientfood.presentation.util.DebugLog

/**
 * Gon [22.04.26] : PagingData를 load하는 추상 클래스인 PagingSource를 상속받아 정의한 클래스
 *                  Paging Key Type : 현재 로드한 데이터의 페이지 정보에 대한 데이터 타입 (Int)
 *                  Loaded Data Type : 응답 Model Type (MealNtrIrdntEntity)
 *
 *                  mealNtrIrdntDAO : Room DB로 부터 데이터를 가져오는 DAO 객체
 *                  firstDay : 요청 시 필요한 시작일
 *                  lastDay : 요청 시 필요한 마지막일
 */
class MealNtrIrdntPagingSource(
    private val mealNtrIrdntDAO: MealNtrIrdntDAO,
    private val firstDay: String,
    private val lastDay: String
) : PagingSource<Int, MealNtrIrdntEntity>() {

    private companion object {
        const val INIT_PAGE_INDEX = 1
    }

    /**
     * Gon [22.04.26] : SwipeRefresh, Data Update 등 현재 목록을 대체할 새 데이터를 로드할 경우 호출됨
     *                  anchorPosition : 가장 최근에 접근한 Index를 반환
     */
    override fun getRefreshKey(state: PagingState<Int, MealNtrIrdntEntity>): Int? {
        DebugLog.i("getRefreshKey called")

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
     * Gon [22.04.26] : 스크롤 시 데이터를 비동기적으로 가져오는 Load 함수 재정의
     *                  LoadParams : Load 작업과 관련된 정보를 갖고있는 객체
     *                  params.key : 현재 페이지 index를 반환 / Load 작업이 처음일 경우 null을 반환 (Int)
     *                  params.loadSize : Load 시 가져올 데이터의 갯수를 밴환 (Int)
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealNtrIrdntEntity> {
        DebugLog.i("load called")

        val page = params.key ?: INIT_PAGE_INDEX
        val items = mealNtrIrdntDAO.getMealNtrIrdntListForMonth(
            firstDay,
            lastDay,
            page,
            params.loadSize
        )

        return LoadResult.Page(
            data = items,
            prevKey = if (page == INIT_PAGE_INDEX) null else page - 1,
            nextKey = if (items.isEmpty()) null else page + (params.loadSize / 10)
        ).also {
            DebugLog.d("page : $page, loadSize : ${params.loadSize}")
        }
    }
}
