package Mp3Explorer;

import com.mpatric.mp3agic.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Raik Yauheni
 */
public class CreatorMp3Info {
    public static final String CAN_NOT_ADD_FILE_IN_THE_REPORT  = "The file will not add in HTML report";
    private List<File> mp3Files = new ArrayList<>();
    private List<File> badMp3Files = new ArrayList<>();
    private List<Mp3Info> listMp3FilesInfo = new ArrayList<>();

    public List<File> getMp3Files() {
        return mp3Files;
    }

    public List<File> getBadMp3Files() {
        return badMp3Files;
    }

    public List<Mp3Info> getListMp3FilesInfo() {
        return listMp3FilesInfo;
    }

    public List<Mp3Info> getListOfFileInfo(List<File> mp3Files)  {
        List<Mp3Info> result = new ArrayList<>();
        Mp3Info mp3Info;
        for(File file : mp3Files) {
            mp3Info = null;
            try {
                mp3Info = createMp3FileInfo(file);
            } catch (InvalidDataException e) {
                System.out.println(file.getName() + " has invalid format or is corrupted. "+
                        CAN_NOT_ADD_FILE_IN_THE_REPORT);
                badMp3Files.add(file);
            } catch (IOException e) {
                System.out.println("Could not read \""+ file.getName()+ "\" " + CAN_NOT_ADD_FILE_IN_THE_REPORT);
                badMp3Files.add(file);
            } catch (UnsupportedTagException e) {
                System.out.println("\""+file.getName() + "\" Unknown ID3 tags type. The file will add to the report without tags");
                badMp3Files.add(file);
            }  catch (Exception e) {
                System.out.println("Unknown error during reading \"" +   file.getName() + "\" " + CAN_NOT_ADD_FILE_IN_THE_REPORT );
            }  if (mp3Info!= null) {
              result.add(mp3Info);
           }
        }
        return result;
    }

    public Mp3Info createMp3FileInfo(File file) throws InvalidDataException, IOException, UnsupportedTagException, IllegalArgumentException {
        Mp3File mp3file = new Mp3File(file);
        Mp3Info fileInfo;
        if  (mp3file.hasId3v2Tag()) {
            fileInfo = createMp3FileInfoId3v2(mp3file);
        } else if (mp3file.hasId3v1Tag()) {
            fileInfo =  createMp3FileInfoId3v1(mp3file);
        } else {
            fileInfo = new Mp3Info();
        }
        fileInfo.setDuration(mp3file.getLengthInSeconds());
        fileInfo.setPath(file.toPath());
        return fileInfo;
    }

    private Mp3Info createMp3FileInfoId3v1(Mp3File mp3file)  {
        Mp3Info mp3Info = new Mp3Info();
        ID3v1 id3v1Tag = mp3file.getId3v1Tag();
        mp3Info.setArtist(id3v1Tag.getArtist());
        mp3Info.setAlbum(id3v1Tag.getAlbum());
        mp3Info.setTitle(id3v1Tag.getTitle());
        return mp3Info;
    }

    private Mp3Info createMp3FileInfoId3v2 (Mp3File mp3file)  {
        Mp3Info mp3Info = new Mp3Info();
        ID3v2 id3v2Tag = mp3file.getId3v2Tag();
        mp3Info.setArtist(id3v2Tag.getArtist());
        mp3Info.setAlbum(id3v2Tag.getAlbum());
        mp3Info.setTitle(id3v2Tag.getTitle());
        return mp3Info;
    }
}
