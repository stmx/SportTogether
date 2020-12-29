package com.android.stmx.myapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.stmx.myapp.MyApp
import com.android.stmx.myapp.R
import com.android.stmx.myapp.databinding.FragmentActionListBinding
import com.android.stmx.myapp.domain.fiture.SortAndFilter
import com.android.stmx.myapp.domain.model.Action
import com.android.stmx.myapp.helper.RandomAction
import com.android.stmx.myapp.ui.adapter.ActionListAdapter
import com.android.stmx.myapp.ui.viewmodel.ActionListViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ActionListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var actionListAdapter = ActionListAdapter()
    lateinit var binding: FragmentActionListBinding
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance(): ActionListFragment {
            val args = Bundle()
            val fragment = ActionListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActionListBinding.bind(inflater.inflate(R.layout.fragment_action_list, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyApp.appComponent.inject(this)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ActionListViewModel::class.java)
        recyclerView = binding.actionListRecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = actionListAdapter
        }
        viewModel.getDataFlowAction().observe(viewLifecycleOwner) {
            actionListAdapter.updateData(it)
            recyclerView.scrollToPosition(0)
        }

        binding.buttonAddRandom.setOnClickListener {
            viewModel.addAction(RandomAction.generateRandomAction())
        }
        binding.buttonUserAction.setOnClickListener{

            val newData = SortAndFilter.filterByOwner(actionListAdapter.actionList as ArrayList<Action>,viewModel.getUserId())
            actionListAdapter.updateData(newData)

        }
        binding.buttonAdd.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, AddActionFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

}