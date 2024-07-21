package invo8.meezan.mb.beforeAuth.fragments.localeFragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import invo8.meezan.mb.R
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.interfaces.LocationSelectCallBack
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.modelClasses.CityLocations
import java.util.ArrayList


class OtherCitiesAdapter(
    private val context: Context,
    private var cityLocationsArrayList: ArrayList<CityLocations>,
    private val locationSelectCallBack: LocationSelectCallBack

) : RecyclerView.Adapter<OtherCitiesAdapter.ViewHolder>() {
    private var currentPage = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.item_other_cities, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cityLocations = cityLocationsArrayList[position]

        Log.wtf(
            "Adapter_List",
            "Size :: ${cityLocationsArrayList.size} List :: $cityLocationsArrayList"
        )


        holder.title.text = cityLocations.name


        holder.itemView.setOnClickListener {
            locationSelectCallBack.onItemClick(cityLocations)
        }


    }

    override fun getItemCount(): Int {
        return cityLocationsArrayList.size
    }

    fun filterList(filterlist: ArrayList<CityLocations>) {
        cityLocationsArrayList = filterlist
        notifyDataSetChanged()

    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView

        init {
            title = itemView.findViewById(R.id.item_title)
        }
    }

}