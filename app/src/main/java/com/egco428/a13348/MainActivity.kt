package com.egco428.a13348

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.bind
import android.util.Log
import android.view.*
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_main.view.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // setSupportActionBar(tool_bar as android.support.v7.widget.Toolbar?)
       // supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
                //finish()
                val intent = Intent(this, Main2Activity::class.java)
                startActivity(intent)

                return true
            }
        }

        return super.onOptionsItemSelected(item)

    }

    private class MyCustomAdapter(context: Context): BaseAdapter(){
        private val mContext: Context
        private val names = arrayListOf<String>("You're Lucky", "You will get A", "Don't Panic")
        private val dates = arrayListOf<String>("You're Lucky", "You will get A", "Don't Panic")


        init {
            //,าจาก this ด้านบน
            mContext = context
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
            val greyColor = Color.parseColor("#D7D5D2")
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
            if(position%2 == 0){
                rowMain.setBackgroundColor(greyColor)

                //viewHolder.rowImageView.setImageResource(R.drawable.woman)

            }

            Log.d("Result","Load name_textView")
            viewHolder.nameTextView.text = names.get(position)
            Log.d("Result","Load position_textView")
            viewHolder.positionTextView.text = "Row Number: $position"

            //remove row
            rowMain.setOnClickListener {
                rowMain.animate().setDuration(1500).alpha(0F).withEndAction({
                    names.removeAt(position)
                    notifyDataSetChanged()
                    rowMain.setAlpha(1.0F)
                })
            }

            return rowMain

        }
        private class ViewHolder(val nameTextView: TextView, val positionTextView: TextView, val rowImageView: ImageView)
    }
}
