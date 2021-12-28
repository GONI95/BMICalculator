package sang.gondroid.calingredientfood.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.util.DebugLog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DebugLog.v("called")
    }
}