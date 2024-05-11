package com.noureldin.holyquran.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.noureldin.holyquran.adapters.OnItemClickListener
import com.noureldin.holyquran.adapters.RadioAdapter
import com.noureldin.holyquran.api.ApiManager
import com.noureldin.holyquran.databinding.FragmentRadioBinding
import com.noureldin.holyquran.model.Radio
import com.noureldin.holyquran.model.Response
import com.noureldin.holyquran.service.MediaPlayerStateListener
import com.noureldin.holyquran.service.PlayRadioService
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback


@AndroidEntryPoint
class RadioFragment : Fragment() {
    lateinit var binding: FragmentRadioBinding
    val adapter = RadioAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadioBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.OnPlayPauseClickListener = OnItemClickListener { position, radio ->
            radioService?.let {radioService->
                adapter.changeRadioChannelStatus(position,!radioService.getIsplaying())

                if (!radioService.getIsplaying()) {
                    startServiceMediaPlayer(radio)
                } else {
                    pauseServiceMediaPlayer()
                }
            }
        }

        initRecyclerView()
        getRadioChannelsFromApi()



    }
    private  val TAG = "RadioFragment"
    var bound = false

    private fun getRadioChannelsFromApi() {
        ApiManager.radioWebService.getRadio().enqueue(object:Callback<Response>{
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful){
                    val radioChannelList = response.body()?.radios ?: listOf()
                    adapter.setRadiosList(radioChannelList.toMutableList())
                }
                Log.e(TAG, "onResponse: response=${response.body()?.radios.toString()}" )
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Toast.makeText(requireActivity(),t.message,Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun initRecyclerView() {
        binding.radioRv.adapter = adapter

    }

    private fun pauseServiceMediaPlayer() {
        if(bound){
            radioService?.playPauseMediaPlayer()
        }
    }

    var radioService: PlayRadioService?=null
    private fun startServiceMediaPlayer(radio: Radio) {
        if (bound && radio.name != null && radio.url != null && radio.id != null) {
            radioService?.startMediaPlayer(radio.url, radio.name, radio.id)
        }
    }
    override fun onStart() {
        super.onStart()
        startService()
        bindService()

    }

    override fun onStop() {
        super.onStop()
        requireActivity().unbindService(connection)
    }
    private fun bindService() {
        val intent=Intent(requireActivity(),PlayRadioService::class.java)
        requireActivity().bindService(intent,connection,Context.BIND_AUTO_CREATE)
    }

    private val connection = object :ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as PlayRadioService.MyBinder
            Log.e("@@@", "from onServiceConnected" )

            radioService = binder.getService()
            bound = true

            radioService?.mediaPlayerStateListener = MediaPlayerStateListener { id, isPlayed ->
                adapter.changeRadioChannelStatus(isPlayed,id)
            }


        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
            radioService = null
        }

    }


    private fun startService() {
        val intent=Intent(requireActivity(),PlayRadioService::class.java)
        activity?.startService(intent)
    }


}