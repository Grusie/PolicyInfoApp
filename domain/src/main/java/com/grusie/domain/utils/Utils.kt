package com.grusie.domain.utils

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

class Utils {
    companion object {
        //랜덤 비밀번호 생성
        fun generateRandomBytes(length: Int): ByteArray {
            val random = SecureRandom()
            val bytes = ByteArray(length)
            random.nextBytes(bytes)
            return bytes
        }

        //랜덤 비밀번호 암호화
        fun encryptData(data: ByteArray, key: String): String {
            val byteKey = stringToByteArray(key)
            val cipher = Cipher.getInstance("AES")
            val secretKey = SecretKeySpec(byteKey, "AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return cipher.doFinal(data).joinToString(",")
        }

        //랜덤 비밀번호 키 생성(최초 1회만 진행)
        fun generateAESKey(): ByteArray {
            val keyGenerator = KeyGenerator.getInstance("AES")
            keyGenerator.init(256)
            val secretKey = keyGenerator.generateKey()
            return secretKey.encoded
        }

        private fun stringToByteArray(byteArrayString: String): ByteArray {
            val byteStrings = byteArrayString.split(",")
            val bytes = ByteArray(byteStrings.size)
            for (i in byteStrings.indices) {
                bytes[i] = byteStrings[i].toByte()
            }
            return bytes
        }
    }
}