package com.example.android.weatheralarmclock

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.android.weatheralarmclock.model.Alarm
import kotlinx.android.synthetic.main.activity_list_alarms.*

class ListAlarmsActivity : AppCompatActivity() {
    private var alarms: MutableList<Alarm> = listOf(
            Alarm("11:00", "Alarm 1", active = true),
            Alarm("11:06", "Alarm 2", active = false),
            Alarm("13:06", "Alarm 3", active = true)
    ).toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alarms)

        list_view_alarms.adapter = AlarmAdapter(alarms, this)
    }

    private class AlarmAdapter(var alarms: MutableList<Alarm>, var activity: Activity) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: inflateNewAlarmView(position, activity, R.layout.alarm_item, null)

            val isAlarmActive = getItem(position).active
            val activeAlarmTimeTextView = view.findViewById<TextView>(R.id.text_time_active)
            val inactiveAlarmTimeTextView = view.findViewById<TextView>(R.id.text_time_inactive)

            // Display alarm time in it's own active/inactive text view
            if (isAlarmActive) {
                activeAlarmTimeTextView.text = getItem(position).time
                activeAlarmTimeTextView.visibility = View.VISIBLE
                inactiveAlarmTimeTextView.visibility = View.GONE
            } else {
                inactiveAlarmTimeTextView.text = getItem(position).time
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
                    .setOnClickListener { editAlarm(position) }

            return newAlarmView
        }

        private fun deleteAlarm(position: Int) {
            alarms.removeAt(position)
            notifyDataSetChanged()
        }

        private fun toggleAlarmActive(position: Int) {
            alarms[position].active = !alarms[position].active
            notifyDataSetChanged()
        }

        private fun editAlarm(position: Int) {
            val dialog = Dialog(this.activity)
            dialog.setContentView(R.layout.edit_alarm)

            val editTextTime = dialog.findViewById<EditText>(R.id.text_edit_time)
            val editTextLabel = dialog.findViewById<EditText>(R.id.text_edit_label)
            editTextTime.setText(alarms[position].time)
            editTextLabel.setText(alarms[position].label)

            dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener { dialog.dismiss() }
            dialog.findViewById<Button>(R.id.button_save).setOnClickListener {
                alarms[position].time = editTextTime.text.toString()
                alarms[position].label = editTextLabel.text.toString()
                notifyDataSetChanged()
                dialog.dismiss()
            }

            dialog.show()
        }
    }

}
