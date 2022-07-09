package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdateStatusBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_status_bottom_sheet, container, false)
    }

    companion object {
        const val TAG = "ModalBottomSheetChangeStatus"
        const val ID_ORDER = "IdOrder"
    }
}