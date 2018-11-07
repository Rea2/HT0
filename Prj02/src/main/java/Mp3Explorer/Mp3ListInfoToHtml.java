package Mp3Explorer;

import java.io.*;
import java.util.*;

/**
 * Created by Raik Yauheni on 04.11.2018.
 */
public class Mp3ListInfoToHtml {

    private Map<String, Map<String, List<Mp3Info>>> mapForCreatingHTML = new HashMap<>();

    public Map<String, Map<String, List<Mp3Info>>> getMapForCreatingHTML() {
        return mapForCreatingHTML;
    }
    public void setMapForCreatingHTML(Map<String, Map<String,List<Mp3Info>>> mapForCreatingHTML) {
        this.mapForCreatingHTML = mapForCreatingHTML;
    }

    public void addMp3InfoToTree(List<Mp3Info> listInfo){
        for (Mp3Info fileInfo : listInfo) {
            if ((! mapForCreatingHTML.containsKey(fileInfo.getArtist())) ||  (mapForCreatingHTML.isEmpty())) {
                ArrayList<Mp3Info> songs = new ArrayList<>();
                songs.add(fileInfo);
                Map<String,List<Mp3Info>> albums = new HashMap<>();
                albums.put(fileInfo.getAlbum(), songs);
                mapForCreatingHTML.put(fileInfo.getArtist(), albums);
            } else if (! mapForCreatingHTML.get(fileInfo.getArtist()).containsKey(fileInfo.getAlbum())) {
                ArrayList<Mp3Info> songs = new ArrayList<>();
                songs.add(fileInfo);
                mapForCreatingHTML.get(fileInfo.getArtist()).put(fileInfo.getAlbum(), songs);
            } else {
                mapForCreatingHTML.get(fileInfo.getArtist()).get(fileInfo.getAlbum()).add(fileInfo);
            }
        }
    }

    public void generateHtmlReport(PrintWriter printWriter){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry <String, Map<String, List <Mp3Info>>> entry1 : mapForCreatingHTML.entrySet() ) {
            sb.append("<tr><h1>Author: ").append(entry1.getKey()).append("</h1>");
            for(Map.Entry<String, List<Mp3Info>> entry2: entry1.getValue().entrySet()){
                sb.append("<tr><h2>Album: ").append(entry2.getKey()).append("</h2>");
                sb.append("<table border=\"1\" style=\"width:100%\">");
                sb.append("<tr><td><h3>Track name</h3></td>\n<td width = 200><h3>Track length, mm : ss </h3>" +
                        "</td>\n<td width = 200><h3>Link</h3></td>\n</tr>");
                for(Mp3Info mp3Info : entry2.getValue()){
                    sb.append("<tr><td>").append(mp3Info.getTitle()).append("</td>\n<td>")
                            .append(mp3Info.durationToString()).append("</td>\n<td><a href=\"")
                            .append(mp3Info.getPath().toUri().toString())
                            .append("\">Play</a>\n</td>\n</tr>\n");
                }
                sb.append("</table>\n");
                printWriter.print(sb);
                printWriter.flush();
                sb = new StringBuilder();

            }
        }

    }
}
