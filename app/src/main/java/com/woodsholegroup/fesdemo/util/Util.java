package com.woodsholegroup.fesdemo.util;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ltossou on 8/15/2018.
 */

public class Util {
    private Util() {}

    public static void showDialogFragment(FragmentManager supportFragmentManager,
                                          DialogFragment dialogFragment, String dialogTag) {
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        Fragment prev = supportFragmentManager.findFragmentByTag(dialogTag);
        if (prev != null) {
            ft.remove(prev);
        }
        dialogFragment.show(ft, dialogTag);
    }
}
