package com.rustamsadykov.firstapp.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.FragmentOnboardingBinding
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import com.rustamsadykov.firstapp.utils.extentions.dpToPx
import dev.chrisbanes.insetter.applyInsetter
import java.util.*
import kotlin.math.abs
import kotlin.math.min

class OnboardingFragment : BaseFragment(R.layout.fragment_onboarding) {

    private val viewBinding by viewBinding(FragmentOnboardingBinding::bind)

    private val viewModel: OnboardingViewModel by viewModels()

    private var player: ExoPlayer? = null

    private var scrollTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = ExoPlayer.Builder(requireContext()).build().apply {
            addMediaItem(MediaItem.fromUri("asset:///onboarding.mp4"))
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.volumeControlButton.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.signUpButton.applyInsetter {
            type(navigationBars = true) { margin() }
        }
        viewBinding.playerView.player = player

        viewBinding.onboardingViewPager.apply {
            setTextPages()
            attachDots(viewBinding.onboardingTextTabLayout)
            offscreenPageLimit = 1
            setPageTransformer { page, position ->
                val dist = abs(position)
                page.scaleX = 1.0f - dist * 0.5f
                page.scaleY = 1.0f - dist * 0.5f
                page.translationX = -dpToPx(200.0f) * position
                page.alpha = 1.0f - dist * 0.3f
            }
        }

        muteVideoHandler()
        startScrollTimer()
        setupListeners()
    }

    private fun scrollTask() = object : TimerTask() {
        override fun run() {
            activity?.runOnUiThread {
                viewBinding.onboardingViewPager.apply {
                    setCurrentItem((currentItem + 1) % (adapter?.itemCount ?: 1), true)
                }
            }
        }
    }

    private fun startScrollTimer() {
        scrollTimer?.cancel()
        scrollTimer = Timer().apply {
            scheduleAtFixedRate(scrollTask(), 4000, 4000)
        }
    }

    private fun stopScrollTimer() {
        scrollTimer?.cancel()
        scrollTimer = null
    }

    private fun setupListeners() {
        viewBinding.signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_signInFragment)
        }
        viewBinding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_signUpFragment)
        }

        viewBinding.volumeControlButton.setOnClickListener(::muteButtonListener)
        viewBinding.onboardingViewPager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            when (state) {
                ViewPager2.SCROLL_STATE_IDLE -> {
                    startScrollTimer()
                }
                ViewPager2.SCROLL_STATE_DRAGGING -> {
                    stopScrollTimer()
                }
                ViewPager2.SCROLL_STATE_SETTLING -> {
                    stopScrollTimer()
                }
            }
        }
    }

    private fun muteButtonListener(view: View) {
        viewBinding.playerView.player?.let {
            viewModel.isVideoMuted = !viewModel.isVideoMuted
            muteVideoHandler()
        }
    }

    private fun muteVideoHandler() {
        if (viewModel.isVideoMuted) {
            viewBinding.playerView.player?.volume = 0F
            viewBinding.volumeControlButton.setImageResource(R.drawable.ic_volume_off_white_24dp)
        } else {
            viewBinding.playerView.player?.volume = 1F
            viewBinding.volumeControlButton.setImageResource(R.drawable.ic_volume_up_white_24dp)
        }
    }

    override fun onResume() {
        super.onResume()
        player?.play()
        startScrollTimer()
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
        stopScrollTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        stopScrollTimer()
    }

    private fun ViewPager2.setTextPages() {
        adapter =
            ListDelegationAdapter(onboardingTextAdapterDelegate()).apply {
                items =
                    listOf(
                        getString(R.string.onboarding_view_pager_text_1),
                        getString(R.string.onboarding_view_pager_text_2),
                        getString(R.string.onboarding_view_pager_text_3)
                    )
            }
    }

    private fun ViewPager2.attachDots(tabLayout: TabLayout) {
        TabLayoutMediator(tabLayout, this) { _, _ -> }.attach()
    }

}