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
package de.mgd.simplesoundboard.core;

import de.mgd.simplesoundboard.SoundResourceRefreshable;
import de.mgd.simplesoundboard.dao.FileSystemSoundResourceDao;
import de.mgd.simplesoundboard.dao.SoundResourceDao;
import de.mgd.simplesoundboard.listener.SoundCategoryTabSelectListener;
import de.mgd.simplesoundboard.service.DefaultSoundResourceService;
import de.mgd.simplesoundboard.service.SoundResourceService;

public class ServiceFactory {

    private static DefaultSoundResourceService defaultSoundResourceService;
    private static FileSystemSoundResourceDao fileSystemSoundResourceDao;
    private static SoundCategoryTabSelectListener soundCategoryTabSelectListener;

    public static void initSoundCategoryTabSelectListener(final SoundResourceRefreshable refreshable) {
        soundCategoryTabSelectListener = new SoundCategoryTabSelectListener(refreshable);
    }

    public static SoundCategoryTabSelectListener getSoundCategoryTabSelectListener() {
        return soundCategoryTabSelectListener;
    }

    public static SoundResourceDao getOrCreateSoundResourceDao() {
        if(fileSystemSoundResourceDao == null) {
            fileSystemSoundResourceDao = new FileSystemSoundResourceDao();
            fileSystemSoundResourceDao.setSoundResourceService(getOrCreateSoundResourceService());
        }

        return fileSystemSoundResourceDao;
    }

    public static SoundResourceService getOrCreateSoundResourceService() {
        if(defaultSoundResourceService == null) {
            defaultSoundResourceService = new DefaultSoundResourceService();
        }

        return defaultSoundResourceService;
    }

}
