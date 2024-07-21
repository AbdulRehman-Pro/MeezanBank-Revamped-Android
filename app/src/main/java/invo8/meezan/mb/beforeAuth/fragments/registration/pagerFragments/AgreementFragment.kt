package invo8.meezan.mb.beforeAuth.fragments.registration.pagerFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import invo8.meezan.mb.R
import invo8.meezan.mb.beforeAuth.BeforeAuthMainActivity
import invo8.meezan.mb.databinding.FragmentAgreementBinding


class AgreementFragment : Fragment() {

    private lateinit var binding:FragmentAgreementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment'
        binding = FragmentAgreementBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.agreeAndContinue.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    BeforeAuthMainActivity::class.java
                )
            )
            requireActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            requireActivity().finish()
        }

    }

}