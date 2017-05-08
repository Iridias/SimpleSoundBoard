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

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import de.mgd.simplesoundboard.R;

public class SoundEntryResetListener implements MediaPlayer.OnCompletionListener {

	private SoundEntryClickCallbackNotifier notifier;
	private Handler handler = new Handler();
	private View view;
	private int position;

	public SoundEntryResetListener(final View view, final int position, final SoundEntryClickCallbackNotifier notifier) {
		this.view = view;
		this.position = position;
		this.notifier = notifier;
	}

	@Override
	public void onCompletion(final MediaPlayer mp) {
		final ImageView iv = (ImageView) view.findViewById(R.id.sound_entry_icon);
		handler.post(() -> {
			iv.setImageResource(R.drawable.media_start);
		});
		notifier.notify(position);
	}

	public void setHandler(final Handler handler) {
		this.handler = handler;
	}
}
