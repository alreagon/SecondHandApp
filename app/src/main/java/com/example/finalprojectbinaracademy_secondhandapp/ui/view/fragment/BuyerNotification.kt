package com.example.finalprojectbinaracademy_secondhandapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationItemResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationResponse
import com.example.finalprojectbinaracademy_secondhandapp.databinding.FragmentBuyerNotificationBinding
import com.example.finalprojectbinaracademy_secondhandapp.ui.adapter.NotifAdapter
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

        checkUserLogin()
        refreshNotif()
    }

    private fun checkUserLogin() {
        notificationViewModel.getStatusLogin().observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.pbNotif.visibility = View.VISIBLE
                getNotification()

                binding.notLogin.visibility = View.GONE
            }
        })
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
        binding.pbNotif.visibility = View.GONE
        val adapter = NotifAdapter(object : NotifAdapter.OnClickListener {
            override fun onClickItem(data: NotificationItemResponse) {
                notificationViewModel.readNotification(data.id)
                when(data.status) {
                    "create" -> {
                        if (data.product != null) {
                            val action = BuyerNotificationDirections.actionBuyerNotificationToBuyerDetailProduk(data.productId)
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(requireContext(),"product tidak ada...", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })

        notificationViewModel.getStatusLogin().observe(viewLifecycleOwner, Observer {
            if (!it) {
                adapter.clearData()
            } else {
                adapter.submitData(data)
            }
        })

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