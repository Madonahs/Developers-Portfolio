/*Copyright (c) 2018 Madona Syombua

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */

package com.madonasyombua.growwithgoogleteamproject.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.User
import com.madonasyombua.growwithgoogleteamproject.databinding.ActivityMainBinding
import com.madonasyombua.growwithgoogleteamproject.ui.SharedPref
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.*
import com.madonasyombua.growwithgoogleteamproject.ui.login.LoginStatusManager
import com.madonasyombua.growwithgoogleteamproject.util.Constant
import kotlinx.android.synthetic.main.activity_add_feeds.toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*


class MainActivity : AppCompatActivity(), FeedsFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener, OnSharedPreferenceChangeListener, PostFeedFragment.OnFragmentInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener  {
    private var fragment: Fragment? = null
    private lateinit var sharedPref: SharedPref
    private var prevState = false
    private var user: User? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPref(this)
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        prevState = sharedPref.loadNightModeState()
        currentUser = FirebaseAuth.getInstance().currentUser
        setCorrectTheme()

        setSupportActionBar(toolbar)
        setupNavDrawer()
        fragment = supportFragmentManager.findFragmentByTag(TAG)
        if (fragment == null) {
            fragment = FeedsFragment()
        }
        binding.navigation.setOnNavigationItemSelectedListener(this)
        val transaction = supportFragmentManager.beginTransaction()
        fragment?.let { transaction.replace(R.id.content, it)}
        transaction.commit()

        val data = intent.getBundleExtra(Constant.USER)
        user = if (data != null) User.build(data) else
            User("this guy", "thisguy@devs.com", "000000")
    }

    private fun setupNavDrawer() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
             toolbar.setNavigationIcon(R.drawable.ic_menu)
             toolbar.setNavigationOnClickListener { drawer_container.openDrawer(GravityCompat.START) }
             nav_view.setNavigationItemSelectedListener(this)
             nav_view.itemIconTintList = null
        }
    }

    private fun setDrawerHeader() {
        drawer_header_user_image.setImageResource(R.drawable.avater)
        drawer_header_user_name.text = currentUser?.displayName
        drawer_header_user_profession.text = getString(R.string.dummy_position)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val fragments = supportFragmentManager.fragments
        var isOnStack = false
        when (item.itemId) {
            R.id.action_feeds -> {
                fragment = FeedsFragment()
                for (frag in fragments) {
                    /* Check if fragment is on stack*/
                    if (frag is FeedsFragment) {
                        isOnStack = true
                        break
                    }
                }
            }
            R.id.action_interests -> {
                fragment = InterestFragment()
                for (frag in fragments) {
                    /* Check if fragment is on stack*/
                    if (frag is InterestFragment) {
                        isOnStack = true
                        break
                    }
                }
            }
            R.id.action_projects -> {
                fragment = ProjectsFragment()
                for (frag in fragments) {
                    /* Check if fragment is on stack*/
                    if (frag is ProjectsFragment) {
                        isOnStack = true
                        break
                    }
                }
            }
            R.id.action_profile -> {
                fragment = ProfileFragment.newInstance(user!!)
                for (frag in fragments) {
                    /* Check if fragment is on stack*/
                    if (frag is ProfileFragment) {
                        isOnStack = true
                        break
                    }
                }
            }
        }
     if (!isOnStack) fragment?.let {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, it, TAG)
                .addToBackStack(null)
                .commit()
    }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(Constant.USER, user?.bundleUp())
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        user = savedInstanceState.getBundle(Constant.USER)?.let { User.build(it) }
    }

    override fun onBackPressed() {
        if (drawer_container.isDrawerOpen(GravityCompat.START)) {
            drawer_container.closeDrawers()
        } else {

            if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStackImmediate() else moveTaskToBack(true)
        }
    }

   /* override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val intent: Intent
        when (item.itemId) {
            R.id.manage_profile -> {
                intent = Intent(this, ManageProfileActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.settings -> {
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.about -> {
                intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.help -> {
                intent = Intent(this, HelpActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.logout -> {

                LoginStatusManager.storeLoginStatus(this, false)
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return false
    }*/


    override fun onFragmentInteraction(uri: Uri?) {
    }

    override fun onDialogSubmit() {}
    override fun onDialogSubmit(dialog: PostFeedFragment?, text: String?, fileName: String?) {
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "enable_dark_mode") {
            sharedPref.setNightModeState(sharedPreferences.getBoolean(key, false))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun setCorrectTheme() {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPref.setNightModeState(sharedPreference.getBoolean("enable_dark_mode", false))
        sharedPreference.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (prevState != sharedPref.loadNightModeState()) {
            startActivity(Intent(this, this.javaClass))
            finish()
        }
    }


    companion object {
        private val TAG = MainActivity::class.java.name

        @JvmStatic
        fun dpToPixels(dp: Int, view: View): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), view.resources.displayMetrics)
        }
    }
}
