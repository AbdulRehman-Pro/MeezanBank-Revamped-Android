package invo8.meezan.mb.beforeAuth.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import invo8.meezan.mb.databinding.FragmentHomeRemittanceBinding
import test.jinesh.captchaimageviewlib.CaptchaImageView


class HomeRemittanceFragment : Fragment() {
    private lateinit var binding: FragmentHomeRemittanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeRemittanceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reCVaptchaImage.setCaptchaType(CaptchaImageView.CaptchaGenerator.BOTH)
        binding.reCVaptchaImage.setIsDotNeeded(true)
        Handler(Looper.myLooper()!!).postDelayed({
            binding.reCVaptchaImage.regenerate()
        }, 500)
        binding.refreshCaptcha.setOnClickListener {
            binding.reCVaptchaImage.regenerate()
        }
        binding.sendRequest.setOnClickListener(View.OnClickListener {
            if (binding.reCaptcha.editText!!.text.toString()
                    .trim() == binding.reCVaptchaImage.captchaCode
            ) {
                Toast.makeText(requireContext(), "Matched", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Not Matching", Toast.LENGTH_SHORT).show()
            }
        })
    }

}