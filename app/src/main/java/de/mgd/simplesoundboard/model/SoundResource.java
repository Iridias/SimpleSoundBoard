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
package de.mgd.simplesoundboard.model;

import java.io.File;

public class SoundResource {

	private final String name;
	private final File file;

	private SoundResource(final Builder b) {
		name = b.name;
		file = b.file;
	}

	public static class Builder {
		private String name;
		private File file;

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setFile(File file) {
			this.file = file;
			return this;
		}

		public SoundResource build() {
			return new SoundResource(this);
		}
	}

	public String getName() {
		return name;
	}

	public File getFile() {
		return file;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SoundResource that = (SoundResource) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		return file != null ? file.equals(that.file) : that.file == null;

	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (file != null ? file.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "SoundResource{" +
				"name='" + name + '\'' +
				", file=" + file +
				'}';
	}
}
