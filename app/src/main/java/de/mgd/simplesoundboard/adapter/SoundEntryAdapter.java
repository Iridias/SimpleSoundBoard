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
package de.mgd.simplesoundboard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.mgd.simplesoundboard.R;
import de.mgd.simplesoundboard.model.SoundResource;

public class SoundEntryAdapter extends ArrayAdapter<SoundResource> {

    private Context context;
    private List<SoundResource> data = new ArrayList<>();

    public SoundEntryAdapter(@NonNull final Context context, final int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public @NonNull View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        final View v = useOrCreateView(convertView);
        final SoundResource sr = data.get(position);

        TextView name = (TextView) v.findViewById(R.id.sound_entry_name);
        name.setText(sr.getName());

        return v;
    }

    private View useOrCreateView(final View convertView) {
        if (convertView != null) {
            return convertView;
        }

        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return vi.inflate(R.layout.sound_entry, null);
    }

    @Override
    public int getCount() {
        return (data != null ? data.size() : 0);
    }

    public SoundResource getItem(final int position) {
        if(data == null || data.isEmpty()) {
            return null;
        }

        return data.get(position);
    }

    public void setData(final List<SoundResource> data) {
        this.data = data;
    }
}
