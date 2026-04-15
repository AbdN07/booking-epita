package dev._xdbe.booking.creelhouse.infrastructure.persistence;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import dev._xdbe.booking.creelhouse.infrastructure.persistence.CryptographyHelper;

@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {

    @Autowired
    private CryptographyHelper cryptographyHelper;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        // Step 7a: Encrypt the PAN before storing it in the database
        return attribute;
        // Step 7a: End of PAN encryption
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        // Step 7b: Decrypt the PAN when reading it from the database
        String pan = dbData;
        // Step 7b: End of PAN decryption
        String maskedPanString = panMasking(pan);
        return maskedPanString;
    }

    private String panMasking(String pan) {
        // Step 6:
        if (pan == null || pan.length() <= 8) {
            return pan;
        }

       int longueur = pan.length();
       String debut = pan.substring(0, 4);
       String fin = pan.substring(longueur - 4);
       String mil = "*".repeat(longueur - 8);

       return debut + mil + fin;
       // Step 6: End
    }
}
