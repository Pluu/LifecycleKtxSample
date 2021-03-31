package com.pluu.sample.coroutinektx.ktx

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pluu.sample.coroutinektx.SampleViewModel
import com.pluu.sample.coroutinektx.databinding.ActivityFlowWithLifecycleBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class RepeatOnLifecycleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowWithLifecycleBinding
    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowWithLifecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.flowToLiveDataCounter.observe(this) {
            binding.tvLiveData.text = it.toString()
            Timber.tag("Activity").d("LiveData $it")
        }

        viewModel.flowCounter
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.tvFlow.text = it.toString()
                Timber.tag("Activity").d("Flow $it")
            }.launchIn(lifecycleScope)

        viewModel.channelFlowCounter
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.tvChannel.text = it.toString()
                Timber.tag("Activity").d("ChannelFlow $it")
            }.launchIn(lifecycleScope)
    }
}