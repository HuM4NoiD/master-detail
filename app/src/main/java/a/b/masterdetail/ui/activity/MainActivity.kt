package a.b.masterdetail.ui.activity

import a.b.masterdetail.R
import a.b.masterdetail.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.mainFragContainer) as NavHostFragment

        binding.bottomNavBar.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
            when (destination.id) {
                R.id.detailFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    binding.bottomNavBar.visibility = View.GONE
                }
                else -> {
                    if (bottomNavBar.visibility == View.GONE) {
                        bottomNavBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}