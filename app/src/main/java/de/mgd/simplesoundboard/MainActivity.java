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
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import de.mgd.simplesoundboard.adapter.SoundEntryAdapter;
import de.mgd.simplesoundboard.core.ServiceFactory;
import de.mgd.simplesoundboard.dao.SoundResourceDao;
import de.mgd.simplesoundboard.listener.SoundEntryClickListener;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private SoundEntryAdapter soundEntryAdapter;
    private SoundResourceDao soundResourceDao;
    private SoundEntryClickListener soundEntryClickListener;

    private static final int ARBITRARY_PERMISSION_REQUEST_CODE = 42;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        soundResourceDao = ServiceFactory.getOrCreateSoundResourceDao();
        soundEntryAdapter = new SoundEntryAdapter(getApplicationContext(), 0);
        soundEntryClickListener = new SoundEntryClickListener(getApplicationContext(), soundEntryAdapter);
        final ListView soundEntryList = (ListView) findViewById(R.id.sound_entry_list);

        handler.post(() -> {
            soundEntryAdapter.setData(soundResourceDao.findExistingSoundResources());
            soundEntryList.setAdapter(soundEntryAdapter);
            soundEntryList.setOnItemClickListener(soundEntryClickListener);
        });
    }

    private void refreshSoundResources() {
		handler.post(() -> {
			soundEntryAdapter.setData(soundResourceDao.findExistingSoundResources());
			soundEntryAdapter.notifyDataSetInvalidated();
		});
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
