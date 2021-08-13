package com.gekaradchenko.coursespbandnbukotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gekaradchenko.coursespbandnbukotlin.nbu.NBUCourse
import com.gekaradchenko.coursespbandnbukotlin.nbu.NBUCourseApi
import com.gekaradchenko.coursespbandnbukotlin.privatebank.PBCourseApi
import com.gekaradchenko.coursespbandnbukotlin.privatebank.PBCourse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val RUB = "RUB"
private const val USD = "USD"
private const val EUR = "EUR"
private const val DATE_FORMAT_PB = "dd.MM.yyyy"
private const val DATE_FORMAT_NBU = "yyyyMMdd"

class ViewModel(application: Application) : AndroidViewModel(application) {
    val app = application
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _onPBDate = SingleLiveEvent<Boolean>()
    val onPBDate: LiveData<Boolean> = _onPBDate

    private val _datePB = MutableLiveData<Date>()
    val datePB: LiveData<Date> = _datePB

    private val _dateNBU = MutableLiveData<Date>()
    val dateNBU: LiveData<Date> = _dateNBU


    private val _pbList = MutableLiveData<List<PBCourse>>()
    val pbList: LiveData<List<PBCourse>> = _pbList

    private val _nbuList = MutableLiveData<List<NBUCourse>>()
    val nbuList: LiveData<List<NBUCourse>> = _nbuList


    init {

        _datePB.value = Date()
        _dateNBU.value = Date()
        getPBCourseReal()
        getNBUCourseReal()
    }

    fun onShowDatePickerPB() {
        _onPBDate.postValue(true)
    }

    fun onShowDatePickerNBU() {
        _onPBDate.postValue(false)
    }

    fun setDate(calendar: Calendar, b: Boolean) {
        if (b) _datePB.value = calendar.time else _dateNBU.value = calendar.time
    }


    fun getPBCourseReal() {
        coroutineScope.launch {

            val getCourseDeferred = PBCourseApi.RETROFIT_INSTANCE.getPBCourse(
                true,
                SimpleDateFormat(DATE_FORMAT_PB).format(datePB.value)
            )

            try {
                val result = getCourseDeferred.await()
                val list = ArrayList<PBCourse>()
                for (pb in result.exchangeRate) {
                    if (pb.currency == RUB) list.add(pb)
                    if (pb.currency == USD) list.add(pb)
                    if (pb.currency == EUR) list.add(pb)
                }
                _pbList.value = list

            } catch (t: Throwable) {
                Log.d("ViewModel", "getPBCourseReal: ${t.message}")
            }
        }
    }

     fun getNBUCourseReal() {
        coroutineScope.launch {

            val getCourseDeferred = NBUCourseApi.RETROFIT_INSTANCE.getNBUCourse(
                SimpleDateFormat(DATE_FORMAT_NBU).format(dateNBU.value),
                true
            )
            try {
                val result = getCourseDeferred.await()
                val list = ArrayList<NBUCourse>()
//                for (nbu in result) {
//                    if (nbu.cc == RUB) list.add(nbu)
//                    if (nbu.cc == USD) list.add(nbu)
//                    if (nbu.cc == EUR) list.add(nbu)
//                }

//                list.forEach {
//                    println("${it.cc} продажа: ${it.rate}  покупка: ${it.txt}")
//                }
//                println(result.size)

                _nbuList.value = result
            } catch (t: Throwable) {
                Log.d("ViewModel", "getNBUCourseReal: ${t.message}")
            }
        }
    }
}