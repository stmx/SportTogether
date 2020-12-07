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
import com.android.stmx.myapp.R
import com.android.stmx.myapp.databinding.FragmentAddActionBinding
import com.android.stmx.myapp.domain.model.Action
import com.android.stmx.myapp.ui.viewmodel.ActionListViewModel
import java.text.SimpleDateFormat
import java.util.*


class AddActionFragment : Fragment() {

    lateinit var binding: FragmentAddActionBinding
    private val dateAndTime = Calendar.getInstance()

    companion object {
        fun newInstance(): AddActionFragment {
            val args = Bundle()
            val fragment = AddActionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(ActionListViewModel::class.java)
        binding = FragmentAddActionBinding.bind(view)
        binding.addDate.keyListener = null
        binding.addTime.keyListener = null
        binding.addToFirebase.setOnClickListener {
            val action = Action.newInstance(
                    getDateTime(),
                    getDescription(),
                    getAmountPeople(),
                    getPayment(),
                    getPlace(),
                    getSport()
            )
            viewModel.addAction(action)

            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.showDatePicker.setOnClickListener {
            DatePickerDialog(requireActivity(), datePickerListener,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show()
        }
        binding.showTimePicker.setOnClickListener {
            TimePickerDialog(requireActivity(), timePickerListener,
                    dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE),
                    true
            ).show()
        }
    }

    private val datePickerListener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        binding.addDate.text.clear()
        binding.addDate.text.insert(0, "${addZeroToInt(dayOfMonth)}.${addZeroToInt(monthOfYear)}.${addZeroToInt(year)}")
    }
    private val timePickerListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        binding.addTime.text.clear()
        binding.addTime.text.insert(0, "${addZeroToInt(hourOfDay)}:${addZeroToInt(minute)}")
    }

    private fun addZeroToInt(num: Int): String {
        return if (num < 10) "0$num" else "$num"
    }
    private fun getDateTime():Date {
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