package sang.gondroid.calingredientfood.presentation.widget.custom.dialog

import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.databinding.DialogFragmentMealNtrIrdntBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import sang.gondroid.calingredientfood.presentation.widget.adapter.BaseRecyclerViewAdapter
import sang.gondroid.calingredientfood.presentation.widget.listener.AdapterListener

class MealNtrIrdntDialogFragment(private val model: MealNtrIrdntModel) :
    BaseDialogFragment<DialogFragmentMealNtrIrdntBinding>() {

    private val foodNtrIrdntDetailAdapter by lazy {
        BaseRecyclerViewAdapter<FoodNtrIrdntModel>(
            modelList = model.foodNtrIrdntList,
            object : AdapterListener {
                override fun onClickItem(model: Model) {
                    DebugLog.d("$model")
                }
            }
        )
    }

    override fun getDataBinding(): DialogFragmentMealNtrIrdntBinding =
        DialogFragmentMealNtrIrdntBinding.inflate(layoutInflater)

    override fun onResume() {
        super.onResume()
        requireContext().dialogFragmentResize(0.9f)
    }

    override fun initViews() {
        with(binding) {
            item = model
            foodNtrIrdntDetailViewPager.apply {
                adapter = foodNtrIrdntDetailAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        currentPosition = position + 1
                    }
                })
            }

            val autoSlideJob = lifecycleScope.launch {
                autoSlide(model.foodNtrIrdntList.isNotEmpty())
            }

            stopSlideButton.setOnClickListener {
                autoSlideJob.cancel()
            }

            dismissButton.setOnClickListener {
                dismiss()
            }
        }
    }

    private suspend fun autoSlide(boolean: Boolean) {
        with(binding.foodNtrIrdntDetailViewPager) {
            while (model.foodNtrIrdntList.isNotEmpty() && boolean) {
                delay(2000)

                if (currentItem == model.foodNtrIrdntList.size - 1)
                    setCurrentItem(0, false)
                else
                    setCurrentItem(currentItem + 1, true)
            }
        }
    }
}
