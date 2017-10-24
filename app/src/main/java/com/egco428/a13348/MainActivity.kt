package com.egco428.a13348

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_main.view.*

private val names = arrayListOf<String>("You're Lucky", "You will get A", "Don't Panic")
private val meaning = arrayListOf<String>("positive", "positive", "negative")
private val dates = arrayListOf<String>("10-Oct-2017 19:10", "10-Oct-2017 22:10", "12-Oct-2017 19:10")

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_listview.adapter = MyCustomAdapter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_new -> {
                val intent = Intent(this, Main2Activity::class.java)
                startActivityForResult(intent, REQUEST_CODE);

                return true
            }
        }

        return super.onOptionsItemSelected(item)

    }


    private class MyCustomAdapter(context: Context): BaseAdapter(){
        private val mContext: Context

        init {
            //,าจาก this ด้านบน
            mContext = context
        }

        fun addNewValue(msg: String, mean: String, date: String) {
            names.add(msg)
            meaning.add(mean)
            dates.add(date)
            //Log.d("names",names.last())
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return names[position]
        }

        override fun getCount(): Int {
            return names.size
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            /*val textView = TextView(mContext)
            textView.text = "Show the message"
            return textView*/
            val blueColor = Color.parseColor("#0099FF")
            val orangeColor = Color.parseColor("#FF6633")
            val rowMain: View
            if(convertView == null){
                val layoutInflator = LayoutInflater.from(viewGroup!!.context)
                rowMain = layoutInflator.inflate(R.layout.row_main,viewGroup,false) //input sub layout
                val viewHolder = ViewHolder(rowMain.name_textview,rowMain.position_textview,rowMain.imageView)
                rowMain.tag = viewHolder
            }else{
                rowMain = convertView
            }

            val viewHolder = rowMain.tag as ViewHolder
            if(meaning.get(position).equals("positive")){
                rowMain.name_textview.setTextColor(blueColor)
            }
            else{
                rowMain.name_textview.setTextColor(orangeColor)
            }

            //Log.d("Result","Load name_textView")
            viewHolder.nameTextView.text = names.get(position)
            //Log.d("Result","Load position_textView")
            viewHolder.positionTextView.text = dates.get(position)

            //remove row
            rowMain.setOnClickListener {
                rowMain.animate().setDuration(1500).alpha(0F).withEndAction({
                    names.removeAt(position)
                    meaning.removeAt(position)
                    dates.removeAt(position)
                    notifyDataSetChanged()
                    rowMain.setAlpha(1.0F)
                })
            }

            return rowMain

        }
        private class ViewHolder(val nameTextView: TextView, val positionTextView: TextView, val rowImageView: ImageView)
    }


    // This method is called when the second activity finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // check that it is the SecondActivity with an OK result
        if (resultCode == 2) {

                // get String data from Intent
            val returnMsg = data.getStringExtra("msg")
            val returnMean = data.getStringExtra("mean")
            val returnDate = data.getStringExtra("date")

            val myCustomAdapter = MyCustomAdapter(this)
            myCustomAdapter.addNewValue(returnMsg, returnMean, returnDate)

            main_listview.adapter = MyCustomAdapter(this)
        }
        else if(resultCode == 3){
            //do nothing
        }
    }
}
