package invo8.meezan.mb.beforeAuth.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.airbnb.lottie.LottieAnimationView
import com.rehman.utilities.Utils.showToast
import invo8.meezan.mb.R
import invo8.meezan.mb.Utils
import invo8.meezan.mb.beforeAuth.fragments.loginForgot.ForgotUsernameAndPassBottomSheet
import invo8.meezan.mb.beforeAuth.fragments.registration.RegistrationActivity
import invo8.meezan.mb.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.animationView.setMinAndMaxProgress(0.0f, 0.85f)

        binding.userRegister.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireActivity(),
                    RegistrationActivity::class.java
                )
            )
            requireActivity().overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            requireActivity().finish()
        }

        binding.touchID.setOnClickListener {
            showDialog()
        }

        binding.forgotUsername.setOnClickListener {
            val fragment = ForgotUsernameAndPassBottomSheet("Forgot Username")
            fragment.show(requireActivity().supportFragmentManager, "forgotUser")
        }

        binding.forgotPassword.setOnClickListener {
            val fragment = ForgotUsernameAndPassBottomSheet("Forgot Password")
            fragment.show(requireActivity().supportFragmentManager, "forgotPass")
        }

    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_fingerprint_info)
        dialog.setCancelable(true)

        val animationView = dialog.findViewById<LottieAnimationView>(R.id.animationView)

        animationView.setMinAndMaxProgress(0.0f, 0.85f)

        if (dialog.isShowing) {
            binding.animationView.pauseAnimation()
        } else {
            binding.animationView.playAnimation()
        }


        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
    }

    override fun onResume() {
        super.onResume()
        Utils.setStatusBarColor(requireActivity(), R.color.purple, false)
    }



}