package sang.gondroid.calingredientfood.presentation.widget.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.NumberCounterHorizontalBinding
import sang.gondroid.calingredientfood.presentation.util.DebugLog

/**
 * Gon [22.02.22] : ConstraintLayout 상속받아 구현한 CustomView 정의 [ up, down Button ]
 *                  View를 상속받는 class는 constructor(생성자)를 갖지 않으면 컴파일 에러가 발생
 *                  @JvmOverloads 어노테이션을 활용하면, 여러 생성자를 만들지 않아도 간결하게 작성 가능
 *                  하지만, 파라미터 값이 누락될 경우 default 적용되기 때문에 예상과 다른 동작이 이뤄질 수 있음
 *
 *                  AttributeSet을 넘겨 View의 속성을 설정하기 때문에 문제는 없을 것으로 판단됨
 */
class HorizontalNumberCounter @JvmOverloads internal constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    // Gon [22.02.22] : NumberCounterHorizontalBinding(외부)에서 접근 가능한 프로퍼티 선언
    val countInfoTextView: TextView
        get() = binding.countInfoTextView

    val countDownButton: ImageButton
        get() = binding.countDownButton

    val countUpButton: ImageButton
        get() = binding.countUpButton

    // Gon [22.02.22] : NumberCounterHorizontalBinding(BindingClass) 타입의 프로퍼티를 선언
    private val binding: NumberCounterHorizontalBinding

    // Gon [22.02.22] : NumberCounterHorizontalBinding(BindingClass) 객체를 생성하여 반환하는 메서드 정의
    private fun getDataBinding(context: Context): NumberCounterHorizontalBinding =
        NumberCounterHorizontalBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding = getDataBinding(context)

        attrs?.let {
            binding.settingMap = setTypedArray(it)
        }
    }

    /**
     * Gon [22.02.22] : 매개변수인 xml 문서의 태그와 연관된 속성의 모음인 AttributeSet을 attrs.xml/NumberCounter의 속성 값을 가진, Map을 반환
     *
     *                  obtainStyledAttributes() : 리소스 식별자(NumberCounter)를 통해, attrs의 속성 값을 보유하는 TypeArray를 반환
     *
     *                  recycle() : 메서드를 호출한 시점부터 이 개체를 재사용 가능하도록 설정
     *                              TypeArray는 내부적으로 몇 개의 배열이 포함되어 있어 사용할 때 매번 메모리를 할당하지 않기 위해
     *                              Cache를 위한 배열을 통해 Static field로 Cache함, recycle()을 호출해 Cache로 사용한 객체를
     *                              반환하여 Garbage Collection의 대상에서 제외시킴
     */
    @SuppressLint("CustomViewStyleable")
    private fun setTypedArray(attrs: AttributeSet) : Map<String, Any> {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberCounter)

        try {
            //text 설정이 layout에서 이루어지면 enable 작업이 안됨
            binding.countInfoTextView.text = typedArray.getText(R.styleable.NumberCounter_default_text)

            return mapOf(
                "text" to typedArray.getText(R.styleable.NumberCounter_default_text),
                "textColor" to typedArray.getColor(R.styleable.NumberCounter_nc_textColor, Color.BLACK),
                "textSize" to typedArray.getDimension(R.styleable.NumberCounter_nc_textSize, 40F),
                "minValue" to typedArray.getInteger(R.styleable.NumberCounter_nc_minValue, 0),
                "maxValue" to typedArray.getInteger(R.styleable.NumberCounter_nc_maxValue, 10000)
            )
        } finally {
            typedArray.recycle()
        }
    }
}