package com.example.android.weatheralarmclock

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.android.weatheralarmclock.model.Alarm
import kotlinx.android.synthetic.main.activity_list_alarms.*

class ListAlarmsActivity : AppCompatActivity() {
    private var alarms = listOf(
            Alarm("11:00", "Alarm 1"),
            Alarm("11:06", "Alarm 2"),
            Alarm("13:06", "Alarm 3")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alarms)

        alarms_list_view.adapter = AlarmAdapter(alarms, this)
        alarms_list_view.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "Item ${position + 1} [id=$id] was clicked", Toast.LENGTH_SHORT).show()
            editItem(position)
        }

        button_send_mail.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "message/rfc822"
            sendIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("cristian.danciu.01@gmail.com"))
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Alarm app test")
            sendIntent.putExtra(Intent.EXTRA_TEXT, text_send_mail.text)
            try {
                startActivity(Intent.createChooser(sendIntent, "Send Email"))
            } catch (ex: android.content.ActivityNotFoundException) {
                Toast.makeText(this, "Couldn't find any email clients", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun editItem(position: Int) {
        val dialog = Dialog(this)
        dialog.setTitle("Input Box")
        dialog.setContentView(R.layout.edit_alarm)

        val editTextTime = dialog.findViewById<EditText>(R.id.text_edit_time)
        val editTextLabel = dialog.findViewById<EditText>(R.id.text_edit_label)
        editTextTime.setText(alarms[position].time)
        editTextLabel.setText(alarms[position].label)

        dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<Button>(R.id.button_save).setOnClickListener {
            alarms[position].time = editTextTime.text.toString()
            alarms[position].label = editTextLabel.text.toString()
            (alarms_list_view.adapter as AlarmAdapter).notifyDataSetChanged()
            dialog.dismiss()
        }

        dialog.show()
    }

    private class AlarmAdapter(var alarms: List<Alarm>, var activity: Activity) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: View.inflate(activity, R.layout.alarm_item, null)

            view.findViewById<TextView>(R.id.text_index).text = (position + 1).toString()
            view.findViewById<TextView>(R.id.text_time).text = getItem(position).time
            view.findViewById<TextView>(R.id.text_label).text = getItem(position).label

            return view
        }

        override fun getItem(position: Int): Alarm = alarms[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getCount(): Int = alarms.size
    }


}
