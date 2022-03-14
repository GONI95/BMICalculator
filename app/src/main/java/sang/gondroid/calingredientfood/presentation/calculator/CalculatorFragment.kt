package sang.gondroid.calingredientfood.presentation.calculator

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.lifecycle.Lifecycle
import org.koin.android.viewmodel.ext.android.viewModel
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.FragmentCalculatorBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.base.BaseFragment
import sang.gondroid.calingredientfood.presentation.util.Constants
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import sang.gondroid.calingredientfood.presentation.widget.adapter.BaseRecyclerViewAdapter
import sang.gondroid.calingredientfood.presentation.widget.custom.FoodNtrIrdntBottomSheet
import sang.gondroid.calingredientfood.presentation.widget.custom.NotificationSnackBar
import sang.gondroid.calingredientfood.presentation.widget.listener.CalculatorListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import sang.gondroid.calingredientfood.presentation.MainActivity


internal class CalculatorFragment : BaseFragment<FragmentCalculatorBinding, CalculatorViewModel>() {

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        private val permissionStateList by lazy {
            Array(REQUIRED_PERMISSION.size) { false }
        }

        private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
        private var imageUri: Uri? = null
        private var currentPhotoPath : String? = null
    }

    private val calculatorSet = mutableSetOf<FoodNtrIrdntModel>()

    override val viewModel: CalculatorViewModel by viewModel()

    override fun getDataBinding(): FragmentCalculatorBinding = FragmentCalculatorBinding.inflate(layoutInflater)

    /**
     * Gon [22.02.14] : BaseRecyclerViewAdapter 객체, CalculatorViewHolder의 Event가 발생되면 호출되는 CalculatorListener 구현체
     *                  by lazy : 사용되는 시점에서 객체 생성과 동시에 값을 초기화
     */
    private val calculatorAdapter by lazy {
        BaseRecyclerViewAdapter<FoodNtrIrdntModel>(listOf(), object : CalculatorListener {

            override fun onClickItem(model: FoodNtrIrdntModel) {
                FoodNtrIrdntBottomSheet.newInstance(model).show(requireActivity().supportFragmentManager, Constants.BOTTOM_SHEET_TAG)
            }

            override fun onClickRemoveButton(model: FoodNtrIrdntModel) {
                viewModel.removeCalculatorItem(model)
            }

            override fun onClickCountUpdateButton(servingCount: Int, position: Int) {
                viewModel.countUpdateCalculatorItem(servingCount, position)
            }
        })
    }

    // Gon [22.03.14] : Android 6.0(API 23) 마시멜로우 이상 버젼에선 Manifest에 Permission 추가와 위험 권한에 대해 승인을 받도록 구현이 필요
    private val requestPermissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.forEach { actionMap ->
            when (actionMap.key) {
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                    if (actionMap.value) {
                        permissionStateList[0] = true

                    } else {
                        permissionStateList[0] = false
                        NotificationSnackBar.make(requireView(), resources.getString(R.string.please_set_permissions)).show()
                    }
                }
            }
        }
    }

    /**
     * Gon [22.03.14] : Android 9.0(API 28) 파이 이상 버젼에선 ImageDecoder를 사용해 Bitmap 객체로 변환
     *                  미만 버젼에선 MediaStore.Images.Media.getBitmap()을 사용해 Bitmap 객체로 변환
     */
    private val findPictureResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data ?: imageUri

                uri?.let {
                    viewModel.selectPictureUri.value = uri
                    binding.pictureDawable = BitmapDrawable(resources, getImageBitmap(uri))
                }
            }
        }

    private fun getImageBitmap(uri: Uri): Bitmap =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)

        } else {
            //촬영한 사진이 갤러리에 갱신되지 않는 경우, 이를 해결하기 위한 방법
            currentPhotoPath?.let {
                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
                    val f = File(it)
                    mediaScanIntent.data = Uri.fromFile(f)
                    requireActivity().sendBroadcast(mediaScanIntent)
                }
                currentPhotoPath = null
            }

            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        }

    override fun initViews() = with(binding) {
        calculatorViewModel = viewModel
        calculatorAdapter = this@CalculatorFragment.calculatorAdapter
        handler = this@CalculatorFragment

        // Gon [22.03.14] : 외부 저장소는 항상 접근이 보장되어 있지 않음(이동식 SD 카드 제거 등)
        externalStorageState = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * Gon [22.03.14] : 갤러리로 부터 사진 가져오기
     *                  ACTION_GET_CONTENT : 특정 종류의 데이터를 선택하고 반환
     *                  type : image/'*' (갤러리)
     *                         video/'*' (동영상)
     *                         application/'*' (애플리케이션)
     */
    fun findPicture() {
        val intent = Intent(ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        findPictureResultLauncher.launch(intent)
    }

    /**
     * Gon [22.03.14] : 사진 촬영 후 공용 저장소 Pictures/TakePicture에 저장
     *                  ContentValues : ContentResolve 객체가 처리할 수 있는 값 집합을 저장하는데 사용
     *                                  DISPLAY_NAME : 미디어 항목의 표시할 이름
     *                                  MIME_TYPE : 미디어 항목의 MIME 유형
     *                                  RELATIVE_PATH : 지속되는 저장 장치 내에서 항목의 상대 경로
     *                  contentResolver.insert : URL의 테이블에 행 삽입
    */
    fun takePicture() {
        requestPermissionResultLauncher.launch(REQUIRED_PERMISSION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, createFileName())
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/TakePicture")

            imageUri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        } else {
            val imagePath = File(Environment.getExternalStorageDirectory(), "TakePicture").apply{
                if(!this.exists()) this.mkdirs()
            }

            val imageFile = File(imagePath, createFileName()).also {
                currentPhotoPath = it.absolutePath
            }

            /**
             *  Gon[22.03.14] : 다른 앱과 file을 공유하기 위해 content URI를 생성
             */
            imageUri = FileProvider.getUriForFile(
                requireContext(),
                "sang.gondroid.calingredientfood.fileprovider",
                imageFile
            )
        }

        if (!permissionStateList.contains(false)) {
            /**
             * Gon[22.03.08] : file에 대한 content URI를 다른 앱에 제공
             *                 ACTION_IMAGE_CAPTURE : 카메라를 통해 이미지를 캡처하고 반환하도록 하는 Intent 작업
             *                 EXTRA_OUTPUT : 요청된 이미지 또는 비디오를 저장하는데 사용할 ContentResolve Uri
             */
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            findPictureResultLauncher.launch(intent)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createFileName() : String {
        val filename = SimpleDateFormat(FILENAME_FORMAT).format(System.currentTimeMillis())
        return "${filename}.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        calculatorSet.forEach {
            viewModel.addCalculatorItem(it)
        }
    }

    /**
     * Gon [22.03.03] : MainActivity에서 FragmentListener의 sendCalculatorItem()에 의해 호출됨
     *                  Fragment가 초기화 되지않은 상태에서 ViewModel을 호출할 수 없기 때문에 Lifecycle 상태가
     *                  INITIALIZED인 경우 calculatorList 담아둠
     *                  STARTED로 변경가 호출된 경우부턴 viewModel.addCalculatorItem() 호출
     */
    fun receiveCalculatorItem(model: FoodNtrIrdntModel): Boolean =
        when(lifecycle.currentState) {
            Lifecycle.State.INITIALIZED -> {
                if (calculatorSet.contains(model))
                    false
                else {
                    calculatorSet.add(model)
                    true
                }
            }

            else ->
                viewModel.addCalculatorItem(model)
        }

    override fun observeData() { }
}