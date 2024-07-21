package invo8.meezan.mb.beforeAuth.fragments.qiblaFragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import invo8.meezan.mb.R
import invo8.meezan.mb.Utils
import invo8.meezan.mb.Utils.LOCATION_PERMISSION_REQUEST_CODE
import invo8.meezan.mb.beforeAuth.BeforeAuthMainActivity
import invo8.meezan.mb.common.SwipeButton
import invo8.meezan.mb.databinding.FragmentQiblaBinding
import io.github.derysudrajat.compassqibla.CompassQibla


class QiblaFragment : Fragment(), SwipeButtonInterface {

    private lateinit var binding: FragmentQiblaBinding
    private var currentCompassDegree = 0f
    private var currentNeedleDegree = 0f

    lateinit var activity: BeforeAuthMainActivity

    lateinit var dialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQiblaBinding.inflate(layoutInflater, container, false)
        activity = context as BeforeAuthMainActivity
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Utils.hasLocationPermission(requireContext())) {
            getQiblaDirections()
        } else {
            Utils.requestLocationPermission(requireContext(),requireActivity(),this)
        }


    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                getQiblaDirections()
            } else {
                Utils.showPermissionRationaleDialog(requireContext(),this)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun getQiblaDirections() {
        CompassQibla.Builder(activity).onGetLocationAddress { address ->
            binding.locationAddress.isSelected = true
            binding.locationAddress.text = address.getAddressLine(0)
            binding.locationCoordinates.text =
                "Lat ${String.format("%.4f", address.latitude)} / Lng ${
                    String.format(
                        "%.4f",
                        address.longitude
                    )
                }"
        }.onDirectionChangeListener { qiblaDirection ->
            binding.locationDirection.text = if (qiblaDirection.isFacingQibla) "You're Facing Qibla"
            else "Heading: ${qiblaDirection.needleAngle.toInt()} degrees°"

            val rotateNeedle = RotateAnimation(
                currentNeedleDegree, qiblaDirection.needleAngle, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f
            ).apply {
                duration = 1000
            }
            currentNeedleDegree = qiblaDirection.needleAngle

            binding.qiblaDirection.startAnimation(rotateNeedle)
        }.build()
    }

    override fun onSwipeEndButton(isSwiped: Boolean) {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onReleaseButton(isClicked: Boolean) {
//        dialog.dismiss()
    }


}