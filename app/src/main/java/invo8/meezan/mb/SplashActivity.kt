package invo8.meezan.mb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rehman.utilities.Utils
import invo8.meezan.mb.beforeAuth.BeforeAuthMainActivity
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.modelClasses.CityLocations
import invo8.meezan.mb.tinyDB.TinyDB
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.util.ArrayList

class SplashActivity : AppCompatActivity() {
    private lateinit var tinyDB: TinyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.hideSystemUiVisibility(this)
        setContentView(R.layout.activity_splash)
        tinyDB = TinyDB(this)

        Handler().postDelayed({
            loadCitiesFromAssets()
            startActivity(Intent(this, BeforeAuthMainActivity::class.java))
            finish()
        }, 2000)
    }

    private fun loadCitiesFromAssets() {
        val json: String
        try {
            val inputStream = assets.open("cities.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.defaultCharset())

            // Use Gson to parse the JSON
            val cities = Gson().fromJson(json, Array<CityLocations>::class.java)

// Create an ArrayList of Any and add your CityLocations objects
            val cityList: ArrayList<Any> = ArrayList(cities.asList())

// Store it in TinyDB
            tinyDB.putListObject("Cities_List", cityList)




            Log.wtf("CITIES","Save List")


        } catch (e: IOException) {
            e.printStackTrace()
        }
    }



}