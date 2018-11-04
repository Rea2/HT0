package Mp3Explorer;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Raik Yauheni on 02.11.2018.
 */
public class TestRunner {

    static List <Mp3Info> listMp3Info;

    public static void main(String[] args) throws Exception {
        SearchEngineMp3 f = new SearchEngineMp3();
        System.out.println(Charset.defaultCharset());
        listMp3Info =  f.getListMp3Id3TagsInfo("f:\\MusicDirectory");
//        for(Mp3Info info : listMp3Info) {
//            System.out.println(info.getFullDescription());
//        }
        Mp3FileInfoToHtml printerToHtml = new Mp3FileInfoToHtml();
        printerToHtml.addMp3InfosToTree(listMp3Info);


        PrintWriter pw = new PrintWriter("report.html");
        printerToHtml.genereateHtmlReport(pw);





    }
}
