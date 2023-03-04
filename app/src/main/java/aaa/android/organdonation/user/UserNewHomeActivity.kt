package aaa.android.organdonation.user

import aaa.android.organdonation.R
import aaa.android.organdonation.databinding.ActivityAdminHomeBinding
import aaa.android.organdonation.databinding.ActivityUserHomeBinding

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_admin_home.*


class UserNewHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityUserHomeBinding
    private var isUpButton = false

    //lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarUserHome.toolbar)

        //  navController = findNavController(R.id.nav_host_fragment_content_admin_home)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_user_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_search, R.id.nav_add, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarUserHome.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.admin_home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_user_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_user_home)
        when (item.itemId) {
            R.id.nav_search -> {
                navController.navigate(R.id.userHomeFragment)
            }
            R.id.nav_add -> {
                navController.navigate(R.id.userDonorFormFragment)
            }
            R.id.nav_donation_statistics -> {
                navController.navigate(R.id.userStatisticsFragment)
            }
            R.id.nav_donation_donation_status -> {
                navController.navigate(R.id.userHomeStatusFragment)
            }

            R.id.nav_donation_faq -> {
                navController.navigate(R.id.userFAQFragment)
            }
            R.id.nav_health_proxy -> {
                navController.navigate(R.id.userHealthyProxyFragment)
            }


            R.id.nav_logout -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                        DialogInterface.OnClickListener { dialog, id -> this.finish() })
                    .setNegativeButton("No",
                        DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
                val alert: AlertDialog = builder.create()
                alert.show()

            }
            else -> {

            }
        }
        binding.drawerLayout.close()
        return false

    }

    fun showHamburIcon() {
        supportActionBar?.show()
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    /* fun useUpButton() {
         supportActionBar?.setHomeAsUpIndicator(
             androidx.appcompat.R.drawable.abc_ic_ab_back_material
         )
         binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
         isUpButton = true
     }
 */
    // Enable the drawer and disable up button
    /* private fun useHamburgerButton() {
         binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
         isUpButton = false
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return if (isUpButton) {
             val navController = findNavController(R.id.nav_host_fragment_content_admin_home)
             useHamburgerButton()
             true
         } else super.onOptionsItemSelected(item)
     }*/
}