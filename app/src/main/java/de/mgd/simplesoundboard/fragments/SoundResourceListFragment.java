package de.mgd.simplesoundboard.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import de.mgd.simplesoundboard.R;
import de.mgd.simplesoundboard.SoundResourceRefreshable;
import de.mgd.simplesoundboard.adapter.SoundEntryAdapter;
import de.mgd.simplesoundboard.core.AppEngine;
import de.mgd.simplesoundboard.core.ServiceFactory;
import de.mgd.simplesoundboard.dao.SoundResourceDao;
import de.mgd.simplesoundboard.listener.SoundEntryClickListener;
import de.mgd.simplesoundboard.model.SoundResource;

public class SoundResourceListFragment extends Fragment implements SoundResourceRefreshable {

	private Handler handler = new Handler();
	private SoundEntryAdapter soundEntryAdapter;
	private SoundResourceDao soundResourceDao = ServiceFactory.getOrCreateSoundResourceDao();
	private SoundEntryClickListener soundEntryClickListener;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.soundresource_list, container, false);
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		soundEntryAdapter = new SoundEntryAdapter(getActivity(), 0);
		soundEntryClickListener = new SoundEntryClickListener(getActivity(), soundEntryAdapter);
		ListView soundEntryList = (ListView) getView();

		handler.post(() -> {
			soundEntryAdapter.setData(findSoundResources());
			soundEntryList.setAdapter(soundEntryAdapter);
			soundEntryList.setOnItemClickListener(soundEntryClickListener);
		});
	}

	private List<SoundResource> findSoundResources() {
		if(AppEngine.getInstance().getCurrentCategory() == null) {
			return soundResourceDao.findExistingSoundResources();
		}

		return soundResourceDao.findExistingSoundResources(AppEngine.getInstance().getCurrentCategory());
	}

	@Override
	public void refreshSoundResources() {
		handler.post(() -> {
			soundEntryAdapter.setData(findSoundResources());
			soundEntryAdapter.notifyDataSetInvalidated();
		});
	}
}
