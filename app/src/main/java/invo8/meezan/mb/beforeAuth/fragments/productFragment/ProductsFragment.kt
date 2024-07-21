package invo8.meezan.mb.beforeAuth.fragments.productFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import invo8.meezan.mb.R
import invo8.meezan.mb.beforeAuth.fragments.productFragment.productDetails.ProductDetailsBottomSheet
import invo8.meezan.mb.databinding.FragmentProductsBinding


class ProductsFragment : Fragment(), ProductClickInterface {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var productList: ArrayList<ProductsModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productList = ArrayList()
        binding.productsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.productsRV.hasFixedSize()


        productList.add(
            ProductsModel(
                R.drawable.ic_kaaba_line,
                "Hajj Application",
                "Shariah compliant financing for your Holy journey",
                "labbaik-travel-asaan"
            )
        )
        productList.add(
            ProductsModel(
                R.drawable.ic_car,
                "Car Ijarah",
                "Shariah compliant car financing",
                "car-ijarah"
            )
        )
        productList.add(
            ProductsModel(
                R.drawable.ic_bike,
                "Bike Ijarah",
                "Shariah compliant motorcycle financing",
                "apni-bike"
            )
        )
        productList.add(
            ProductsModel(
                R.drawable.ic_electronics,
                "Consumer Ease",
                "Shariah compliant, hassle-free financing for consumer products",
                "consumer-ease"
            )
        )
        productList.add(
            ProductsModel(
                R.drawable.ic_home_line,
                "Easy Home",
                "Shariah compliant financing to purchase, build or renovate your home",
                "easy-home"
            )
        )
        productList.add(
            ProductsModel(
                R.drawable.ic_card_line,
                "Debit Cards",
                "Our debit cards gives you access to ATM and outlets worldwide",
                "#premiumdebitcard"
            )
        )
        productList.add(
            ProductsModel(
                R.drawable.ic_sms,
                "SMS Alerts",
                "Stay updated constantly on your account activities",
                "wp-content/themes/mbl/downloads/Services-Form.pdf"
            )
        )
        productList.add(
            ProductsModel(
                R.drawable.ic_protection_umbrella,
                "Meezan Kafalah",
                "Give your dreams the protection of savings and Takaful",
                "meezan-kafalah"
            )
        )

        binding.productsRV.adapter = ProductsAdapter(requireContext(), productList, this)

    }

    override fun onItemProductClick(productsModel: ProductsModel) {

        val fragment = ProductDetailsBottomSheet(productsModel)
        fragment.show(requireActivity().supportFragmentManager, "productDetails")

    }


}