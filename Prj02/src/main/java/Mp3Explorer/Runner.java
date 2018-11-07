package Mp3Explorer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Runner} class provide all functionality of the application. It creates objects, uses other classes
 * and methods of the package.  *
 * @author Raik Yauheni
 *
 */
public class Runner {
    public static final String MESSAGE_ILLEGAL_ARGUMENTS =
            "Program is shutting down.\n" +
            "You should enter correct paths to directories with mp3 files like this:\n" +
            "-C:\\Program Files\\Music D:\\Music\\the music\"\n";
    private final String MESSAGE_CAN_NOT_CREATE_FILE_REPORT =
            "Can not create file " + REPORT_FILE_NAME + "\n" +
            "Perhaps, you do not have permissions for this operation or there is not enough space\n" +
             "Program is shutting down.";
    public static  final String DELIMITER_STRINGS =
            "----------------------------------------------------------------------------------";
    private final static String REPORT_FILE_NAME = "report.html";
    private final String REPORT_FILE_PATH = "test/";
    /** The value is used for MP3 files storage. */
    private List<File> listMp3Files = new ArrayList<>();

    /** The value is used for Mp3Info objects storage. */
    private List<Mp3Info> listMp3Info = null;
    private File fileResults;

    public List<File> getListMp3Files() {
        return listMp3Files;
    }

    public List<Mp3Info> getListMp3FilesInfo() {
        return listMp3Info;
    }

    /**
     * Receives list of folders as arguments,checks them. If there is at least on suitable  argument,
     * run searching mp3 files
     *
     * @param args
     *        list of folders, where it will be perform searching mp3 files and analysis ID3 Tags.
     * @throws IllegalArgumentException
     *         if there is not at least one suitable argument among {@code args}  .
     */
    public static void main(String[] args) throws IllegalArgumentException {
        Runner runner = new Runner();
        List<File> directories = runner.checkAndGetDirectories(args);
        runner.perform(directories);
    }

    public void perform(List<File> directories) {
        SearchEngineMp3 searcher = new SearchEngineMp3();
        CreatorMp3Info creatorMp3Info = new CreatorMp3Info();
        Mp3ListInfoToHtml printerToHtml = new Mp3ListInfoToHtml();

        for (File dir : directories) {
            searcher.getListMp3Files(dir);
        }
        listMp3Files.addAll(searcher.getMp3Files());
        System.out.println("It has been found: " +  listMp3Files.size() + " Mp3 files.");
        System.out.println("Creating HTML report ...");
        listMp3Info = creatorMp3Info.getListOfFileInfo(listMp3Files);
        printerToHtml.addMp3InfoToTree(listMp3Info);
        PrintWriter pw = null;

        try {
         fileResults = new File(REPORT_FILE_PATH + REPORT_FILE_NAME);
         pw = new PrintWriter(fileResults);
        } catch (IOException e) {
            System.out.println(MESSAGE_CAN_NOT_CREATE_FILE_REPORT);
        }

        printerToHtml.generateHtmlReport(pw);
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        Logger logger = LogManager.getRootLogger();
        Logger logger2 = LogManager.getLogger("TAG");
        searcher.printDuplicatesFiles(searcher.getDuplicateFiles(listMp3Files),  logger);
        searcher.printDuplicatesTags(searcher.getDuplicateTags(listMp3Info),  logger2);
        System.out.println("The program has worked successfully!");

        if (fileResults != null) {
            System.out.println("You can see results into: " +  fileResults.getAbsolutePath());
        }
    }
    /**
     *
     * @param args
     *        list folder for checking
     * @return
     *        {@code List<File>} checked list of  folders
     *
     * @throws IllegalArgumentException
*              if there is not at least one suitable argument among {@code args}.
     */

    public  List<File> checkAndGetDirectories(String...args) throws IllegalArgumentException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Empty arguments.\n" + MESSAGE_ILLEGAL_ARGUMENTS);
        }
        List<File> listDirectories = new ArrayList<>();
        File file = null;
        for (String stringPath : args) {
            file = new File(stringPath);
            if (file.isDirectory()) {
                if (!listDirectories.contains(file)){
                    listDirectories.add(file);
                }
            } else System.out.println("Directory " + file.getAbsolutePath() + " does not exist.");
         }

        if(listDirectories.size() == 0 ) throw new IllegalArgumentException("There are not any directories.\n" +
                MESSAGE_ILLEGAL_ARGUMENTS);
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
