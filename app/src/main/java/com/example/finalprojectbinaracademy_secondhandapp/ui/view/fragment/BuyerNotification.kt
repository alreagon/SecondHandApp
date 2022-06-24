package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.LoginResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentBuyerNotificationBinding
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentEditProfileBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.NotifAdapter
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.EditProfileViewModel
import com.example.finalprojectbinaracademy_secondhandapp.ui.viewmodel.NotificationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BuyerNotification : Fragment() {
    private var _binding: FragmentBuyerNotificationBinding? = null
    private val binding get() = _binding!!
    private val notificationViewModel: NotificationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_buyer_notification, container, false)
        _binding = FragmentBuyerNotificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNotification()

        refreshNotif()
    }

    private fun getNotification() {
        notificationViewModel.getAccessToken().observe(viewLifecycleOwner, Observer {
            notificationViewModel.getNotif(it)
        })

        notificationViewModel.listNotif.observe(viewLifecycleOwner, Observer {
            setupView(it)
        })
    }

    private fun setupView(data: NotificationResponse) {
        val adapter = NotifAdapter()
        adapter.submitData(data)
        val recyclerNotification = binding.recylcerView
        recyclerNotification.layoutManager = LinearLayoutManager(requireContext())
        recyclerNotification.adapter = adapter
    }

    private fun refreshNotif() {
        binding.refreshLayout.setOnRefreshListener {
            getNotification()

            binding.refreshLayout.isRefreshing = false
        }
    }
}