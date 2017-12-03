package com.example.android.weatheralarmclock

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.android.weatheralarmclock.model.Alarm
import com.example.android.weatheralarmclock.model.AlarmViewModel
import com.example.android.weatheralarmclock.model.toTimeUnitString
import kotlinx.android.synthetic.main.activity_list_alarms.*
import java.util.*


class ListAlarmsActivity : AppCompatActivity() {
    private lateinit var alarmAdapter: AlarmAdapter
    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_alarms)

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)

        alarmViewModel.alarms?.observe(this, Observer { alarms ->
            if (alarms != null) {
                this.alarmAdapter = AlarmAdapter(alarms, this, alarmViewModel)
                list_view_alarms.adapter = this.alarmAdapter
                list_view_alarms.emptyView = text_view_empty_list
            }
        })

        button_add_alarm.setOnClickListener { editAlarm() }
    }

    private fun editAlarm(position: Int? = null) {
        val editAlarmDialog = Dialog(this)
        editAlarmDialog.setContentView(R.layout.edit_alarm)

        if (position != null) {
            val alarm = alarmViewModel.alarms!!.value!![position]
            editAlarmDialog.findViewById<TextView>(R.id.text_edit_time_hour)
                    .text = alarm.hour.toString()
            editAlarmDialog.findViewById<TextView>(R.id.text_edit_time_minute)
                    .text = alarm.minute.toTimeUnitString()
            editAlarmDialog.findViewById<EditText>(R.id.text_edit_alarm_label)
                    .setText(alarm.label)
        }

        val (defaultDialogHour, defaultDialogMinute) = getDialogTime(editAlarmDialog)

        val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    editAlarmDialog.findViewById<TextView>(R.id.text_edit_time_hour).text = hour.toString()
                    editAlarmDialog.findViewById<TextView>(R.id.text_edit_time_minute).text = minute.toTimeUnitString()
                    if (!editAlarmDialog.isShowing) editAlarmDialog.show()
                },
                defaultDialogHour,
                defaultDialogMinute,
                true
        )

        editAlarmDialog.findViewById<Button>(R.id.button_edit_alarm_cancel)
                .setOnClickListener { editAlarmDialog.dismiss() }
        editAlarmDialog.findViewById<Button>(R.id.button_edit_alarm_save)
                .setOnClickListener {
                    if (saveAlarm(position, editAlarmDialog)) {
                        editAlarmDialog.dismiss()
                    }
                }
        editAlarmDialog.findViewById<LinearLayout>(R.id.linear_layout_edit_alarm_time)
                .setOnClickListener {
                    val (currentDialogHour, currentDialogMinute) = getDialogTime(editAlarmDialog)
                    timePickerDialog.updateTime(currentDialogHour, currentDialogMinute)
                    timePickerDialog.show()
                }

        when (position) {
            null -> timePickerDialog.show()
            else -> editAlarmDialog.show()
        }
    }

    private fun saveAlarm(position: Int?, editAlarmDialog: Dialog): Boolean {
        val alarm = Alarm(
                editAlarmDialog.findViewById<TextView>(R.id.text_edit_time_hour).text.toString().toInt(),
                editAlarmDialog.findViewById<TextView>(R.id.text_edit_time_minute).text.toString().toInt(),
                editAlarmDialog.findViewById<EditText>(R.id.text_edit_alarm_label).text.toString(),
                true
        )

        if (alarmViewModel.isAlarmAlreadySet(alarm, position)) {
            Toast.makeText(
                    this,
                    "An alarm set for ${alarm.hour}:${alarm.minute.toTimeUnitString()} already exist!",
                    Toast.LENGTH_SHORT
            ).show()

            return false
        }

        alarmViewModel.saveAlarm(alarm)

        return true
    }

    private fun getDialogTime(dialog: Dialog): Pair<Int, Int> {
        val hour = dialog.findViewById<TextView>(R.id.text_edit_time_hour).text
        return if (hour == "") {
            // Return current hour/minute
            val calendar = Calendar.getInstance()
            Pair(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
        } else {
            // The time is available in the edit alarm text views, so return that
            val minute = dialog.findViewById<TextView>(R.id.text_edit_time_minute).text
            Pair(hour.toString().toInt(), minute.toString().toInt())
        }
    }

    private class AlarmAdapter(var alarms: List<Alarm>, var activity: Activity, var alarmViewModel: AlarmViewModel) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: inflateNewAlarmView(position, activity, R.layout.alarm_item, null)

            val isAlarmActive = getItem(position).active
            val activeAlarmTimeTextView = view.findViewById<TextView>(R.id.text_time_active)
            val inactiveAlarmTimeTextView = view.findViewById<TextView>(R.id.text_time_inactive)

            // Display alarm time in it's own active/inactive text view
            val timeString = getItem(position).hour.toString() + ':' + getItem(position).minute.toTimeUnitString()
            if (isAlarmActive) {
                activeAlarmTimeTextView.text = timeString
                activeAlarmTimeTextView.visibility = View.VISIBLE
                inactiveAlarmTimeTextView.visibility = View.GONE
            } else {
                inactiveAlarmTimeTextView.text = timeString
                inactiveAlarmTimeTextView.visibility = View.VISIBLE
                activeAlarmTimeTextView.visibility = View.GONE
            }

            // Handle alarm label
            val labelTextView = view.findViewById<TextView>(R.id.text_label)
            val labelText = getItem(position).label
            if (labelText == "") {
                labelTextView.visibility = View.GONE
            } else {
                labelTextView.text = labelText
                labelTextView.visibility = View.VISIBLE
            }

            // Handle alarm on/off switch
            val alarmSwitch = view.findViewById<Switch>(R.id.switch_alarm)
            alarmSwitch.isChecked = isAlarmActive
            alarmSwitch.setOnClickListener { toggleAlarmActive(position) }

            // Handle remove alarm button
            view.findViewById<Button>(R.id.button_delete_alarm).setOnClickListener { deleteAlarm(position) }

            return view
        }

        override fun getItem(position: Int): Alarm = alarms[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getCount(): Int = alarms.size

        private fun inflateNewAlarmView(position: Int, activity: Activity, layoutId: Int, root: ViewGroup?): View {
            val newAlarmView = View.inflate(activity, layoutId, root)
            newAlarmView.findViewById<LinearLayout>(R.id.edit_alarm_linear_layout)
                    .setOnClickListener { (activity as ListAlarmsActivity).editAlarm(position) }

            return newAlarmView
        }

        private fun deleteAlarm(position: Int) {
            alarmViewModel.deleteAlarm(position)
        }

        private fun toggleAlarmActive(position: Int) {
            val alarm = getItem(position)
            alarm.active = !alarm.active
            alarmViewModel.updateAlarm(alarm)
        }
    }

}
