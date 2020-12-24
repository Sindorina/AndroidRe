package cn.signp.banner

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback

class Banner<T, BA : BannerAdapter<T, *>> @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle), BannerLifecycleObserver {
    private var mLoopTask: AutoLoopTask
    private var mViewPager2: ViewPager2
    private lateinit var mAdapter: BA
    private var mLoopTime = 5000L
    private val mPageChangeCallback: BannerOnPageChangeCallback

    init {
        mLoopTask = AutoLoopTask()
        mPageChangeCallback = BannerOnPageChangeCallback()
        ViewPager2(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            //其他功能暂时未添加
        }.also {
            mViewPager2 = it
            addView(it)
        }
        mViewPager2.registerOnPageChangeCallback(mPageChangeCallback)
    }

    override fun onStop(owner: LifecycleOwner) {
        stop()
    }

    override fun onStart(owner: LifecycleOwner) {

    }

    override fun onDestroy(owner: LifecycleOwner) {
        stop()
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (!mViewPager2.isUserInputEnabled) {
            return super.dispatchTouchEvent(ev)
        }
        onTouch.invoke(ev)
        val action = ev.actionMasked
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
            || action == MotionEvent.ACTION_OUTSIDE
        ) {
            start()
        } else if (action == MotionEvent.ACTION_DOWN) {
            bannerClickListener?.click()
            stop()
        }
        return super.dispatchTouchEvent(ev)
    }

    private var bannerClickListener : BannerClickListener? = null
    public fun setOnBannerClick(bannerClickListener : BannerClickListener){
        this.bannerClickListener = bannerClickListener
    }
    private var onTouch = {_:MotionEvent->Unit}

    fun setOnBannerTouchListener(call:(MotionEvent)->Unit){
        this.onTouch = call
    }
    /**
     * 设置适配器
     * @param adapter
     */
    public fun setAdapter(adapter: BA?) {
        if (adapter == null) {
            throw NullPointerException("banner adapter must not null!")
        }
        this.mAdapter = adapter
        mAdapter.registerAdapterDataObserver(mAdapterDataObserver)
        mViewPager2.adapter = adapter
        mViewPager2.setCurrentItem(1, false)
    }

//    fun addBannerLifecycleObserver(owner: LifecycleOwner){
//        owner.lifecycle.addObserver(BannerLifecycleObserverAdapter(owner, this))
//    }

    /**
     *
     */
    public fun setLoopTime(loopTime: Long) {
        this.mLoopTime = loopTime
    }

    public fun start() {
        stop()
        postDelayed(mLoopTask, mLoopTime)
    }

    public fun stop() {
        removeCallbacks(mLoopTask)
    }

    public fun setData(data: List<T>?) {
        if (data == null) return
        mAdapter.setData(data)
        mAdapter.notifyDataSetChanged()
        mViewPager2.setCurrentItem(0, false)
        start()
    }

    private inner class AutoLoopTask : Runnable {
        override fun run() {
            val count = mAdapter.itemCount
            if (count == 0) {
                return
            }
            val currentPosition = mViewPager2.currentItem
            val next = (currentPosition + 1) % count
            mViewPager2.currentItem = next
            mViewPager2.postDelayed(mLoopTask, mLoopTime)
        }
    }

    inner class BannerOnPageChangeCallback : OnPageChangeCallback() {
        private var currentPosition = 0
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            currentPosition = position
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                if (currentPosition == 0) {
                    mViewPager2.setCurrentItem(mAdapter.getRealCount(), false)
                } else if (currentPosition == mAdapter.itemCount - 1) {
                    mViewPager2.setCurrentItem(1, false)
                }
            }
        }
    }

    private val mAdapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            start()
        }
    }
}