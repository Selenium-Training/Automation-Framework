package newCore.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

public class DirectoryUtils {
	
    static public void copy(final String sourcePath, final String targetPath) throws IOException {
        File destDir = new File(targetPath);
        File srcDir = new File(sourcePath);

        if (exists(targetPath)) {
            org.apache.commons.io.FileUtils.copyDirectoryToDirectory(srcDir, destDir);
        } else {
            org.apache.commons.io.FileUtils.copyDirectory(srcDir, destDir);
        }
    }

    static public String crtDir(final String dirPath) {
        final File dir = new File(dirPath);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir.getAbsolutePath();
    }

    static public void del(final String dirPath) {
        FileUtils.del(dirPath);
    }

    static public boolean exists(final String dirPath) {
        return FileUtils.exists(dirPath);
    }

    static public String getAbsPath(final String dirPath) {
        return FileUtils.getAbsPath(dirPath);
    }

    static public String getCwd() {
        String currentDir = null;

        final File currentPath = new java.io.File("");

        try {
            currentDir = currentPath.getCanonicalPath().toString();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return currentDir;
    }

    static public Collection<File> listFiles(final String pathname) {
        final File dir = new File(pathname);

        return org.apache.commons.io.FileUtils.listFiles(dir, new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
    }

    static public void rmDir(final String dirPath) {
        final File dir = new File(dirPath);
        dir.delete();

        try {
            org.apache.commons.io.FileUtils.deleteDirectory(dir);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}

