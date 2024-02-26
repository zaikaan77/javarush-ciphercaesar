package ua.javarush.caesar.fileservice;

import ua.javarush.caesar.caesarcipher.CaesarCipher;
import ua.javarush.caesar.service.EncodingOptions;
import ua.javarush.caesar.service.StatisticalAnalysisDecoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileEncodingProcessor {

    private final CaesarCipher caesarCipher;
    private final FileService fileService;
    private final StatisticalAnalysisDecoder statisticalAnalysisDecoder;
    private final int bufferSize;

    public FileEncodingProcessor(CaesarCipher caesarCipher, FileService fileService,
                                 StatisticalAnalysisDecoder statisticalAnalysisDecoder, int bufferSize) {
        this.caesarCipher = caesarCipher;
        this.fileService = fileService;
        this.statisticalAnalysisDecoder = statisticalAnalysisDecoder;
        this.bufferSize = bufferSize;
    }

    public void encode(String sourceFilename, int key) {
        String targetFilename = fileService.createTargetFilename(sourceFilename, EncodingOptions.ENCRYPT, key);
        createTargetFile(targetFilename);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetFilename));
             BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFilename))) {
            char[] buffer = new char[bufferSize];
            int numberOfSymbols;

            while ((numberOfSymbols = bufferedReader.read(buffer)) != -1) {
                bufferedWriter.write(caesarCipher.encode(buffer, key), 0, numberOfSymbols);
                bufferedWriter.flush();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String decode(String sourceFilename, int key) {
        String targetFilename = fileService.createTargetFilename(sourceFilename, EncodingOptions.DECRYPT, key);
        createTargetFile(targetFilename);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetFilename));
             BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFilename))) {
            char[] buffer = new char[bufferSize];
            int numberOfSymbols;

            while ((numberOfSymbols = bufferedReader.read(buffer)) != -1) {
                bufferedWriter.write(caesarCipher.decode(buffer, key), 0, numberOfSymbols);
                bufferedWriter.flush();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return targetFilename;
    }

    public int executeBruteForce(String sourceFilename) {
        int key;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFilename))) {
            StringBuilder encryptedTextBuilder = new StringBuilder();
            char[] buffer = new char[bufferSize];
            int numberOfSymbols;

            while ((numberOfSymbols = bufferedReader.read(buffer)) != -1) {
                encryptedTextBuilder.append(buffer, 0, numberOfSymbols);
            }

            String encryptedText = encryptedTextBuilder.toString();
            key = statisticalAnalysisDecoder.findEncodingKey(encryptedText);

            String oldName = decode(sourceFilename, key);
            String newName = fileService.createTargetFilename(oldName, EncodingOptions.BRUTE_FORCE, key);
            renameFile(oldName, newName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return key;
    }

    private void renameFile(String oldName, String newName) {
        Path source = Path.of(oldName);
        Path target = Path.of(newName);
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTargetFile(String targetFilename) {
        try {
            if (Files.exists(Path.of(targetFilename))) {
                Files.delete(Path.of(targetFilename));
            }
            Files.createFile(Path.of(targetFilename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
