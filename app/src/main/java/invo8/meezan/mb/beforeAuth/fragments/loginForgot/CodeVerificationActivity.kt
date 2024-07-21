package invo8.meezan.mb.beforeAuth.fragments.loginForgot

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import invo8.meezan.mb.R
import invo8.meezan.mb.Utils.maskPhoneNumber
import invo8.meezan.mb.Utils.setClickableColoredTextNoUnderline
import invo8.meezan.mb.databinding.ActivityCodeVerificationBinding
import java.text.DecimalFormat
import java.text.NumberFormat

class CodeVerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCodeVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.detailAndNum.text =
            "${getString(R.string.passcode_has_been_sent)} ${maskPhoneNumber("03164841504")}"

        binding.detailNote.setClickableColoredTextNoUnderline(
            getString(R.string.passcode_note), "Note:",
            ContextCompat.getColor(this, R.color.red), false
        ) {}


        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.otpTimeResend.text =
                    "${getString(R.string.passcode_expires_in)} ${
                        (f.format(min)).toString() + ":" + f.format(
                            sec
                        )
                    }"

            }

            // When the task is over it will print 00:00:00 there
            override fun onFinish() {
                binding.otpTimeResend.text =
                    "${getString(R.string.passcode_expires_in)} 00:00"

            }
        }.start()
    }


}