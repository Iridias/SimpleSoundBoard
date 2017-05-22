/*
    This file is part of Simple SoundBoard.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package de.mgd.simplesoundboard;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import de.mgd.simplesoundboard.core.ServiceFactory;
import de.mgd.simplesoundboard.dao.SoundResourceDao;
import de.mgd.simplesoundboard.fragments.SoundResourceCategoryFragment;
import de.mgd.simplesoundboard.fragments.SoundResourceListFragment;
import de.mgd.simplesoundboard.listener.SoundCategorySwipeListener;

public class MainActivity extends AppCompatActivity implements SoundResourceRefreshable, TabNavigationAware {

	private SoundResourceDao soundResourceDao = ServiceFactory.getOrCreateSoundResourceDao();
	private SoundCategorySwipeListener swipeListener = new SoundCategorySwipeListener(this);

    private static final int ARBITRARY_PERMISSION_REQUEST_CODE = 42;
    private static final String CONTENT_FRAGMENT_ID = "CONTENT";
	private static final String NAVIGATION_FRAGMENT_ID = "NAV";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ServiceFactory.initSoundCategoryTabSelectListener(this);
        initFragments();
    }

    private void initFragments() {
		FragmentTransaction tx = getFragmentManager().beginTransaction();

		if(soundResourceDao.hasCategories()) {
			tx.replace(R.id.content_nav, new SoundResourceCategoryFragment(), NAVIGATION_FRAGMENT_ID);
		}
		tx.replace(R.id.content_root, new SoundResourceListFragment(), CONTENT_FRAGMENT_ID);
		tx.commit();
	}

    public void refreshSoundResources() {
		refreshFragment(NAVIGATION_FRAGMENT_ID);
		refreshFragment(CONTENT_FRAGMENT_ID);
	}

	private void refreshFragment(final String tag) {
		Fragment f = getFragmentManager().findFragmentByTag(tag);
		if(f instanceof SoundResourceRefreshable) {
			((SoundResourceRefreshable)f).refreshSoundResources();
		}
	}

	public void activateTabWithIndexOffset(final int offset) {
    	TabLayout tabLayout = (TabLayout) getFragmentManager().findFragmentByTag(NAVIGATION_FRAGMENT_ID).getView();
		int tabIndex = tabLayout.getSelectedTabPosition();
		TabLayout.Tab t = tabLayout.getTabAt(tabIndex + offset);
		if(t != null) {
			t.select();
		}
	}

	@Override
	public boolean dispatchTouchEvent(final MotionEvent ev){
    	boolean result = false;
    	if(soundResourceDao.hasCategories()) {
			result = swipeListener.getGestureDetector().onTouchEvent(ev);
		}
		return result || super.dispatchTouchEvent(ev);
	}

    @Override
    protected void onResume() {
        super.onResume();
        if(!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ARBITRARY_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean hasPermission(final String perm) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
		if(item.getItemId() == R.id.action_refresh) {
			refreshSoundResources();
		}

        return super.onOptionsItemSelected(item);
    }
}
