package invo8.meezan.mb.beforeAuth.fragments.loginForgot



import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import invo8.meezan.mb.R
import invo8.meezan.mb.databinding.ForgotUsernameAndPassBottomSheetBinding


class ForgotUsernameAndPassBottomSheet(private var screen: String) : BottomSheetDialogFragment() {

    private var isExpanded = false

    private lateinit var binding: ForgotUsernameAndPassBottomSheetBinding
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var bottomSheetDialog1: BottomSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ForgotUsernameAndPassBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleName.text = screen

        binding.sendRequest.setOnClickListener {
            startActivity(Intent(requireContext(),CodeVerificationActivity::class.java))
            dialog!!.dismiss()
        }


    }






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
}