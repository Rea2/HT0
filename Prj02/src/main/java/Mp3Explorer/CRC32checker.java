package Mp3Explorer;

import java.io.*;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

/**
 * Created by Raik Yauheni on 04.11.2018.
 */

public class CRC32checker {
    public static final String ERROR_READING_FILE_MESSAGE = "Error: Can't read file. CRC32 can not be check";

    public  Map<Path, Long> calcCrc32All(Collection<File> collection) {
        Map<Path, Long> map = new HashMap<>();
        long crc32 = 0L;
        for(File file : collection) {
            try {
                crc32 = checkCRC32(file);
            } catch (IOException e) {
                System.out.println(ERROR_READING_FILE_MESSAGE);
                continue;
            }
            map.put(file.toPath(), crc32);
        }
        return map;
    }
    public long checkCRC32(File file) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        CRC32 crc = new CRC32();
        int cnt;
        while ((cnt = inputStream.read()) != -1) {
            crc.update(cnt);
        }
        return crc.getValue();
    }


}