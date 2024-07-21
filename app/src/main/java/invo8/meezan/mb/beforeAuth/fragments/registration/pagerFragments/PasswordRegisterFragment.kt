package invo8.meezan.mb.beforeAuth.fragments.registration.pagerFragments

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import invo8.meezan.mb.R
import invo8.meezan.mb.Utils
import invo8.meezan.mb.beforeAuth.fragments.registration.interfaces.ViewPagerInterface
import invo8.meezan.mb.databinding.FragmentPasswordRegisterBinding
import nu.aaro.gustav.passwordstrengthmeter.PasswordStrengthLevel
import java.text.DecimalFormat
import java.text.NumberFormat


class PasswordRegisterFragment : Fragment() {
    private lateinit var binding: FragmentPasswordRegisterBinding
    private lateinit var viewPagerInterface: ViewPagerInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewPagerInterface = context as ViewPagerInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPasswordRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailAndNum.text =
            "${getString(R.string.passcode_has_been_sent)} ${Utils.maskPhoneNumber("03164841504")}"

        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.countdownTimer.text = (f.format(min)).toString() + ":" + f.format(sec)


            }

            // When the task is over it will print 00:00:00 there
            override fun onFinish() {
                binding.countdownTimer.text = "00:00"

            }
        }.start()


        binding.passwordInputMeter.setEditText(binding.password.editText)
        binding.passwordInputMeter.setStrengthLevels(
            arrayOf(
                PasswordStrengthLevel("Level 1", android.R.color.holo_red_light),
                PasswordStrengthLevel("Level 3", android.R.color.holo_orange_light),
                PasswordStrengthLevel("Level 5", android.R.color.holo_green_dark)
            )
        )

        binding.passwordInputMeter.setAnimationDuration(500)
        binding.passwordInputMeter.setShowStrengthIndicator(true)
        binding.passwordInputMeter.setShowStrengthLabel(false)


        binding.nextScreen.setOnClickListener {
            viewPagerInterface.onButtonClick(2)
        }

    }

}