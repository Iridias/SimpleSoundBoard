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
package de.mgd.simplesoundboard.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;

import de.mgd.simplesoundboard.model.SoundResource;

public class DefaultSoundResourceService implements SoundResourceService {

	@Override
	public SoundResource createSoundResourceFromFile(final File file) {
		SoundResource.Builder b = new SoundResource.Builder();
		b.setFile(file);
		b.setName(enhanceName(file.getName()));

		return b.build();
	}

	private String enhanceName(final String fileName) {
		return StringUtils.replace(FilenameUtils.getBaseName(fileName), "_"," ");
	}
}
