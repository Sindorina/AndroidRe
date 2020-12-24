package cn.signp.banner

import android.view.ViewGroup

interface IViewHolder<T,VH> {
    fun onCreateHolder(parent:ViewGroup,viewType:Int):VH

    fun onBindView(holder:VH,data:T,position:Int,size:Int)
}