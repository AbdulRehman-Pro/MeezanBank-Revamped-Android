package invo8.meezan.mb.beforeAuth.fragments.registration

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rehman.utilities.Utils.showToast
import invo8.meezan.mb.R
import invo8.meezan.mb.beforeAuth.BeforeAuthMainActivity
import invo8.meezan.mb.beforeAuth.fragments.registration.interfaces.ViewPagerInterface
import invo8.meezan.mb.beforeAuth.fragments.registration.pagerAdpter.RegisterPagerAdapter
import invo8.meezan.mb.beforeAuth.fragments.registration.pagerFragments.AgreementFragment
import invo8.meezan.mb.beforeAuth.fragments.registration.pagerFragments.PasswordRegisterFragment
import invo8.meezan.mb.beforeAuth.fragments.registration.pagerFragments.UserDetailFragment
import invo8.meezan.mb.databinding.ActivityRegisterationBinding


class RegistrationActivity : AppCompatActivity(), ViewPagerInterface {
    private lateinit var binding: ActivityRegisterationBinding
    private lateinit var viewPagerAdapter: RegisterPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val fragments: MutableList<Fragment> = ArrayList()


        fragments.add(UserDetailFragment())
        fragments.add(PasswordRegisterFragment())
        fragments.add(AgreementFragment())

        viewPagerAdapter = RegisterPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(UserDetailFragment(), "User Details")
        viewPagerAdapter.addFragment(PasswordRegisterFragment(), "User Password")
        viewPagerAdapter.addFragment(AgreementFragment(), "Policy Agreement")
        binding.detailPager.adapter = viewPagerAdapter


        binding.stepIndicator.setViewPager(binding.detailPager)


    }




    override fun onBackPressed() {
        startActivity(
            Intent(
                this,
                BeforeAuthMainActivity::class.java
            )
        )
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
        return
    }

    override fun onButtonClick(position: Int) {
        binding.detailPager.currentItem = position
    }


}