package invo8.meezan.mb.beforeAuth.fragments.registration.pagerFragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rehman.utilities.Utils.showToast
import invo8.meezan.mb.R
import invo8.meezan.mb.beforeAuth.fragments.registration.interfaces.ViewPagerInterface
import invo8.meezan.mb.databinding.FragmentUserDetailBinding


class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailBinding
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
        binding = FragmentUserDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextScreen.setOnClickListener {
            viewPagerInterface.onButtonClick(1)
        }

    }

}