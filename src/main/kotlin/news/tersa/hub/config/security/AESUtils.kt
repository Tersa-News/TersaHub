package news.tersa.hub.config.security

import org.jasypt.util.text.AES256TextEncryptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Utilities for AES encryption and decryption
 * Followed tutorial from https://www.baeldung.com/java-aes-encryption-decryption
 */
@Component
class AESUtils {

    @Value("\${tersa.aesSecret}")
    private lateinit var secret: String

    fun encrypt(input: String): String {
        val textEncryptor = AES256TextEncryptor()
        textEncryptor.setPassword(secret)
        return textEncryptor.encrypt(input)
    }

    fun decrypt(input: String): String {
        val textEncryptor = AES256TextEncryptor()
        textEncryptor.setPassword(secret)
        return textEncryptor.decrypt(input)
    }
}