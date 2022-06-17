package hr.algebra.eventmanagement.data

import hr.algebra.eventmanagement.model.Event
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class JsonHelper {
    companion object {
        fun getEvents(context: Context): List<Event> {
            lateinit var jsonString: String
            try {
                jsonString =
                    context.assets.open("events.json").bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                Log.e("Error", ioException.stackTrace.toString())
            }
            val listEvent = object : TypeToken<List<Event>>() {}.type
            return Gson().fromJson(jsonString, listEvent)
        }
    }
}