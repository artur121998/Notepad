import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.speechtotext.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HelpSheetDialogFragment():BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_helpsheet, container, false)
    }

}