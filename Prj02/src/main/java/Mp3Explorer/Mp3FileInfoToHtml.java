package Mp3Explorer;

import java.io.*;
import java.util.*;

/**
 * Created by Raik Yauheni on 04.11.2018.
 */
public class Mp3FileInfoToHtml {

    private Map<String, TreeMap<String, ArrayList<Mp3Info>>> treeMap = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    });

    public Map<String, TreeMap<String, ArrayList<Mp3Info>>> getTreeMap() {
        return treeMap;
    }
    public void setTreeMap(Map<String, TreeMap<String, ArrayList<Mp3Info>>> treeMap) {
        this.treeMap = treeMap;
    }

    public void addMp3InfoToTree(List<Mp3Info> listInfo){
        for (Mp3Info fileInfo : listInfo) {
            if ((! treeMap.containsKey(fileInfo.getArtist())) ||  (treeMap.isEmpty())) {
                ArrayList<Mp3Info> songs = new ArrayList<>();
                songs.add(fileInfo);
                TreeMap<String, ArrayList<Mp3Info>> albums = new TreeMap<>();
                albums.put(fileInfo.getAlbum(), songs);
                treeMap.put(fileInfo.getArtist(), albums);
            } else if (! treeMap.get(fileInfo.getArtist()).containsKey(fileInfo.getAlbum())) {
                ArrayList<Mp3Info> songs = new ArrayList<>();
                songs.add(fileInfo);
                treeMap.get(fileInfo.getArtist()).put(fileInfo.getAlbum(), songs);
            } else {
                treeMap.get(fileInfo.getArtist()).get(fileInfo.getAlbum()).add(fileInfo);
            }
        }
    }

    public String genereateHtmlReport(PrintWriter printWriter){

        StringBuilder sb = new StringBuilder();

        for(Map.Entry <String, TreeMap<String, ArrayList <Mp3Info>>> entry1 : treeMap.entrySet() ) {
            sb.append("<tr><h1>Author: ").append(entry1.getKey()).append("</h1>");
            for(Map.Entry<String, ArrayList<Mp3Info>> entry2: entry1.getValue().entrySet()){
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
                sb = new StringBuilder();
            }
        }
        return sb.toString();
    }
}
