package Mp3Explorer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raik Yauheni on 04.11.2018.
 */
public class Runner {
    public static final String ILLEGAL_ARGUMENTS_MESSAGE =
            "Program is shutting down.\n" +
            "You should enter correct paths to directories with mp3 files like this:\n" +
            "-C:\\Program Files\\Music D:\\Music\\the music\"\n";
    public static final String DELIMITER_STRINGS =
            "----------------------------------------------------------------------------------";
    public final String reportPath = "test/report.html";
    private List<File> listMp3Files = new ArrayList<>();
    private List<Mp3Info> listMp3Info = null;

    public List<File> getListMp3Files() {
        return listMp3Files;
    }

    public List<Mp3Info> getListMp3FilesInfo() {
        return listMp3Info;
    }

    public static void main(String[] args) throws IllegalArgumentException {
        Runner runner = new Runner();
        List<File> directories = runner.checkAndGetDirectories(args);
        runner.perform(directories);
    }

    public void perform(List<File> directories) {
        SearchEngineMp3 searcher = new SearchEngineMp3();
        CreatorMp3Info creatorMp3Info = new CreatorMp3Info();
        Mp3FileInfoToHtml printerToHtml = new Mp3FileInfoToHtml();

        for (File dir : directories) {
            listMp3Files.addAll(searcher.getListMp3Files(dir));
        }
        System.out.println("It has been found: " +  listMp3Files.size() + " files");
        listMp3Info = creatorMp3Info.getListOfFileInfo(listMp3Files);
        printerToHtml.addMp3InfoToTree(listMp3Info);

        PrintWriter pw = null;
        try {
         pw = new PrintWriter("test/report.html");
        } catch (FileNotFoundException e) {
        System.out.println("can not create file report.html");
        System.out.println("Perhaps, you do not have permissions for this operation or there is not enough space");
        }
        printerToHtml.genereateHtmlReport(pw);

        System.setProperty("log4j.configurationFile", "log4j2.xml");
        Logger logger = LogManager.getRootLogger();
        Logger logger2 = LogManager.getLogger("TAG");

        searcher.printDuplicatesFiles(searcher.getDuplicateFiles(listMp3Files),  logger);
        searcher.printDuplicatesTags(searcher.getDuplicateTags(listMp3Info),  logger2);
    }

    public  List<File> checkAndGetDirectories(String...args) throws IllegalArgumentException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Empty arguments.\n" +
            ILLEGAL_ARGUMENTS_MESSAGE);
        }
        List<File> listDirectories = new ArrayList<>();
        File file = null;
        for (String stringPath : args) {
            file = new File(stringPath);
            if (file.isDirectory() && ! listDirectories.contains(file) ) {
                listDirectories.add(file);
            } else {
                if (!listDirectories.contains(file)) {
                    System.out.println("Directory " + file.getAbsolutePath() + " does not exist.");
                }
            }
        }
        if(listDirectories.size() == 0 ) throw new IllegalArgumentException("There are not any directories.\n" +
                ILLEGAL_ARGUMENTS_MESSAGE);
        System.out.println(DELIMITER_STRINGS);
        System.out.println("The search is being performed in the following directories:");
        System.out.println(DELIMITER_STRINGS);
        int counter = 0;
        for(File dir : listDirectories) {
            System.out.println(++counter + "." + dir.toString());
        }
        System.out.println(DELIMITER_STRINGS);
        return listDirectories;
    }
}
