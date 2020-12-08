package com.android.stmx.myapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.stmx.myapp.R
import com.android.stmx.myapp.databinding.ItemActionListBinding
import com.android.stmx.myapp.domain.fiture.SortAndFilter.sortByDate
import com.android.stmx.myapp.domain.model.Action

class ActionListAdapter: RecyclerView.Adapter<ActionListAdapter.ActionHolder>() {

    private val actionList:MutableList<Action> = ArrayList()

    fun updateData(newActionList: List<Action>) {
        val sorted = sortByDate(newActionList.toMutableList() as ArrayList<Action>)
        val actionDiffUtilCallback = DiffUtilActionList(actionList,sorted)
        val resultDiffUtil = DiffUtil.calculateDiff(actionDiffUtilCallback)
//        actionList.clear()
//        actionList.addAll(newActionList)
//        notifyDataSetChanged()
        actionList.clear()
        actionList.addAll(sorted)
        resultDiffUtil.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_action_list,parent,false)
        return ActionHolder(view)
    }

    override fun onBindViewHolder(holder: ActionHolder, position: Int) {
        holder.bind(actionList[position])
    }

    override fun getItemCount(): Int {
        return actionList.size
    }

    inner class ActionHolder(item: View) : RecyclerView.ViewHolder(item) {
        private lateinit var binding:ItemActionListBinding
        fun bind(action:Action) {
            binding = ItemActionListBinding.bind(itemView)
            binding.actionId.text = "id: ${action.id}"
            binding.actionIdOwner.text = "idOwner: ${action.idOwner}"
            binding.actionDate.text = "date: ${action.getDateString()}"
            binding.actionTime.text = "time: ${action.getTimeString()}"
            binding.actionDescription.text = "description: ${action.description}"
            binding.actionNeedPeople.text = "needPeople: ${action.needPeople}"
            binding.actionPayment.text = "payment: ${action.payment}"
            binding.actionPlace.text = "place: ${action.place}"
            binding.actionSport.text = "sport: ${action.sport}"
        }
    }
}
