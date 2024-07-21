package invo8.meezan.mb.beforeAuth.fragments.productFragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import invo8.meezan.mb.R


class ProductsAdapter(
    private val context: Context,
    private val productsList: ArrayList<ProductsModel>,
    private val productClickInterface: ProductClickInterface

) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.item_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productsModel: ProductsModel = productsList[position]

        Log.wtf("Adapter_List", "Size :: ${productsList.size} List :: $productsList")



        Glide.with(context).load(productsModel.icon)
            .into(holder.iconImage)

        holder.title.text = productsModel.title
        holder.description.text = productsModel.description


        holder.itemView.setOnClickListener {
            productClickInterface.onItemProductClick( productsModel)
        }


    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iconImage: ImageView
        var title: TextView
        var description: TextView

        init {

            iconImage = itemView.findViewById(R.id.item_icon)
            title = itemView.findViewById(R.id.item_title)
            description = itemView.findViewById(R.id.item_desc)
        }
    }

}