package com.egco428.a13348

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main2.*
import android.util.Log
import android.view.View
import com.android.volley.toolbox.Volley
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import android.content.Intent
import android.graphics.Color
import java.text.SimpleDateFormat
import java.util.*


class Main2Activity : AppCompatActivity() {
    var url = "http://www.atilal.info/cookies.php"
    var msg : String = ""
    var mean : String = ""
    var dateToStr : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        make_button.setOnClickListener {

            val request = StringRequest(url, object : Response.Listener<String> {
                override fun onResponse(string: String) {
                    Toast.makeText(applicationContext, "Waiting...", Toast.LENGTH_SHORT).show()
                    parseJsonData(string)

                    //hide make a wish button
                    make_button.setVisibility(View.INVISIBLE)
                    save_button.setVisibility(View.VISIBLE)

                    imageView2.setImageResource(R.drawable.opened_cookie)
                    result_make.setText(msg)
                    if(mean.equals("negative")){
                        re.setTextColor(Color.parseColor("#FF6633"))
                        result_make.setTextColor(Color.parseColor("#FF6633"))
                    }
                    val curDate = Date()
                    val format = SimpleDateFormat("dd-MMM-yyyy HH:mm")
                    dateToStr = format.format(curDate)
                    date.setText(dateToStr)
                    textView3.setText(msg)

                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, "Some error occurred!!", Toast.LENGTH_SHORT).show()
                }
            })

            val rQueue = Volley.newRequestQueue(this@Main2Activity)
            rQueue.add(request)
        }

        save_button.setOnClickListener {
            if(!(msg.equals("") && mean.equals(""))){
                val intent = Intent()
                intent.putExtra("msg", msg)
                intent.putExtra("mean", mean)
                intent.putExtra("date",dateToStr)
                setResult(2, intent)
                finish()
            }
            else{
                //
            }
        }

    }

    fun parseJsonData(jsonString: String) {
        try {
            val json = JSONObject(jsonString)
            msg = json.getString("message")
            mean = json.getString("meaning")
            Log.d("msg",msg.toString())
            Log.d("mean",mean.toString())

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent()
                setResult(3, intent)
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
