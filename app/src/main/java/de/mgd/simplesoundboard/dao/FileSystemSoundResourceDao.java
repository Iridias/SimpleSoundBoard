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
package de.mgd.simplesoundboard.dao;

import android.os.Environment;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.mgd.simplesoundboard.model.SoundResource;
import de.mgd.simplesoundboard.service.SoundResourceService;

public class FileSystemSoundResourceDao implements SoundResourceDao {

	private static final String DEFAULT_DIR = "SoundBoard";
	private static final String[] FILE_TYPES = new String[] { "mp3", "ogg", "3gp", "m4a", "wav", "aac", "flac" };

	private SoundResourceService soundResourceService;

	@Override
	public List<String> findSoundCategories() {
		List<File> files = findFilesInStorageDirectoryIfAny(this::isSoundCategory);
		if(files.isEmpty()) {
			return Collections.emptyList();
		}

		files.sort(NameFileComparator.NAME_INSENSITIVE_COMPARATOR);
		return files.stream().map(File::getName).collect(Collectors.toList());
	}

	@Override
	public List<SoundResource> findExistingSoundResources() {
		return findExistingSoundResources(null);
	}

	@Override
	public List<SoundResource> findExistingSoundResources(final String category) {
		List<File> files = findFilesInStorageDirectoryIfAny(this::isMediaFile, category);
		if(files.isEmpty()) {
			return Collections.emptyList();
		}
		files.sort(NameFileComparator.NAME_INSENSITIVE_COMPARATOR);

		return files.stream().map(f -> soundResourceService.createSoundResourceFromFile(f)).collect(Collectors.toList());
	}

	@Override
	public boolean hasCategories() {
		File resourceDir = new File(Environment.getExternalStorageDirectory(), DEFAULT_DIR);
		if(!resourceDir.isDirectory()) {
			return false;
		}

		File[] subDirs = resourceDir.listFiles(this::isSoundCategory);

		return subDirs != null && subDirs.length > 0;
	}

	private List<File> findFilesInStorageDirectoryIfAny(final FileFilter filter) {
		return findFilesInStorageDirectoryIfAny(filter, null);
	}

	private List<File> findFilesInStorageDirectoryIfAny(final FileFilter filter, final String subDir) {
		String child = StringUtils.isBlank(subDir) ? DEFAULT_DIR : DEFAULT_DIR + File.separator + subDir;
		File resourceDir = new File(Environment.getExternalStorageDirectory(), child);
		if(!resourceDir.isDirectory()) {
			return Collections.emptyList();
		}

		File[] resultList = resourceDir.listFiles(filter);
		if(resultList == null || resultList.length == 0) {
			return Collections.emptyList();
		}

		return Arrays.asList(resultList);
	}

	private boolean isSoundCategory(final File file) {
		return file.isDirectory();
	}

	private boolean isMediaFile(final File file) {
		return file.isFile() && FilenameUtils.isExtension(StringUtils.lowerCase(file.getName()), FILE_TYPES);
	}


	public void setSoundResourceService(final SoundResourceService soundResourceService) {
		this.soundResourceService = soundResourceService;
	}
}
