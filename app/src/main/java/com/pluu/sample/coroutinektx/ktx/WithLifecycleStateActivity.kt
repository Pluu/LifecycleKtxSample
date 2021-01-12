package com.pluu.sample.coroutinektx.ktx

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import com.pluu.sample.coroutinektx.SampleViewModel
import com.pluu.sample.coroutinektx.databinding.ActivityWithLifecycleStateBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class WithLifecycleStateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWithLifecycleStateBinding
    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithLifecycleStateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.flowToLiveDataCounter.observe(this) {
            binding.tvLiveData.text = it.toString()
            Timber.tag("Activity").d("LiveData $it")
        }

        // 1. LifecycleOwner.withStarted
        lifecycleScope.launch {
            withStarted {
                lifecycleScope.launch {
                    viewModel.flowCounter
                        .collect {
                            binding.tvFlow.text = it.toString()
                            Timber.tag("Activity").d("Flow $it")
                        }
                }
            }
        }

        lifecycleScope.launch {
            withStarted {
                lifecycleScope.launch {
                    viewModel.channelFlowCounter
                        .collect {
                            binding.tvChannel.text = it.toString()
                            Timber.tag("Activity").d("ChannelFlow $it")
                        }
                }
            }
        }
    }
}