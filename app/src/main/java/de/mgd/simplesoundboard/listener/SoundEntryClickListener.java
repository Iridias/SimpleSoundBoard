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
package de.mgd.simplesoundboard.listener;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import de.mgd.simplesoundboard.R;
import de.mgd.simplesoundboard.adapter.SoundEntryAdapter;
import de.mgd.simplesoundboard.model.SoundResource;

public class SoundEntryClickListener implements AdapterView.OnItemClickListener, SoundEntryClickCallbackNotifier {

	private SoundEntryAdapter soundEntryAdapter;
	private Context context;
	private Handler handler = new Handler();
	private SparseArray<MediaPlayer> mediaPlayerCache = new SparseArray<>();

	public SoundEntryClickListener(final Context context, final SoundEntryAdapter soundEntryAdapter) {
		this.context = context;
		this.soundEntryAdapter = soundEntryAdapter;
	}

	@Override
	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
		MediaPlayer player = mediaPlayerCache.get(position);
		if(player != null) {
			handleStopRequest(player, view, position);
			return;
		}

		SoundResource sr = soundEntryAdapter.getItem(position);
		changePlayIconToStop(view);

		player = MediaPlayer.create(context, Uri.fromFile(sr.getFile()));
		player.setOnCompletionListener(new SoundEntryResetListener(view, position, this));
		player.start();

		mediaPlayerCache.put(position, player);
	}

	private void handleStopRequest(final MediaPlayer player, final View view, final int position) {
		if(player.isPlaying()) {
			player.stop();
		}
		changePlayIconToPlay(view);
		mediaPlayerCache.delete(position);
	}

	private void changePlayIconToPlay(final View view) {
		changePlayIcon(view, R.drawable.media_start);
	}

	private void changePlayIconToStop(final View view) {
		changePlayIcon(view, R.drawable.media_stop);
	}

	private void changePlayIcon(final View view, final int drawableResource) {
		final ImageView iv = (ImageView) view.findViewById(R.id.sound_entry_icon);
		handler.post(() -> {
			iv.setImageResource(drawableResource);
		});
	}

	@Override
	public void notify(final int affectedPosition) {
		mediaPlayerCache.delete(affectedPosition);
	}
}
