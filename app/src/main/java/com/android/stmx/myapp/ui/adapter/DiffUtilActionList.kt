package com.android.stmx.myapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.android.stmx.myapp.domain.model.Action

class DiffUtilActionList(
        private val oldData: List<Action>,
        private val newData: List<Action>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id==newData[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id==newData[newItemPosition].id
    }
}