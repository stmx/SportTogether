package com.android.stmx.myapp.ui.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.stmx.myapp.MyApp
import com.android.stmx.myapp.R
import com.android.stmx.myapp.databinding.FragmentAddActionBinding
import com.android.stmx.myapp.domain.model.Action
import com.android.stmx.myapp.helper.addZero
import com.android.stmx.myapp.ui.viewmodel.ActionListViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AddActionFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: FragmentAddActionBinding

    companion object {
        fun newInstance(): AddActionFragment {
            val args = Bundle()
            val fragment = AddActionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddActionBinding.bind(inflater.inflate(R.layout.fragment_add_action, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyApp.appComponent.inject(this)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ActionListViewModel::class.java)
        binding.addDate.keyListener = null
        binding.addTime.keyListener = null
        binding.addToFirebase.setOnClickListener {
            val action = createAction()
            viewModel.addAction(action)
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.showDatePicker.setOnClickListener {
            showDatePickerDialog()
        }
        binding.showTimePicker.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private val datePickerListener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        binding.addDate.text.clear()
        binding.addDate.text.insert(0, "${dayOfMonth.addZero()}.${monthOfYear.addZero()}.${year.addZero()}")
    }
    private val timePickerListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        binding.addTime.text.clear()
        binding.addTime.text.insert(0, "${hourOfDay.addZero()}:${minute.addZero()}")
    }

    private fun showDatePickerDialog() {
        val dateAndTime = Calendar.getInstance()
        DatePickerDialog(requireActivity(), datePickerListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show()
    }

    private fun showTimePickerDialog() {
        val dateAndTime = Calendar.getInstance()
        TimePickerDialog(requireActivity(), timePickerListener,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                true
        ).show()
    }

    private fun createAction(): Action {
        return Action.newInstance(
                Date(),
                getDateTime(),
                getDescription(),
                getAmountPeople(),
                getPayment(),
                getPlace(),
                getSport()
        )
    }

    private fun getDateTime(): Date {
        val simple = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ROOT)
        return simple.parse("${getTime()} ${getDate()}")!!
    }

    private fun getPlace(): String {
        return binding.addPlace.text.toString()
    }

    private fun getPayment(): Int {
        return binding.addPayment.text.toString().toInt()
    }

    private fun getAmountPeople(): Int {
        return binding.addNeedPeople.text.toString().toInt()
    }

    private fun getDescription(): String {
        return binding.addDescription.text.toString()
    }

    private fun getTime(): String {
        return binding.addTime.text.toString()
    }

    private fun getSport(): String {
        return binding.addSport.selectedItem.toString()
    }

    private fun getDate(): String {
        return binding.addDate.text.toString()
    }
}