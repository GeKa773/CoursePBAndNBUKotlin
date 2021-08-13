package com.gekaradchenko.coursespbandnbukotlin

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.DatePickerDialog
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gekaradchenko.coursespbandnbukotlin.databinding.ActivityMainBinding
import com.gekaradchenko.coursespbandnbukotlin.nbu.NBUListAdapter
import com.gekaradchenko.coursespbandnbukotlin.nbu.NBUListener
import com.gekaradchenko.coursespbandnbukotlin.privatebank.PBListAdapter
import com.gekaradchenko.coursespbandnbukotlin.privatebank.PBListener
import java.util.*


const val LANDSCAPE_APP_NAME = ""
private lateinit var viewModel: ViewModel
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModelFactory = ViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            supportActionBar?.setCustomView(R.layout.action_bar_layout)
        } else {
            supportActionBar?.title = LANDSCAPE_APP_NAME
        }


        val pbAdapter = PBListAdapter(PBListener {
            setSelectedItem(it)
        })

        val nbuAdapter = NBUListAdapter(NBUListener {
        }, applicationContext)

        binding.recyclerViewForPrivateBank.adapter = pbAdapter

        binding.recyclerViewForNBU.adapter = nbuAdapter

        viewModel.pbList.observe(this, Observer {
            pbAdapter.submitList(it)
        })

        viewModel.nbuList.observe(this, Observer {
            nbuAdapter.submitList(it)
        })

        viewModel.onPBDate.observe(this, ::showDatePicker)

        viewModel.datePB.observe(this, Observer {
            viewModel.getPBCourseReal()
        })
        viewModel.dateNBU.observe(this, Observer {
            viewModel.getNBUCourseReal()
        })

    }

    private fun setSelectedItem(course: String) {
        viewModel.nbuList.value?.forEachIndexed { index, nbuCourse ->
            nbuCourse.isSelected = false
            if (nbuCourse.cc == course) {
                nbuCourse.isSelected = true
                binding.recyclerViewForNBU.scrollToPosition(index)
            }
        }
    }

    private fun showDatePicker(b: Boolean) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            viewModel.setDate(calendar, b)
        }
        DatePickerDialog(this, datePicker, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
