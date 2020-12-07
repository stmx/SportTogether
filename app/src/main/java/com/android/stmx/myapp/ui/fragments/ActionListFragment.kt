package com.android.stmx.myapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.stmx.myapp.R
import com.android.stmx.myapp.databinding.FragmentActionListBinding
import com.android.stmx.myapp.domain.model.Action
import com.android.stmx.myapp.ui.adapter.ActionListAdapter
import com.android.stmx.myapp.ui.viewmodel.ActionListViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class ActionListFragment : Fragment() {
    var actionListAdapter = ActionListAdapter()
    lateinit var binding: FragmentActionListBinding
    private lateinit var recyclerView:RecyclerView

    companion object{
        fun newInstance(): ActionListFragment {
            val args = Bundle()
            val fragment = ActionListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_action_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(ActionListViewModel::class.java)
        binding = FragmentActionListBinding.bind(view)


        recyclerView = binding.actionListRecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = actionListAdapter
        }
        viewModel.getDataFlowAction().observe(viewLifecycleOwner){
            actionListAdapter.updateData(it)
        }

        binding.buttonAddRandom.setOnClickListener {
            viewModel.addAction(Action("", viewModel.getUserId(),Date() , "deskription${Random.nextInt(0, 200)}", Random.nextInt(0, 10), Random.nextInt(100, 200), "x", "foot"))
        }
        binding.buttonAdd.setOnClickListener {
            requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,AddActionFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
        }




    }
    fun getDateByString(stringDate:String):Date {
        val tz = TimeZone.getTimeZone("Europe/Moscow")
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.ROOT)
        dateFormat.isLenient = false
        dateFormat.timeZone = tz
        return dateFormat.parse(stringDate)!!
    }

}