package newCore.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    static public void append(final String pathname, final String contents) {
        if (!FileUtils.exists(pathname)) {
            FileUtils.crtFile(pathname);
        }

        try {
            final FileWriter fw = new FileWriter(pathname, true);
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contents);
            bw.close();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static public void appendLine(final String pathname, final String contents) {
        FileUtils.append(pathname, contents + System.getProperty("line.separator"));
    }

    static public boolean contains(final String pathname, final String text) throws IOException {
        final String contents = FileUtils.readAll(pathname);

        return contents.contains(text);
    }

    static public void copy(final String sourcePath, final String targetPath) throws IOException {
        final Path sourceDirectory = Paths.get(sourcePath);
        final Path targetDirectory = Paths.get(targetPath);

        final CopyOption[] copyOptions = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES };

        Files.copy(sourceDirectory, targetDirectory, copyOptions);
    }

    static public void crtFile(final File file) {
        FileUtils.crtFile(file.getAbsolutePath());
    }

    static public void crtFile(final String pathname) {
        final File file = new File(pathname);

        if ((file.getParentFile() != null) && !file.getParentFile().exists()) {
            DirectoryUtils.crtDir(file.getParentFile().getPath());
        }

        try {
            file.createNewFile();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static public void del(final String pathname) {
        final File file = new File(pathname);
        file.delete();
    }

    static public boolean exists(final String pathname) {
        final File file = new File(pathname);
        return file.exists();
    }

    static public String getAbsPath(final String pathname) {
        final File dir = new File(pathname);
        return dir.getAbsolutePath();
    }

    static public long getFileSize(final String pathname) {
        final File file = new File(pathname);
        return file.length();
    }

    static public void insertLine(final String pathname, final String contents, int lineNumber) {
        final List<String> lines = FileUtils.readAllLines(pathname);

        if (lineNumber < 0) {
            lineNumber += lines.size();
        }

        FileUtils.del(pathname);
        FileUtils.crtFile(pathname);

        for (int i = 0; i < lines.size(); i++) {
            if (i == lineNumber) {
                FileUtils.append(pathname, contents + System.getProperty("line.separator"));
            }

            FileUtils.append(pathname, lines.get(i) + System.getProperty("line.separator"));
        }
    }

    static public List<String> read(final String pathname) throws IOException {
        final File file = new File(pathname);

        return org.apache.commons.io.FileUtils.readLines(file);
    }

    static public String readAll(final String pathname) {
        final List<String> lines = FileUtils.readAllLines(pathname);
        final StringBuilder contents = new StringBuilder();

        for (final String line : lines) {
            contents.append(line + System.getProperty("line.separator"));
        }

        return contents.toString();
    }

    static public List<String> readAllLines(final String pathname) {
        final File file = new File(pathname);
        // List<String> lines = null;
        List<String> lines = new ArrayList<String>();

        try {
            // lines = Files.readAllLines(file.toPath(),
            // Charset.defaultCharset());

            FileInputStream input;

            input = new FileInputStream(file);

            final CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
            decoder.onMalformedInput(CodingErrorAction.REPLACE);

            final InputStreamReader reader = new InputStreamReader(input, decoder);
            final BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();

            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Failed to search " + file.toPath());
            e.printStackTrace();

            lines = new ArrayList<String>();
            return lines;
        }

        return lines;
    }

    static public String removeIllegalCharacters(String fileName) {
        // Remove illegal characters
        fileName = fileName.replaceAll("\\\\", "_");
        fileName = fileName.replaceAll("/", "_");
        fileName = fileName.replaceAll(":", "_");
        fileName = fileName.replaceAll("\\*", "_");
        fileName = fileName.replaceAll("\\?", "_");
        fileName = fileName.replaceAll("\"", "_");
        fileName = fileName.replaceAll("<", "_");
        fileName = fileName.replaceAll(">", "_");
        fileName = fileName.replaceAll("\\|", "_");

        return fileName;
    }

    static public void save(final List<String> data, final String pathname) {
        FileUtils.crtFile(pathname);

        for (int i = 0; i < data.size(); i++) {
            FileUtils.append(pathname, data.get(i) + System.getProperty("line.separator"));
        }
    }

    static public void write(final String pathname, final String contents) {
        try {
            final FileWriter fw = new FileWriter(pathname);
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contents);
            bw.close();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static public void writeLine(final String pathname, final String contents) {
        FileUtils.write(pathname, contents + System.getProperty("line.separator"));
    }

    private FileUtils() {
        // TODO Auto-generated constructor stub
    }
}
