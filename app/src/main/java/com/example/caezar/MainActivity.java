package com.example.caezar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.caezar.fragments.ChartFragment;
import com.example.caezar.fragments.HomeFragment;
import com.example.caezar.fragments.NFTFragment;
import com.example.caezar.fragments.ProfileFragment;
import com.example.caezar.fragments.TokenFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;



public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private NFTFragment nftFragment;
    private TokenFragment tokenFragment;
    private ChartFragment chartFragment;
    private ProfileFragment profileFragment;

    ChipNavigationBar bottomMenu;
    FrameLayout container;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        bottomMenu = findViewById(R.id.bottom_menu);

        //called when activity called first time
        if (savedInstanceState == null) {
            homeFragment = homeFragment.newInstance("First param", "Second Param");
            nftFragment = nftFragment.newInstance("First param", "Second Param");
            tokenFragment = tokenFragment.newInstance("First param", "Second Param");
            chartFragment = chartFragment.newInstance("First param", "Second Param");
            profileFragment = profileFragment.newInstance("First param", "Second Param");

            bottomMenu.setItemSelected(R.id.home, true);
            //replaceFragment(new ExploreFragment());
            displayHomeFragment();


        }

        bottomMenu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switch (id){
                    case R.id.home:
                        displayHomeFragment();
                        break;
                    case R.id.nft:
                        displayNFTFragment();
                        break;
                    case R.id.chart:
                        displayChartFragment();
                        break;
                    case R.id.caesar:
                        displayTokenFragment();
                        break;
                    case R.id.profile:
                        displayProfileFragment();
                        break;
                }
            }

        });

        bottomMenu.showBadge(R.id.profile);
        bottomMenu.showBadge(R.id.nft, 32);



    }

    protected void displayHomeFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (homeFragment.isAdded()) { // if the fragment is already in container
            ft.show(homeFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.container, homeFragment);
        }
        // Hide fragment FavFragment
        if (chartFragment.isAdded()) { ft.hide(chartFragment); }
        // Hide fragment NFTFragment
        if (nftFragment.isAdded()) { ft.hide(nftFragment); }
        // Hide fragment NotifacFragment
        if (tokenFragment.isAdded()) { ft.hide(tokenFragment); }
        // Hide fragment ProfileFragment
        if (profileFragment.isAdded()) { ft.hide(profileFragment); }
        // Commit changes
        ft.commit();
    }

    protected void displayNFTFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (nftFragment.isAdded()) { // if the fragment is already in container
            ft.show(nftFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.container, nftFragment);
        }
        // Hide fragment ExploreFragment
        if (homeFragment.isAdded()) { ft.hide(homeFragment); }
        // Hide fragment NotifacFragment
        if (chartFragment.isAdded()) { ft.hide(chartFragment); }
        // Hide fragment TokenFragment
        if (tokenFragment.isAdded()) { ft.hide(tokenFragment); }
        // Hide fragment ProfileFragment
        if (profileFragment.isAdded()) { ft.hide(profileFragment); }
        // Commit changes
        ft.commit();
    }

    protected void displayChartFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (chartFragment.isAdded()) { // if the fragment is already in container
            ft.show(chartFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.container, chartFragment);
        }
        // Hide fragment FavFragment
        if (homeFragment.isAdded()) { ft.hide(homeFragment); }
        // Hide fragment FavFragment
        if (nftFragment.isAdded()) { ft.hide(nftFragment); }
        // Hide fragment ExploreFragment
        if (tokenFragment.isAdded()) { ft.hide(tokenFragment); }
        // Hide fragment ProfileFragment
        if (profileFragment.isAdded()) { ft.hide(profileFragment); }
        // Commit changes
        ft.commit();
    }

    protected void displayTokenFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (tokenFragment.isAdded()) { // if the fragment is already in container
            ft.show(tokenFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.container, tokenFragment);
        }
        // Hide fragment FavFragment
        if (homeFragment.isAdded()) { ft.hide(homeFragment); }
        // Hide fragment FavFragment
        if (chartFragment.isAdded()) { ft.hide(chartFragment); }
        // Hide fragment ExploreFragment
        if (nftFragment.isAdded()) { ft.hide(nftFragment); }
        // Hide fragment ProfileFragment
        if (profileFragment.isAdded()) { ft.hide(profileFragment); }
        // Commit changes
        ft.commit();
    }

    protected void displayProfileFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (profileFragment.isAdded()) { // if the fragment is already in container
            ft.show(profileFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.container, profileFragment);
        }
        // Hide fragment FavFragment
        if (homeFragment.isAdded()) { ft.hide(homeFragment); }
        // Hide fragment ExploreFragment
        if (chartFragment.isAdded()) { ft.hide(chartFragment); }
        // Hide fragment ExploreFragment
        if (tokenFragment.isAdded()) { ft.hide(tokenFragment); }
        // Hide fragment ProfileFragment
        if (nftFragment.isAdded()) { ft.hide(nftFragment); }
        // Commit changes
        ft.commit();
    }




}