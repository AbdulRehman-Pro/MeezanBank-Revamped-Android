package invo8.meezan.mb.beforeAuth

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import invo8.meezan.mb.R
import invo8.meezan.mb.Utils
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.LocaleFragment
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.interfaces.LocationDataPassingActivityCallBack
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.interfaces.LocationDataPassingFragmentCallBack
import invo8.meezan.mb.beforeAuth.fragments.localeFragment.modelClasses.CityLocations
import invo8.meezan.mb.databinding.ActivityBeforeAuthMainBinding


class BeforeAuthMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeforeAuthMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    companion object {

        val isDrawerOpen = MutableLiveData<Boolean>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeforeAuthMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        setupNavigationDrawer()
        setupNavController()
        binding.navView.menu.getItem(7).setOnMenuItemClickListener {
            makePhoneCall(this, "+92 21 111 331 331")
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }

    fun makePhoneCall(activity: AppCompatActivity, phoneNumber: String) {
        val permission = Manifest.permission.CALL_PHONE
        if (ContextCompat.checkSelfPermission(
                activity, permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$phoneNumber")
            activity.startActivity(intent)
        } else {
            // Request the CALL_PHONE permission if not granted
            ActivityCompat.requestPermissions(activity, arrayOf(permission), 1)
        }
    }

    private fun setupNavigationDrawer() {
        binding.drawerLayout.setScrimColor(getColor(android.R.color.transparent))
        binding.drawerLayout.drawerElevation = 0f

        val actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarHome.toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX: Float = drawerView.width * slideOffset
                binding.appBarHome.homeLayout.translationX = slideX

                // Calculate the scale factor based on the slideOffset
                val scaleFactor: Float = 1 - slideOffset * 0.25f


                // Apply scaleX to homeLayout for resizing effect
                binding.appBarHome.homeLayout.scaleX = scaleFactor
                binding.appBarHome.homeLayout.scaleY = scaleFactor
                binding.appBarHome.homeLayout.radius = slideOffset * 100
                binding.appBarHome.homeLayout.cardElevation = slideOffset * 50
                binding.appBarHome.homeLayout.elevation = slideOffset * 50

                binding.navView.alpha = slideOffset


            }


            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                isDrawerOpen.value = true
                Utils.setStatusBarColor(this@BeforeAuthMainActivity, R.color.white, true)

            }

            override fun onDrawerStateChanged(newState: Int) {
                super.onDrawerStateChanged(newState)
                Log.wtf("Drawer", "State changes $newState")
                if (newState == 2 && navController.currentDestination!!.id == R.id.nav_login) {
                    Utils.setStatusBarColor(this@BeforeAuthMainActivity, R.color.purple, false)
                }
            }
        }




        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }


    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_dashboard) as NavHostFragment
        navController = navHostFragment.navController


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_login,
                R.id.nav_products,
                R.id.nav_locale,
                R.id.nav_discounts,
                R.id.nav_qibla,
                R.id.nav_hru,
                R.id.nav_guide,
                R.id.nav_faq
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)



        navController.addOnDestinationChangedListener { _, destination, _ ->

            var titleResId = ""


            when (destination.id) {
                R.id.nav_login -> {
                    titleResId = ""
                    changeToolbarIcon(titleResId)
                }

                R.id.nav_products -> {
                    titleResId = "Products"
                    changeToolbarIcon(titleResId)
                }

                R.id.nav_locale -> {
                    titleResId = "Locale"
                    changeToolbarIcon(titleResId)
                }

                R.id.nav_discounts -> {
                    titleResId = "Discounts"
                    changeToolbarIcon(titleResId)
                }

                R.id.nav_qibla -> {
                    titleResId = "Qibla"
                    changeToolbarIcon(titleResId)
                }

                R.id.nav_hru -> {
                    titleResId = "HRU"
                    changeToolbarIcon(titleResId)

                }

                R.id.nav_guide -> {
                    titleResId = "Guide"
                    changeToolbarIcon(titleResId)
                }

                R.id.nav_faq -> {
                    titleResId = "FAQ's"
                    changeToolbarIcon(titleResId)
                }

            }


            binding.appBarHome.toolbar.title = titleResId

            binding.drawerLayout.closeDrawer(GravityCompat.START)


        }


    }

    private fun changeToolbarIcon(titleResId: String) {

        if (titleResId == "") {
            var drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_menu_white, null)
            drawable = DrawableCompat.wrap(drawable!!)
            supportActionBar!!.setHomeAsUpIndicator(drawable)
            Utils.setStatusBarColor(this, R.color.purple, false)

        } else {
            var drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_menu, null)
            drawable = DrawableCompat.wrap(drawable!!)
            supportActionBar!!.setHomeAsUpIndicator(drawable)
            Utils.setStatusBarColor(this, R.color.white, true)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {

            super.onBackPressed()
        }
    }


}