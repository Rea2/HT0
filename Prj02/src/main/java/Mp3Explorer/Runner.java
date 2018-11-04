package Mp3Explorer;

import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Created by Raik Yauheni on 04.11.2018.
 */
public class Runner {


    public static void main(String[] args) throws Exception {

        SearchEngineMp3 f = new SearchEngineMp3();
        System.out.println(Charset.defaultCharset());

        Mp3FileInfoToHtml printerToHtml = new Mp3FileInfoToHtml();
        printerToHtml.addMp3FilesInfoToTree(f.getListMp3Id3TagsInfo("f:\\MusicDirectory"));

        PrintWriter pw = new PrintWriter("report.html");
        printerToHtml.genereateHtmlReport(pw);





    }

}
