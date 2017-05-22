package de.mgd.simplesoundboard.core;

import android.app.Application;

public class AppEngine extends Application {

	private static AppEngine instance;

	private String currentCategory;
	private boolean manualRefresh;

	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static AppEngine getInstance() {
		return instance; // assume, that its already created by onCreate
	}

	public String getResourceText(final int id) {
		return (String) getResources().getText(id);
	}

	public boolean isManualRefresh() {
		return manualRefresh;
	}

	public void setManualRefresh(final boolean manualRefresh) {
		this.manualRefresh = manualRefresh;
	}

	public String getCurrentCategory() {
		return currentCategory;
	}

	public void setCurrentCategory(final String currentCategory) {
		this.currentCategory = currentCategory;
	}
}
