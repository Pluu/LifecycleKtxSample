package com.pluu.sample.coroutinektx.ktx

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pluu.sample.coroutinektx.SampleViewModel
import com.pluu.sample.coroutinektx.databinding.ActivityPausingDispatcherBinding
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class PausingDispatcherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPausingDispatcherBinding
    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPausingDispatcherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.flowToLiveDataCounter.observe(this) {
            binding.tvLiveData.text = it.toString()
            Timber.d("LiveData $it")
        }

        // 1. LifecycleCoroutineScope#launchWhenStarted
        // 2. Lifecycle.whenStarted
        lifecycleScope.launchWhenStarted {
            viewModel.flowCounter
                .collect {
                    binding.tvFlow.text = it.toString()
                    Timber.d("Flow $it")
                }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.channelFlowCounter
                .collect {
                    binding.tvChannel.text = it.toString()
                    Timber.d("ChannelFlow $it")
                }
        }
    }
}