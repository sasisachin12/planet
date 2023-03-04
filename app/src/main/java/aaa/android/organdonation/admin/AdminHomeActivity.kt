package aaa.android.organdonation.admin

import aaa.android.organdonation.R
import aaa.android.organdonation.databinding.ActivityAdminHomeBinding

import android.content.DialogInterface

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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


class AdminHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAdminHomeBinding
    private var isUpButton = false

    //lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarAdminHome.toolbar)

        //  navController = findNavController(R.id.nav_host_fragment_content_admin_home)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_admin_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarAdminHome.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            /* if (nd.id == nc.graph.startDestinationId) {
                 drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                 supportActionBar?.hide()
             } else {
                 supportActionBar?.show()
                 drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
             }*/
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.admin_home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_admin_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_admin_home)
        when (item.itemId) {
            R.id.nav_home -> {
                navController.navigate(R.id.adminHomeFragment)
            }
            R.id.nav_gallery -> {
                navController.navigate(R.id.adminDonorDetailsFragment)
            }
            R.id.nav_slideshow -> {
                navController.navigate(R.id.adminViewAllAccountHomeFragment)
            }
            R.id.nav_search_orgon -> {
                navController.navigate(R.id.userHomeFragment)
            }

            R.id.nav_notification -> {
                navController.navigate(R.id.adminNotificationFragment)
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