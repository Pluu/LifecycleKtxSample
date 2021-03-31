package com.pluu.sample.coroutinektx.ktx

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import com.pluu.sample.coroutinektx.SampleViewModel
import com.pluu.sample.coroutinektx.databinding.ActivityRepeatOnLifecycleBinding
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class FlowWithLifecycleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepeatOnLifecycleBinding
    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepeatOnLifecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.flowToLiveDataCounter.observe(this) {
            binding.tvLiveData.text = it.toString()
            Timber.tag("Activity").d("LiveData $it")
        }

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.flowCounter
                .collect {
                    binding.tvFlow.text = it.toString()
                    Timber.tag("Activity").d("Flow $it")
                }
        }

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.channelFlowCounter
                .collect {
                    binding.tvChannel.text = it.toString()
                    Timber.tag("Activity").d("ChannelFlow $it")
                }
        }
    }
}