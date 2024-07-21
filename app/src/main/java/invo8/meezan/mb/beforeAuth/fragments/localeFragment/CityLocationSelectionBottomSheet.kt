package invo8.meezan.mb.beforeAuth.fragments.localeFragment


import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rehman.utilities.Utils.showToast
import invo8.meezan.mb.R
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.interfaces.LocationSelectCallBack
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.modelClasses.CityLocations
import invo8.meezan.mb.databinding.CityLocationSelectionBottomSheetBinding
import invo8.meezan.mb.tinyDB.TinyDB
import java.util.Locale


class CityLocationSelectionBottomSheet(private val citiesList: ArrayList<CityLocations>) :
    BottomSheetDialogFragment(), LocationSelectCallBack {

    companion object {
        var locationData: MutableLiveData<CityLocations> = MutableLiveData()
    }

    private var isExpanded = false

    private lateinit var binding: CityLocationSelectionBottomSheetBinding
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var bottomSheetDialog1: BottomSheetDialog? = null
    private lateinit var popularCitiesAdapter: PopularCitiesAdapter
    private lateinit var popularCityLocationsList: ArrayList<CityLocations>


    private val itemsPerPage = 10 // Adjust this as needed.

    private var currentPage = 0
    private var isLoading = false
    private lateinit var otherCityLocationsList: ArrayList<CityLocations>
    private lateinit var paginatedList :ArrayList<CityLocations>
    private lateinit var otherCitiesAdapter: OtherCitiesAdapter



    private lateinit var tinyDB: TinyDB


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CityLocationSelectionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tinyDB = TinyDB(requireContext())
        binding.searchCityET.hint = "Search From ${citiesList.size} Cities..."

        otherCityLocationsList = ArrayList()
        popularCityLocationsList = ArrayList()
        paginatedList = ArrayList()

        popularCityLocationsList = citiesList.filter {
            it.name!!.lowercase() in listOf(
                "Karachi".lowercase(),
                "Lahore".lowercase(),
                "Islamabad".lowercase(),
                "Faisalabad".lowercase(),
                "Multan".lowercase(),
                "Peshawar".lowercase(),
                "Gujranwala".lowercase(),
                "Rawalpindi".lowercase()
            )
        } as ArrayList<CityLocations>

        otherCityLocationsList = citiesList.filterNot {
            it.name!!.lowercase() in listOf(
                "Karachi".lowercase(),
                "Lahore".lowercase(),
                "Islamabad".lowercase(),
                "Faisalabad".lowercase(),
                "Multan".lowercase(),
                "Peshawar".lowercase(),
                "Gujranwala".lowercase(),
                "Rawalpindi".lowercase()
            )
        } as ArrayList<CityLocations>


        Log.wtf("PopularCity_List", popularCityLocationsList.toString())
        Log.wtf("OtherCity_List", otherCityLocationsList.toString())
        binding.otherCityRV.layoutManager = LinearLayoutManager(requireContext())
        binding.popularCityRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        popularCitiesAdapter = PopularCitiesAdapter(
            requireContext(),
            popularCityLocationsList.sortedBy { it.name },
            this
        )
        otherCitiesAdapter = OtherCitiesAdapter(requireContext(), otherCityLocationsList, this)


        binding.otherCityRV.adapter = otherCitiesAdapter

//        loadNextPage()

        binding.popularCityRV.adapter = popularCitiesAdapter


        binding.searchCityET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                filterOtherCities(searchText)
                filterPopularCities(searchText)
            }

        })


//        // Implement pagination trigger (e.g., when user scrolls to the end).
//        binding.otherCityRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val visibleItemCount = layoutManager.childCount
//                val totalItemCount = layoutManager.itemCount
//                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//
//                if (!isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount - 5 && firstVisibleItemPosition >= 0) {
//                    loadNextPage()
//                }
//            }
//        })

    }

//    private fun loadNextPage() {
//        isLoading = true
//        val startIndex = currentPage * itemsPerPage
//        val endIndex = minOf((currentPage + 1) * itemsPerPage, otherCityLocationsList.size)
//        if (startIndex < endIndex) {
//            val nextPageItems = otherCityLocationsList.subList(startIndex, endIndex)
//            paginatedList.addAll(nextPageItems)
//            otherCitiesAdapter.notifyDataSetChanged()
//            currentPage++
//        }
//        isLoading = false
//    }

    private fun filterPopularCities(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<CityLocations>()

        // running a for loop to compare elements.
        for (item in popularCityLocationsList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name!!.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }

        popularCitiesAdapter.filterList(filteredlist)

    }

    private fun filterOtherCities(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<CityLocations>()

        // running a for loop to compare elements.
        for (item in otherCityLocationsList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name!!.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }

            otherCitiesAdapter.filterList(filteredlist)

    }


    //

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog1 = BottomSheetDialog(requireContext(), R.style.bottomSheetStyle)
        bottomSheetDialog1!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        bottomSheetDialog1!!.window!!.attributes.windowAnimations = R.style.DialogAnim


        bottomSheetDialog1!!.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it1 ->
                bottomSheetBehavior = BottomSheetBehavior.from(it1)
                setupFullHeight(it1)
                bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

                bottomSheetBehavior!!.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

                bottomSheetBehavior!!.setBottomSheetCallback(object : BottomSheetCallback() {
                    override fun onStateChanged(view: View, i: Int) {
                        isExpanded = i == BottomSheetBehavior.STATE_EXPANDED
                        if (isExpanded) {
                            // Switch to square corners when fully expanded
//                            dialog!!.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
                        } else {
                            // Switch back to rounded corners when not fully expanded
//                            dialog.window?.statusBarColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
                        }

                    }

                    override fun onSlide(view: View, v: Float) {

                    }
                })
            }
        }
        return bottomSheetDialog1!!
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onItemClick(cityLocations: CityLocations) {
        dialog!!.dismiss()
        locationData.value = cityLocations

    }
}