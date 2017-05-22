package de.mgd.simplesoundboard.listener;

import de.mgd.simplesoundboard.TabNavigationAware;

public class SoundCategorySwipeListener extends OnSwipeTouchListener {

	private TabNavigationAware tabNavigationAware;

	public SoundCategorySwipeListener(final TabNavigationAware tabNavigationAware) {
		this.tabNavigationAware = tabNavigationAware;
	}

	public boolean onSwipeRight() {
		tabNavigationAware.activateTabWithIndexOffset(-1);
		return true;
	}

	public boolean onSwipeLeft() {
		tabNavigationAware.activateTabWithIndexOffset(1);
		return true;
	}

	public boolean onSwipeTop() {
		return false;
	}

	public boolean onSwipeBottom() {
		return false;
	}

}
