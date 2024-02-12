package ua.javarush.ciphercaesar.caesarcipher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Alphabet {
    private final List<Character> symbols;

    public Alphabet(List<Character> symbols) {
        this.symbols = Collections.unmodifiableList(symbols);
    }

    public Character characterEncoding(Character original, int shift) {
        if (!symbols.contains(Character.toLowerCase(original))) {
            return original;
        }
        int newIndex = Math.floorMod((symbols.indexOf(Character.toLowerCase(original)) + shift), symbols.size());

        return Character.isUpperCase(original) ? Character.toUpperCase(symbols.get(newIndex)) : symbols.get(newIndex);
    }

//    public static void main(String[] args) {
//        List<Character> symbols = Arrays.asList('p', 'r', 'c', '1', 'i');
//        Alphabet alphabet = new Alphabet(symbols);
//
//        Character result = alphabet.characterEncoding('c', 0);
//        System.out.println(result);
//    }
}