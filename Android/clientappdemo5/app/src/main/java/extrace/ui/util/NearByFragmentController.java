package extrace.ui.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import extrace.ui.main.MyRcvboxFragment;
import extrace.ui.main.MySendboxFragment;

public class NearByFragmentController {

    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static NearByFragmentController controller;

    public static NearByFragmentController getInstance(Fragment parentFragment, int containerId) {
        if (controller == null) {
            controller = new NearByFragmentController(parentFragment, containerId);
        }
        return controller;
    }

    private NearByFragmentController(Fragment fragment, int containerId) {
        this.containerId = containerId;
        //fragment嵌套fragment，调用getChildFragmentManager
        fm = fragment.getChildFragmentManager();

        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new MyRcvboxFragment());
        fragments.add(new MySendboxFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commit();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

}
