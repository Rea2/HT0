package Mp3Explorer;

import java.io.PrintWriter;


/**
 * Created by Raik Yauheni on 04.11.2018.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        SearchEngineMp3 se = new SearchEngineMp3();
        Mp3FileInfoToHtml printerToHtml = new Mp3FileInfoToHtml();
        printerToHtml.addMp3InfosToTree(se.getListMp3Id3TagsInfo("f:\\MusicDirectory"));
        PrintWriter pw = new PrintWriter("report.html");
        printerToHtml.genereateHtmlReport(pw);
    }

}
