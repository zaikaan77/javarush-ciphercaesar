package ua.javarush.caesar.fileservice;

import ua.javarush.caesar.service.EncodingOptions;

import java.nio.file.Path;

public class FileService {

    private static final String ENCRYPTED = "[ENCRYPTED]";
    private static final String DECRYPTED = "[DECRYPTED]";
    private static final String BRUTE_FORCE = "_KEY_";

    public String createTargetFilename(String sourceFilename, EncodingOptions type, int key) {
        Path path = Path.of(sourceFilename);
        Path pathWithoutFilename = path.getParent();
        String fileName = path.getFileName().toString();
        String targetFilename;

        switch (type) {
            case ENCRYPT -> {
                if (fileName.contains(DECRYPTED)) {
                    fileName = fileName.replace(DECRYPTED, "");
                }
                targetFilename = renameFilenameToTargetFilename(fileName, pathWithoutFilename, ENCRYPTED);
                return targetFilename;
            }
            case DECRYPT -> {
                if (fileName.contains(ENCRYPTED)) {
                    fileName = fileName.replace(ENCRYPTED, "");
                }
                targetFilename = renameFilenameToTargetFilename(fileName, pathWithoutFilename, DECRYPTED);
                return targetFilename;
            }
            case BRUTE_FORCE -> {
                targetFilename = renameFilenameToTargetFilename(fileName, pathWithoutFilename, BRUTE_FORCE + key);
                return targetFilename;
            }
            default -> {
                return sourceFilename;
            }
        }
    }

    private String renameFilenameToTargetFilename(String fileName, Path pathWithoutFilename, String encodingStatus) {
        int indexOfPoint = fileName.lastIndexOf(".");
        String newFileName = new StringBuilder(fileName).insert(indexOfPoint, encodingStatus).toString();
        return pathWithoutFilename + "\\" + newFileName;
    }
}
