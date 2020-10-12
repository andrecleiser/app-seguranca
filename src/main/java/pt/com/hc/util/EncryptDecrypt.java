package pt.com.hc.util;

import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EncryptDecrypt {

    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private final KeySpec ks;
    private final SecretKeyFactory skf;
    private final Cipher cipher;
    byte[] arrayBytes;
    private final String myEncryptionKey;
    private final String myEncryptionScheme;
    SecretKey key;

    public EncryptDecrypt() throws Exception {
        myEncryptionKey = "app-sec-hccopr-7fcdb6a7-8007-4381-b591-042e8fd4838a";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }

    public String encrypt(final String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            final byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            final byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.getEncoder().encode(encryptedText));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }

    public String decrypt(final String encryptedString) {
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            final byte[] encryptedText = Base64.getDecoder().decode(encryptedString.getBytes());
            final byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
}
