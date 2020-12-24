package cn.signp.banner

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BannerAdapter<T, VH : RecyclerView.ViewHolder>(dataList: List<T>?) : RecyclerView.Adapter<VH>(),
    IViewHolder<T, VH> {
    protected var dataList:List<T> = ArrayList()
    private val increaseCount = 2
    public fun setData(data: List<T>?){
        this.dataList = data ?: ArrayList()
    }
    init {
        setData(dataList)
    }
    public fun getData(position: Int) = dataList[position]

    override fun onBindViewHolder(holder: VH, position: Int) {
        val real = getRealPosition(increaseCount == 2, position, getRealCount())
        onBindView(holder, dataList[real], real, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateHolder(parent, viewType)
    }

    open fun getRealCount(): Int {
        return if (dataList.isNullOrEmpty()){
            0
        }else{
            dataList.size
        }
    }

    override fun getItemCount(): Int {
        return if (getRealCount() > 1) getRealCount() + increaseCount else getRealCount()
    }
    open fun getRealPosition(isIncrease: Boolean, position: Int, realCount: Int): Int {
        if (!isIncrease) {
            return position
        }
        return when (position) {
            0 -> {
                realCount - 1
            }
            realCount + 1 -> {
                0
            }
            else -> {
                position - 1
            }
        }
    }
}