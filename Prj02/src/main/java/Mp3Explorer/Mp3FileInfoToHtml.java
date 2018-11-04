package Mp3Explorer;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.*;

/**
 * Created by Raik Yauheni on 04.11.2018.
 */
public class Mp3FileInfoToHtml {
    private Map<String, TreeMap<String, ArrayList<Mp3FileInfo>>> treeMap = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    });

    public Map<String, TreeMap<String, ArrayList<Mp3FileInfo>>> getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(Map<String, TreeMap<String, ArrayList<Mp3FileInfo>>> treeMap) {
        this.treeMap = treeMap;
    }

    public void addMp3FilesInfoToTree (List<Mp3FileInfo> listInfo){
        for (Mp3FileInfo fileInfo : listInfo) {
            if ((! treeMap.containsKey(fileInfo.getArtist())) ||  (treeMap.isEmpty())) {
                ArrayList<Mp3FileInfo> songs = new ArrayList<>();
                songs.add(fileInfo);
                TreeMap<String, ArrayList<Mp3FileInfo>> albums = new TreeMap<>();
                albums.put(fileInfo.getAlbum(), songs);
                treeMap.put(fileInfo.getArtist(), albums);
//                System.out.println("1");
            } else if (! treeMap.get(fileInfo.getArtist()).containsKey(fileInfo.getAlbum())) {
                ArrayList<Mp3FileInfo> songs = new ArrayList<>();
                songs.add(fileInfo);
                treeMap.get(fileInfo.getArtist()).put(fileInfo.getAlbum(), songs);
//                System.out.println("2");
            } else {treeMap.get(fileInfo.getArtist()).get(fileInfo.getAlbum()).add(fileInfo);
//            System.out.println("added");
            }
        }
    }

    public String genereateHtmlReport(PrintWriter printWriter){

        StringBuilder sb = new StringBuilder();

        for(Map.Entry <String, TreeMap<String, ArrayList <Mp3FileInfo>>> entry1 : treeMap.entrySet() ) {
            sb.append("<tr><h1>Author: ").append(entry1.getKey()).append("</h1>");
            for(Map.Entry<String, ArrayList<Mp3FileInfo>> entry2: entry1.getValue().entrySet()){
                sb.append("<tr><h2>Album: ").append(entry2.getKey()).append("</h2>");
                sb.append("<table border=\"1\" style=\"width:100%\">");
                sb.append("<tr><td><h3>Track name</h3></td>\n<td width = 200><h3>Track length, mm : ss </h3>" +
                        "</td>\n<td width = 200><h3>Link</h3></td>\n</tr>");
                for(Mp3FileInfo mp3FileInfo : entry2.getValue()){
                    sb.append("<tr><td>").append(mp3FileInfo.getTitle()).append("</td>\n<td>")
                            .append(mp3FileInfo.durationToString()).append("</td>\n<td><a href=\"")
                            .append(mp3FileInfo.getPath().toUri().toString())
                            .append("\">Play</a>\n</td>\n</tr>\n");
                }
                sb.append("</table>\n");
                printWriter.print(sb);
                sb = new StringBuilder();
            }
        }
        return sb.toString();
    }







//    public void printSizeTreeMap() {
//        System.out.println("Authors: " + treeMap.size());
//        for (Map.Entry<String, TreeMap<String, ArrayList<Mp3FileInfo>>> entry1 : treeMap.entrySet()) {
//            System.out.println("-----------------------");
//            System.out.println("Author:" + entry1.getKey() +" Количество: "+ entry1.getValue().size());
//            for (Map.Entry<String, ArrayList<Mp3FileInfo>> entry2 : entry1.getValue().entrySet()) {
//                System.out.print(TAB);
//                System.out.println("Album:" + entry2.getKey() + " size-" + entry2.getValue().size());
//                for(Mp3FileInfo mp3FileInfo : entry2.getValue()){
//                    System.out.print(TAB);
//                    System.out.print(TAB);
//                    System.out.println(mp3FileInfo.getTitle() + " " + mp3FileInfo.durationToString() + " " +   mp3FileInfo.getPath().toFile().getName());
//                }
//                System.out.println("-----------------------");
//
//     }





}
