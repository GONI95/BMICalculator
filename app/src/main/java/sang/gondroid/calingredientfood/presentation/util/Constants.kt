package sang.gondroid.calingredientfood.presentation.util

object Constants {
    /**
     * Gon [22.01.25] : FragmentManager에 해당 상수를 TAG로 BottomSheetDialogFragment를 추가하여 표시하기 위해 사용되는 상수 정의
     */
    const val BOTTOM_SHEET_TAG : String = "FOOD_NTR_IRDNT_BOTTOM_SHEET"

    /**
     * Gon [22.03.16] : InsertFoodNtrIrdntActivity 호출에 대한 Intent에 사용될 Bundle 저장 및 가져오기에 사용되는 상수 정의
     */
    const val FOOD_NTR_IRDNT_MODEL_KEY : String = "INSERT_FOOD_NTR_IRDNT_ACTIVITY_RESULT_KEY"

    /**
     * Gon [22.03.22] : CalIngredientFoodApplication에 해당 상수를 이용해 Preferences DataStore의 인스턴스를 생성 시
     *                  name 매개변수에 사용되는 상수 정의
     */
    const val PREFERENCES_DATASTORE_NAME: String = "CAL_INGREDIENT_FOOD_PREFERENCES_DATASTORE"
}