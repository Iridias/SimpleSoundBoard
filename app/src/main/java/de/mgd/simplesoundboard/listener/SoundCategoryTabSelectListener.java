package de.mgd.simplesoundboard.listener;

import android.support.design.widget.TabLayout;

import org.apache.commons.lang.StringUtils;

import de.mgd.simplesoundboard.R;
import de.mgd.simplesoundboard.SoundResourceRefreshable;
import de.mgd.simplesoundboard.core.AppEngine;

public class SoundCategoryTabSelectListener implements TabLayout.OnTabSelectedListener {

	private SoundResourceRefreshable soundResourceRefreshable;

	public SoundCategoryTabSelectListener(final SoundResourceRefreshable soundResourceRefreshable) {
		this.soundResourceRefreshable = soundResourceRefreshable;
	}

	@Override
	public void onTabSelected(final TabLayout.Tab tab) {
		String categoryName = (String) tab.getText();
		AppEngine appEngine = AppEngine.getInstance();

		if(appEngine.isManualRefresh()) {
			return;
		}

		if(StringUtils.equals(categoryName, appEngine.getResourceText(R.string.root_category))) {
			appEngine.setCurrentCategory(null);
		} else {
			appEngine.setCurrentCategory(categoryName);
		}

		soundResourceRefreshable.refreshSoundResources();
	}

	@Override
	public void onTabUnselected(final TabLayout.Tab tab) {

	}

	@Override
	public void onTabReselected(final TabLayout.Tab tab) {

	}
}
