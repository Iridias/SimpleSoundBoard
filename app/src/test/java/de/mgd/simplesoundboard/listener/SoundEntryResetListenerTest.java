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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.mgd.simplesoundboard.R;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SoundEntryResetListenerTest {

	private SoundEntryResetListener sut;

	@Mock
	private Handler handler;
	@Mock
	private SoundEntryClickCallbackNotifier notifier;
	@Mock
	private View view;
	private int position = 42;

	@Before
	public void setUp() {
		sut = new SoundEntryResetListener(view, position, notifier);
		sut.setHandler(handler);
	}

	@Test
	public void testOnCompletion() {
		final ImageView iv = mock(ImageView.class);
		final MediaPlayer mp = mock(MediaPlayer.class);

		given(view.findViewById(R.id.sound_entry_icon)).willReturn(iv);

		doAnswer(invocationOnMock -> {
			Runnable r = invocationOnMock.getArgument(0);
			r.run();
			return true;
		}).when(handler).post(any(Runnable.class));

		sut.onCompletion(mp);

		verifyZeroInteractions(mp);
		verify(handler, times(1)).post(any(Runnable.class));
		verify(notifier, times(1)).notify(position);
		verify(view, times(1)).findViewById(R.id.sound_entry_icon);
		verify(iv, times(1)).setImageResource(R.drawable.media_start);
	}

}
