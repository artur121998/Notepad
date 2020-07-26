package com.example.speechtotext.fragment

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.speechtotext.MainActivity
import com.example.speechtotext.R
import com.example.speechtotext.R.layout.fragment_bottomsheet
import com.example.speechtotext.models.DateFilter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottomsheet.*

class BottomSheetDialogFragment( private val mainActivity: MainActivity) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(fragment_bottomsheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            // Bottom Navigation Drawer menu item clicks
            val dateFilter= context?.let { DateFilter(it,mainActivity) }
            when (menuItem.itemId) {
                R.id.nav1 -> dateFilter?.period(-3)
                R.id.nav2 -> dateFilter?.period(-7)
                R.id.nav3 -> dateFilter?.reset()
            }
            true
        }
    }

    private fun Context.toast(message: CharSequence) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 600)
        toast.show()
    }
}