package ua.javarush.caesar.controller;

import ua.javarush.caesar.fileservice.FileEncodingProcessor;
import ua.javarush.caesar.service.EncodingOptions;
import ua.javarush.caesar.view.ConsoleViewProvider;

public class ApplicationController {

    private final FileEncodingProcessor fileEncodingProcessor;
    private final ConsoleViewProvider consoleViewProvider;

    public ApplicationController(FileEncodingProcessor fileEncodingProcessor, ConsoleViewProvider consoleViewProvider) {
        this.fileEncodingProcessor = fileEncodingProcessor;
        this.consoleViewProvider = consoleViewProvider;
    }

    public void run(String[] args) {
        validateParameters(args);
        consoleViewProvider.print("Кількість і тип параметрів були вказані вірно");
        consoleViewProvider.print("Починається процес кодування/декодування...");

        EncodingOptions encodingOptions = EncodingOptions.valueOf(args[0].toUpperCase());
        String sourceFilePath = args[1];
        int key = args.length == 3 ? Integer.parseInt(args[2]) : 0;

        switch (encodingOptions) {
            case ENCRYPT -> fileEncodingProcessor.encode(sourceFilePath, key);
            case DECRYPT -> fileEncodingProcessor.decode(sourceFilePath, key);
            case BRUTE_FORCE ->
                consoleViewProvider.print("Знайдено ключ = " + fileEncodingProcessor.executeBruteForce(sourceFilePath));
        }
        consoleViewProvider.print("Процес кодування/декодування завершився успішно \nПеревірте кінцевий файл");
    }

    private void validateParameters(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Не було вказано жодного параметра!");
        }

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Введено неправильну " +
                "кількість параметрів!");

        if (args.length != 3 && args.length != 2) {
            throw illegalArgumentException;
        }

        String action = args[0].toUpperCase();
        EncodingOptions encodingOptions;
        try {
            encodingOptions = EncodingOptions.valueOf(action);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Опцію command вказано неправильно!");
        }

        if (encodingOptions == EncodingOptions.ENCRYPT || encodingOptions == EncodingOptions.DECRYPT) {
            if (args.length != 3) {
                throw illegalArgumentException;
            }
        } else if (encodingOptions == EncodingOptions.BRUTE_FORCE && args.length != 2) {
            throw illegalArgumentException;
        }
    }
}
