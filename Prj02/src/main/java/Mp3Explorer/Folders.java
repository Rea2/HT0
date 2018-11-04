package Mp3Explorer;

import com.mpatric.mp3agic.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raik Yauheni on 30.10.2018.
 */
public class Folders {

    private List<File> mp3Files = new ArrayList<>();
    private List<Mp3FileInfo> listMp3FilesInfo = new ArrayList<>();
    private List<File> badMp3Files = new ArrayList<>();

    public List<File> getMp3Files() {
        return mp3Files;
    }

    public  List<Mp3FileInfo> getMp3Id3TagsInfo(String FolderPath)  {
        File folder = new File(FolderPath);
        mp3Files = getListMp3Files(folder);
        listMp3FilesInfo =  createListOfMp3FileInfo(mp3Files);
        return listMp3FilesInfo;
    }
    public  List<File> getListMp3Files(File folder) {
       for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    getListMp3Files(fileEntry);
                } else {
                    if (fileEntry.getName().substring(fileEntry.getName().length()-4).equalsIgnoreCase(".mp3")) {
                        mp3Files.add(fileEntry);
                    }
                }
        }
        return mp3Files;
    }

    public List<Mp3FileInfo> createListOfMp3FileInfo (List<File> mp3Files)  {
        List<Mp3FileInfo> result = new ArrayList<>();
        for(File file : mp3Files) {
            try {
                result.add(createMp3FileInfo(file) );
            } catch (InvalidDataException e) {
                System.out.println(file.getName() + " has invalid format or is corrupted. It'll not add in HTML report");
                badMp3Files.add(file);
            } catch (IOException e) {

                e.printStackTrace();
            } catch (UnsupportedTagException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public  Mp3FileInfo createMp3FileInfo(File file) throws InvalidDataException, IOException, UnsupportedTagException {
        Mp3File mp3file = new Mp3File(file);
        Mp3FileInfo fileInfo;

        if (mp3file.hasId3v1Tag()) {
//            System.out.println("ID3 v1 created");
            fileInfo =  createMp3FileInfoId3v1(mp3file);
        } else if (mp3file.hasId3v2Tag()) {
//            System.out.println("ID3 v2 created");
            fileInfo = createMp3FileInfoId3v2(mp3file);
        } else {
            fileInfo = new Mp3FileInfo();
        }
        fileInfo.setDuration(mp3file.getLengthInSeconds());
        fileInfo.setPath(file.toPath());
        return fileInfo;
    }

    private Mp3FileInfo createMp3FileInfoId3v1(Mp3File mp3file)  {
        Mp3FileInfo mp3FileInfo = new Mp3FileInfo();
        ID3v1 id3v1Tag = mp3file.getId3v1Tag();
        mp3FileInfo.setArtist(id3v1Tag.getArtist() );
        mp3FileInfo.setAlbum(id3v1Tag.getAlbum() );
        mp3FileInfo.setTitle(id3v1Tag.getTitle() );
        return  mp3FileInfo;
    }

    private Mp3FileInfo createMp3FileInfoId3v2 (Mp3File mp3file)  {
        Mp3FileInfo mp3FileInfo = new Mp3FileInfo();
        ID3v2 id3v2Tag = mp3file.getId3v2Tag();
//        id3v2Tag.setEncoder("1");
//        System.out.println("Encoder:" + id3v2Tag.getEncoder());
        mp3FileInfo.setArtist(id3v2Tag.getArtist() );
        mp3FileInfo.setAlbum(id3v2Tag.getAlbum() );
        mp3FileInfo.setTitle(id3v2Tag.getTitle() );
        return  mp3FileInfo;
    }

//    public File getHtmlReport() {
//        HtmlView viewDetails = HtmlView
//        .html()
//            .head()
//                .title().text("MP3 Files Report").º() //title
//            .º() //head
//
//            .body()
//
//
//        .h1().text("Task Details").º()
//        .hr().º()
//        .div().text("Title: ISEL MPD project")
//        .br().º()
//        .text("Description: A Java library for serializing objects in HTML.")
//        .br().º()
//        .text("Priority: HIGH")
//        .º() //div
//        .º() //body
//        .º(); //html
//
//
//    }



















}
