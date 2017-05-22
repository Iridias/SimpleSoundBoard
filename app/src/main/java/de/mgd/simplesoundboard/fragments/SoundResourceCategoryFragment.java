package de.mgd.simplesoundboard.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.mgd.simplesoundboard.R;
import de.mgd.simplesoundboard.SoundResourceRefreshable;
import de.mgd.simplesoundboard.core.AppEngine;
import de.mgd.simplesoundboard.core.ServiceFactory;
import de.mgd.simplesoundboard.dao.SoundResourceDao;

public class SoundResourceCategoryFragment extends Fragment implements SoundResourceRefreshable {

	private static final int AMOUNT_OF_DEFAULT_TABS = 1;

	private SoundResourceDao soundResourceDao = ServiceFactory.getOrCreateSoundResourceDao();

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.soundresource_categories, container, false);
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		TabLayout layout = (TabLayout) getView();
		layout.clearOnTabSelectedListeners();
		layout.addOnTabSelectedListener(ServiceFactory.getSoundCategoryTabSelectListener());

		List<String> categories = soundResourceDao.findSoundCategories();
		categories.forEach(c -> addTabToLayout(layout, c));
	}

	private void addTabToLayout(final TabLayout layout, final String name) {
		TabLayout.Tab t = layout.newTab();
		t.setText(name);

		layout.addTab(t);
	}

	@Override
	public void refreshSoundResources() {
		TabLayout layout = (TabLayout) getView();

		int tabCount = layout.getTabCount();
		List<String> categories = soundResourceDao.findSoundCategories();
		if(tabCount > AMOUNT_OF_DEFAULT_TABS && categories.size() != tabCount - AMOUNT_OF_DEFAULT_TABS) {
			resetTabLayout(layout);
			categories.forEach(c -> addTabToLayout(layout, c));
		}
	}

	private void resetTabLayout(final TabLayout layout) {
		AppEngine.getInstance().setManualRefresh(true); // prevent TabSelectListener to kick in
		layout.removeAllTabs();
		addTabToLayout(layout, AppEngine.getInstance().getResourceText(R.string.root_category));
		AppEngine.getInstance().setManualRefresh(false);
	}
}
