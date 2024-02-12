package ua.javarush.ciphercaesar.caesarcipher;

public class CaesarCipher {
    private final Alphabet alphabet;

    public CaesarCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public char[] encode(char[] symbols, int key) {
        return cipher(symbols, key);
    }

    public char[] decode(char[] symbols, int key) {
        return cipher(symbols, -key);
    }

    private char[] cipher(char[] chars, int key) {
        char[] symbols = new char[chars.length];

        for (int i = 0; i < chars.length; i++) {
            symbols[i] = alphabet.characterEncoding(chars[i], key);
        }
        return symbols;
    }
}
