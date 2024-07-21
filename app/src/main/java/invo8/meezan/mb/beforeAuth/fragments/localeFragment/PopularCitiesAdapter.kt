package invo8.meezan.mb.beforeAuth.fragments.localeFragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import invo8.meezan.mb.R
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.interfaces.LocationSelectCallBack
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.modelClasses.CityLocations
import java.util.ArrayList


class PopularCitiesAdapter(
    private val context: Context,
    private var cityLocationsArrayList: List<CityLocations>,
    private val locationSelectCallBack: LocationSelectCallBack

) : RecyclerView.Adapter<PopularCitiesAdapter.ViewHolder>() {
    private var isMove = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.item_popular_cities, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cityLocations = cityLocationsArrayList[position]

        Log.wtf(
            "Adapter_List",
            "Size :: ${cityLocationsArrayList.size} List :: $cityLocationsArrayList"
        )


        holder.title.text = cityLocations.name

        if (cityLocations.name!!.lowercase() == "Lahore".lowercase()) {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_lahore_place))
                .into(holder.image)
        } else if (cityLocations.name.lowercase() == "Faisalabad".lowercase()) {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_faislabad))
                .into(holder.image)
        } else if (cityLocations.name.lowercase() == "Gujranwala".lowercase()) {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_gujaranwala))
                .into(holder.image)
        } else if (cityLocations.name.lowercase() == "Islamabad".lowercase()) {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_islamabad))
                .into(holder.image)
        } else if (cityLocations.name.lowercase() == "Karachi".lowercase()) {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_karachi))
                .into(holder.image)
        } else if (cityLocations.name.lowercase() == "Multan".lowercase()) {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_multan))
                .into(holder.image)
        } else if (cityLocations.name.lowercase() == "Peshawar".lowercase()) {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_peshawar))
                .into(holder.image)
        } else if (cityLocations.name.lowercase() == "Rawalpindi".lowercase()) {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_rawalpindi))
                .into(holder.image)
        }


        holder.itemView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    // You can perform any touch-related actions here
                    startScaleAnimation(holder.image)
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    // Handle touch up event
                    // You can perform any touch-related actions here
                    cancelScaleAnimation(holder.image)

                    // Delegate the click event to the click listener
                    holder.itemView.performClick()
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_CANCEL -> {
                    // Handle touch cancel event
                    // You can perform any touch-related actions here
                    cancelScaleAnimation(holder.image)
                    return@setOnTouchListener true
                }
                else -> {
                    return@setOnTouchListener false
                }
            }
        }

        holder.itemView.setOnClickListener {
            locationSelectCallBack.onItemClick(cityLocations)
        }


    }

    override fun getItemCount(): Int {
        return cityLocationsArrayList.size
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var image: ImageView

        init {
            title = itemView.findViewById(R.id.item_city)
            image = itemView.findViewById(R.id.imageC)
        }
    }

    private fun startScaleAnimation(view: View) {
        val scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.3f)
        val scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.3f)
        scaleDownX.duration = 500
        scaleDownY.duration = 500
        scaleDownX.start()
        scaleDownY.start()
    }

    private fun cancelScaleAnimation(view: View) {
        val scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f)
        val scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f)
        scaleDownX.duration = 500
        scaleDownY.duration = 500
        scaleDownX.start()
        scaleDownY.start()
    }

    fun filterList(filterlist: ArrayList<CityLocations>) {
        cityLocationsArrayList = filterlist
        notifyDataSetChanged()

    }

}