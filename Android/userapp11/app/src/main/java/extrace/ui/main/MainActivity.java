package extrace.ui.main;

import java.util.Locale;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import extrace.ui.domain.ExpressListFragment;
import extrace.ui.domain.ExpressListFragment.OnFragmentInteractionListener;
import extrace.ui.domain.PackageDeliverActivity;
import extrace.ui.misc.MapActivity;
import extrace.ui.misc.Qrcode;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener,OnFragmentInteractionListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Resources r = getResources();                     //背景
        Drawable myDrawable = r.getDrawable(R.drawable.bg);
        Drawable myDrawable2 = r.getDrawable(R.drawable.bg);
        actionBar.setBackgroundDrawable(myDrawable2);      //actonBar背景
        actionBar.setStackedBackgroundDrawable(myDrawable);//tab栏背景


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager =  (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_my:
                Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SetActivity.class);
                startActivity(intent);
                break;
            case R.id.action_my_location:
                Intent intent1 = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_logout:
                Intent intent2 = new Intent(MainActivity.this, PackageDeliverActivity.class);
                startActivity(intent2);
                break;
            case R.id.myscaner:
                Intent intent3 = new Intent(MainActivity.this, Qrcode.class);
                startActivity(intent3);
                break;
        case R.id.action_settings:
    		return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
	        switch(position){
        	case 0:
                return MainFragment.newInstance();
        	case 1:
                return ExpressListFragment.newInstance("ExDLV");	//派送快件
        	case 2:
                return ExpressListFragment.newInstance("ExRCV");	//揽收快件
        	case 3:
                return ExpressListFragment.newInstance("ExTAN");	//转运快件
        	}
//            switch(position){
//        	case 0:
//                return MainFragment.newInstance();
//        	case 1:
//                return MainFragment.newInstance();	//派送快件
//        	case 2:
//                return MainFragment.newInstance();	//揽收快件
//        	case 3:
//                return MainFragment.newInstance();	//转运快件
//        	}
        	return null;
        }

        @Override
        public int getCount() {
            // 总共4页.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
            }
            return null;
        }
    }

	@Override
	public void onFragmentInteraction(String id) {
		// TODO Auto-generated method stub
		
	}

}
