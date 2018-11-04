package Mp3Explorer;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.mpatric.mp3agic.*;

/**
 * Created by Raik Yauheni on 02.11.2018.
 */
public class TestRunner {
    List <File> allFiles = new ArrayList<>();
    List <File> mp3Files = new ArrayList<>();
    static List <Mp3FileInfo> listMp3Info;

    public static void main(String[] args) throws Exception {
        Folders f = new Folders();
        System.out.println(Charset.defaultCharset());
        listMp3Info =  f.getMp3Id3TagsInfo("f:\\MusicDirectory");
//        for(Mp3FileInfo info : listMp3Info) {
//            System.out.println(info.getFullDescription());
//        }
        Mp3FileInfoToHtml printerToHtml = new Mp3FileInfoToHtml();
        printerToHtml.addMp3FilesInfoToTree(listMp3Info);


        PrintWriter pw = new PrintWriter("report.html");
        printerToHtml.genereateHtmlReport(pw);





    }
}
