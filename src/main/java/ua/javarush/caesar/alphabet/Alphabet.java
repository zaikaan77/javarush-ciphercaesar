package ua.javarush.caesar.alphabet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Alphabet {

    public static final List<Character> ALPHABET_ENGLISH = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    public static final String LANGUAGE_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private final List<Character> symbols;

    public Alphabet(List<Character> symbols) {
        this.symbols = Collections.unmodifiableList(symbols);
    }

    public Character symbolEncoding(Character original, int shift) {
        if (!symbols.contains(Character.toLowerCase(original))) {
            return original;
        }
        int newIndex = Math.floorMod((symbols.indexOf(Character.toLowerCase(original)) + shift), symbols.size());

        return Character.isUpperCase(original) ? Character.toUpperCase(symbols.get(newIndex)) : symbols.get(newIndex);
    }
}