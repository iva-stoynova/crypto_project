/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;


import java.util.function.Function;

/**
 *
 * @author grade
 */
public class CryptoUtils {

    private String cipher;

    public CryptoUtils(String cipher) {
        setCipher(cipher);
    }

    public CryptoUtils() {
        this("TIGER");
    }

    public String getCipher() {
        return cipher;
    }

    public final void setCipher(String cipher) {
        this.cipher = (cipher != null) ? cipher : "TIGER";
    }

    @Override
    public String toString() {
        return "CryptoUtils{ " + "cipher = " + cipher + '}';
    }

    private class VigenereEncryption implements IEncryptable {

        @Override
        public String encrypt(String plainText) {
            char plainTextChars[] = plainText.toCharArray();
            char resultChars[] = new char[plainText.length() + 1];
            char cipherChars[] = cipher.toCharArray();
            int offset = 0;

            for (int i = 0; i < plainTextChars.length; i++) {
                if (Character.isAlphabetic(plainTextChars[i])) {
                    if (Character.isUpperCase(plainTextChars[i])) {
                        resultChars[i] = (char) (((plainTextChars[i] - 'A' + (cipherChars[offset] - 'A')) % 26) + 'A');
                    } else if (Character.isLowerCase(plainTextChars[i])) {
                        resultChars[i] = (char) (((plainTextChars[i] - 'a' + (cipherChars[offset] - 'A')) % 26) + 'a');
                    }
                    offset++;
                    if (offset >= cipherChars.length) {
                        offset = 0;
                    }
                } else {
                    resultChars[i] = plainTextChars[i];
                    offset = 0;

                }
            }
        
            String result = new String(resultChars);
            return result;

        }

        @Override
        public String decrypt(String cipherText) {
            char cipherTextChars[] = cipherText.toCharArray();
            char resultChars[] = new char[cipherText.length() + 1];
            char cipherChars[] = cipher.toCharArray();

            int offset = 0;
            
            for (int i = 0; i < cipherTextChars.length; i++) {
                if (Character.isAlphabetic(cipherTextChars[i])) {
                    if (Character.isUpperCase(cipherTextChars[i])) {
                        resultChars[i] = (char)(((cipherTextChars[i] - 'A' - (cipherChars[offset] - 'A') + 26) % 26) + 'A');
                    } else if (Character.isLowerCase(cipherTextChars[i])) {
                        resultChars[i] = (char)(((cipherTextChars[i] - 'a' - (cipherChars[offset] - 'A') + 26) % 26) + 'a');
                    }
                    offset++;
                    if (offset >= cipherChars.length) {
                        offset = 0;
                    }

                } else {
                    resultChars[i] = cipherTextChars[i];
                    offset = 0;
                }
            }
            String result = new String(resultChars);
            return result;
        }
    }

    public Function<String, String> getEncrypt() {
        return (plainText) -> new VigenereEncryption().encrypt(plainText);
    }

    public Function<String, String> getDecrypt() {
        return (cipherText) -> new VigenereEncryption().decrypt(cipherText);
    }
}
