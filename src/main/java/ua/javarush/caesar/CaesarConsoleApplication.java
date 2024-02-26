package ua.javarush.caesar;

import ua.javarush.caesar.alphabet.Alphabet;
import ua.javarush.caesar.caesarcipher.CaesarCipher;
import ua.javarush.caesar.controller.ApplicationController;
import ua.javarush.caesar.fileservice.FileEncodingProcessor;
import ua.javarush.caesar.fileservice.FileService;
import ua.javarush.caesar.service.StatisticalAnalysisDecoder;
import ua.javarush.caesar.view.ConsoleViewProvider;

public class CaesarConsoleApplication {

    public static void main(String[] args) {
        int bufferSize = 128;
        Alphabet alphabet = new Alphabet(Alphabet.ALPHABET_ENGLISH);
        CaesarCipher caesarCipher = new CaesarCipher(alphabet);
        FileService fileService = new FileService();
        StatisticalAnalysisDecoder statisticalAnalysisDecoder = new StatisticalAnalysisDecoder();
        FileEncodingProcessor fileEncodingProcessor = new FileEncodingProcessor(caesarCipher, fileService,
                statisticalAnalysisDecoder, bufferSize);
        ConsoleViewProvider consoleViewProvider = new ConsoleViewProvider();

        new ApplicationController(fileEncodingProcessor, consoleViewProvider).run(args);
    }
}
