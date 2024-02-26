package ua.javarush.caesar.service;

import ua.javarush.caesar.alphabet.Alphabet;

import java.util.Arrays;

public class StatisticalAnalysisDecoder {

    // Інформація стосовно частоти використання літер англійського алфавіту взята із джерела:
    // https://en.wikipedia.org/wiki/Letter_frequency
    protected static final double[] ENGLISH_FREQUENCIES = {
            0.08167,  // a
            0.01492,  // b
            0.02782,  // c
            0.04253,  // d
            0.12702,  // e
            0.02228,  // f
            0.02015,  // g
            0.06094,  // h
            0.06966,  // i
            0.00153,  // j
            0.00772,  // k
            0.04025,  // l
            0.02406,  // m
            0.06749,  // n
            0.07507,  // o
            0.01929,  // p
            0.00095,  // q
            0.05987,  // r
            0.06327,  // s
            0.09056,  // t
            0.02758,  // u
            0.00978,  // v
            0.02360,  // w
            0.00150,  // x
            0.01974,  // y
            0.00074   // z
    };
    private static final double[] languageFrequencies = ENGLISH_FREQUENCIES;
    private final int[] encryptedFrequencies = new int[Alphabet.LANGUAGE_ALPHABET.length()];

    public int findEncodingKey(String encryptedText) {
        calculateSymbolUsageFrequency(encryptedText);

        int bestKey = 0;
        double difference;
        double minDifference = Double.MAX_VALUE;

        for (int key = 1; key <= Alphabet.LANGUAGE_ALPHABET.length(); key++) {
            difference = computeDifference(languageFrequencies, encryptedFrequencies, key, encryptedText.length());

            if (difference < minDifference) {
                minDifference = difference;
                bestKey = key;
            }
        }

        return bestKey;
    }

    private void calculateSymbolUsageFrequency(String encryptedText) {
        char[] languageAlphabetToCharSymbol = Alphabet.LANGUAGE_ALPHABET.toCharArray();

        for (int i = 0; i < encryptedText.length(); i++) {
            char symbol = encryptedText.charAt(i);

            if (Character.isLetter(symbol)) {
                int index = Arrays.binarySearch(languageAlphabetToCharSymbol, Character.toLowerCase(symbol));

                if (index >= 0) {
                    encryptedFrequencies[index]++;
                }
            }
        }
    }

    private double computeDifference(double[] languageFrequencies,
                                     int[] encryptedFrequencies,
                                     int key,
                                     int encryptedTextLength) {
        double difference = 0;

        for (int i = 1; i <= ENGLISH_FREQUENCIES.length; i++) {
            difference += Math.abs(languageFrequencies[i - 1] -
                    (double) encryptedFrequencies[(i + key - 1) % ENGLISH_FREQUENCIES.length] / encryptedTextLength);
        }

        return difference;
    }
}
