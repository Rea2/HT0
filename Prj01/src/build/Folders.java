package build;

import java.io.File;

/**
 * Created by Raik Yauheni on 30.10.2018.
 */
public class Folders {
    public static int countMp3Files;
    public static int countAllFiles;

    public static void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            }
            else {
                if (fileEntry.getName().substring(fileEntry.getName().length()-4).equalsIgnoreCase(".mp3")) {
                    System.out.println(fileEntry.getName());
                    countMp3Files++;
                }
                countAllFiles++;

            }
        }
    }

    public static void main(String[] args) {
        final File folder = new File("f:\\MusicDirectory");
        listFilesForFolder(folder);
        System.out.println("Nubmer of all files / mp3 files: " + countAllFiles + "//" +countMp3Files);

    }


}
