package sang.gondroid.calingredientfood.presentation.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import sang.gondroid.calingredientfood.domain.model.Model

object Extensions {

    /**
     * Gon [22.01.20] : 1. 일반적인 Generic Function Body에서 타입 T는 런타임에는 Type erasure 때문에 접근이 불가능
     *                  하지만 reified 타입 파라미터와 함께 inline 함수를 만들면, 런타임에 타입 T에 접근이 가능하며,
     *                  all { }[반환값 Boolean]을 통해 모든 원소가 람다식인 "변수 is T"를 통해 "변수"가 T의 인스턴스인지 체크
     *
     *                  2. Type erasure : Generic은 컴파일 시간에 엄격한 Type 검사를 제공하지만,
     *                  Generic을 구현하기 위해 Java 컴파일러는 Type erasure를 적용합니다
     */
    inline fun <reified T> List<Model>.checkType() = all { it is T }

    @SuppressLint("Recycle")
    fun Uri.getRealPathUri(context: Context): Uri {
        var uri = this

        path?.let { path ->
            if (path.startsWith("/document")) {
                val id = DocumentsContract.getDocumentId(uri).split(":")[1]
                val columns = MediaStore.Files.FileColumns.DATA
                val selection = MediaStore.Files.FileColumns._ID + " = " + id

                val cursor = context.contentResolver.query(
                    MediaStore.Files.getContentUri("external"),
                    arrayOf(columns),
                    selection,
                    null,
                    null
                )

                if (cursor != null && cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(columns)
                    val realPath = cursor.getString(columnIndex)
                    uri = Uri.parse(realPath)

                    cursor.close()
                }
            }
        }

        return uri
    }
}
