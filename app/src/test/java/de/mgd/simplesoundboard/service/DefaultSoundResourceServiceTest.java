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

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import de.mgd.simplesoundboard.model.SoundResource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class DefaultSoundResourceServiceTest {

	private DefaultSoundResourceService sut;

	@Before
	public void setUp() {
		sut = new DefaultSoundResourceService();
	}

	@Test
	public void testCreateSoundResourceFromFile() {
		testCreateSoundResourceFromFileInternal("test", "test");
		testCreateSoundResourceFromFileInternal("test_it", "test it");
		testCreateSoundResourceFromFileInternal("narf-zort", "narf-zort");
	}

	private void testCreateSoundResourceFromFileInternal(final String fileName, final String expectedName) {
		File file = new File(fileName);

		SoundResource result = sut.createSoundResourceFromFile(file);
		assertNotNull(result);
		assertSame(file, result.getFile());
		assertEquals(expectedName, result.getName());
	}

}
