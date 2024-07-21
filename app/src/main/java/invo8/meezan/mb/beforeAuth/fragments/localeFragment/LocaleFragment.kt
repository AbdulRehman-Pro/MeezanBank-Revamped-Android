package invo8.meezan.mb.beforeAuth.fragments.localeFragment

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.chip.Chip
import com.rehman.utilities.Utils.showToast
import invo8.meezan.mb.R
import invo8.meezan.mb.Utils
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.interfaces.LocationDataPassingFragmentCallBack
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.modelClasses.CityLocations
import invo8.meezan.mb.beforeAuth.fragments.qiblaFragment.SwipeButtonInterface
import invo8.meezan.mb.databinding.FragmentLocaleBinding
import invo8.meezan.mb.tinyDB.TinyDB
import java.util.ArrayList


class LocaleFragment : Fragment(), SwipeButtonInterface, LocationDataPassingFragmentCallBack {

    private lateinit var binding: FragmentLocaleBinding
    private lateinit var tinyDB: TinyDB
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocaleBinding.inflate(layoutInflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tinyDB = TinyDB(requireContext())

        val citiesList = tinyDB.getListObject(
            "Cities_List", CityLocations::class.java
        ) as ArrayList<CityLocations>


        binding.locationCityButton.setOnClickListener {
            val fragment = CityLocationSelectionBottomSheet(citiesList)
            fragment.show(requireActivity().supportFragmentManager, "locationCity")
        }

        if (Utils.hasLocationPermission(requireContext())) {
            getUserLocation()
        } else {
            Utils.requestLocationPermission(requireContext(), requireActivity(), this)
        }

        CityLocationSelectionBottomSheet.locationData.observe(requireActivity()) { cityLocation ->
            binding.locationCity.text = cityLocation.name
        }

// Set an initial selection listener for the "Nearby" chip
        binding.nearbyChip.isChecked = true
        binding.branchChip.isChecked = true


        binding.nearbyChip.setOnCheckedChangeListener { _, isChecked ->
            binding.nearbyChip.isChecked = true
            showBottomDialog()

        }




        binding.chipGroupChoice.setOnCheckedStateChangeListener { group, checkedIds ->

            val selectedChips = mutableSetOf<Chip>()

            // Iterate through the list of checkedIds
            checkedIds.forEach { chipId ->
                val chip = group.findViewById<Chip>(chipId)
                if (chip.isChecked) {
                    selectedChips.add(chip)
                }
            }

//            binding.nearbyChip.isChecked = true
//            selectedChips.add(binding.nearbyChip)
//
//            // Check if at least two chips, including "Nearby," are selected
//            if (selectedChips.size < 2) {
//                binding.nearbyChip.isChecked = true
//            }
//
//            // Track the most recently selected chip
            var lastSelectedChip: Chip? = null
            selectedChips.forEach {
                if (it.isChecked) {
                    lastSelectedChip = it
                }
                Log.wtf("CHIPS_FILTER", "Chips ::: Size ${selectedChips.size} :: text ${it.text}")
            }


            // Allow the user to deselect the most recently selected chip
            lastSelectedChip?.setOnCheckedChangeListener { chip, _ ->

                if (selectedChips.size > 2 || !selectedChips.contains(binding.nearbyChip)) {
                    chip.isChecked = false
                    selectedChips.add(chip as Chip)
                    Log.wtf("CHIPS_FILTER", "Last Chips ::: ${chip!!.text}")
                }

                chip.isChecked = true
                selectedChips.add(chip as Chip)
            }
        }

    }

    private fun showBottomDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_bottom_sorting)
//        dialog.setCancelable(false)

        val radioGroup = dialog.findViewById<RadioGroup>(R.id.radioGroupSort)

        radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            val radioButton = radioGroup.findViewById<RadioButton>(id)
            showToast("${radioButton.text}")

            Handler().postDelayed({
                dialog.dismiss()
            }, 600)
        }


        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // dialog.getWindow().getAttributes().dimAmount = 0.0f;
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnim
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    val address = com.rehman.utilities.Utils.getAddressFromLatLng(
                        requireContext(),
                        latitude,
                        longitude
                    )
                    binding.locationCity.text = address.locality

                    Log.wtf(
                        "Current_Location",
                        "Current Location Lat:: $latitude , Long:: $longitude"
                    )
                }
            }
            .addOnFailureListener {
                Log.wtf("Current_Location", "Error ${it.localizedMessage}")
            }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Utils.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                getUserLocation()
            } else {
                Utils.showPermissionRationaleDialog(requireContext(), this)
            }
        }
    }


    override fun onSwipeEndButton(isSwiped: Boolean) {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            Utils.LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onReleaseButton(isClicked: Boolean) {
//        dialog.dismiss()
    }


    override fun onDataPassed(cityLocation: CityLocations) {
        showToast("Fragment ${cityLocation.name}")
    }

}