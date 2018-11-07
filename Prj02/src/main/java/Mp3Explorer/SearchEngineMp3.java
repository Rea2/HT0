package Mp3Explorer;

import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * @author Raik Yauheni
 */
public class SearchEngineMp3 {
    public static final String TAB = "   ";
    private List<File> mp3Files = new ArrayList<>();

    public List<File> getMp3Files() {
        return mp3Files;
    }

    public  List<File> getListMp3Files(File folder) {
        for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    getListMp3Files(fileEntry);
                } else {
                    String[] strings = fileEntry.getName().split("[.]");//
                        if (strings.length != 0) {
                            if (strings[(strings.length)-1].trim().equalsIgnoreCase("mp3")) {
                                mp3Files.add(fileEntry);
                            }
                        }
                     }
        }
        return mp3Files;
    }

    public Map<Long, HashSet<Path>> getDuplicateFiles (Collection<File> listFiles) {
        System.out.println("Finding duplicates file ...");
        Map<Long, Path> mapTemp = new HashMap<>();
        Map<Long, HashSet<Path>> mapDuplicates = new HashMap<>();
        CRC32checker checkerSum = new CRC32checker();
        Path path= null;
        long crc = 0L;
        for(File file : listFiles) {
            try {
                crc = checkerSum.checkCRC32(file);
            } catch (IOException e) {
                System.out.println(CRC32checker.ERROR_READING_FILE_MESSAGE);
            }
            path =  mapTemp.put(crc,file.toPath());
            if (path != null) {
                if (!(mapDuplicates.containsKey(crc))){
                    HashSet<Path> set = new HashSet<>();
                    set.add(path);
                    mapDuplicates.put(crc, set);
                } else {
                    mapDuplicates.get(crc).add(path);
                }
                path = null;
            }
        }
        for(long lo : mapDuplicates.keySet()) {
            if(mapTemp.containsKey(lo)) {
                mapDuplicates.get(lo).add(mapTemp.get(lo));
            }
        }
        return mapDuplicates;
    }

    public Map<Integer, List<Mp3Info>> getDuplicateTags (Collection<Mp3Info> listFiles) {
        System.out.println("Finding duplicates ID3 tags ...");
        Map<Integer, Mp3Info> mapTemp = new HashMap<>();
        Map<Integer, List<Mp3Info>> mapDuplicates = new HashMap<>();
        Mp3Info mp3Temp;
        int hashTemp = 0;
        for(Mp3Info mp3Info : listFiles) {
               hashTemp = mp3Info.hashCode();
               mp3Temp = mapTemp.put(hashTemp,  mp3Info);// if mapTemp has entry with key hashTemp,
                                                         // it it will change value by a new one and return old value
            if (mp3Temp != null) {
                if (!(mapDuplicates.containsKey(hashTemp))){
                    List<Mp3Info> list = new ArrayList<>();
                    list.add(mp3Temp);
                    mapDuplicates.put(hashTemp, list);
                } else {
                    mapDuplicates.get(hashTemp).add(mp3Temp);
                }
                mp3Temp = null;
            }
        }
        for(int i : mapDuplicates.keySet()) {
            if(mapTemp.containsKey(i)) {
                mapDuplicates.get(i).add(mapTemp.get(i));
            }
        }
        return mapDuplicates;
    }

    public void printDuplicatesFiles(Map<Long, HashSet<Path>> mapDuplicates, Logger log){
        int count1 = 0;// counter of combinations for every CRC32
        int count2 = 0;
        for(Map.Entry<Long, HashSet<Path>>  entryMap : mapDuplicates.entrySet()) {
            log.info("Duplicates " + (++count1) + " (CRC32: " +  entryMap.getKey() + ")") ;
            count2 = 0;
            for(Path path : entryMap.getValue()) {
                log.info((++count2) + "." + path.toAbsolutePath().toString());
            }
            log.info(Runner.DELIMITER_STRINGS);
        }
    }
    /**
     *
     * @param mapDuplicates
     *        map of duplicates songs Map<Integer{hashcode of Mp3Info}, List<Mp3Info>>
     *        with the same ID3 tags "Title", "Author" , "Album"
     *
     * @param log
     *
     */
    public void printDuplicatesTags (Map<Integer, List<Mp3Info>> mapDuplicates, Logger log){
        int count1 = 0; // counter of combinations "artist + album + title"
        int count2 = 0; // counter of duplicates into every combination "artist + album + title"
        log.info(Runner.DELIMITER_STRINGS);
        log.info(Runner.DELIMITER_STRINGS);
        log.info("Tags duplicates:");
        log.info(Runner.DELIMITER_STRINGS);
        for(Map.Entry <Integer, List<Mp3Info>>  entryMap : mapDuplicates.entrySet()) {
            log.info(++count1  + ".Artsit - " + entryMap.getValue().get(0).getArtist() +
                            "| Album - " + entryMap.getValue().get(0).getAlbum()+
                            "| Song - " + entryMap.getValue().get(0).getTitle()+ " :");
            count2 = 0;
            for (Mp3Info mp3Info : entryMap.getValue()) {
                log.info(TAB + (++count2) + "." + mp3Info.getPath().toAbsolutePath().toString());
            }
            log.info(Runner.DELIMITER_STRINGS);
        }
    }












}
