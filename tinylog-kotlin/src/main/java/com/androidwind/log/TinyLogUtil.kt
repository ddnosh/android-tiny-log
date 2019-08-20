package com.androidwind.log

import android.os.Environment
import android.util.Base64

import java.io.File
import java.nio.charset.Charset

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Log Utils
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
object TinyLogUtil {

    val logDir: String
        get() = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            Environment.getExternalStorageDirectory().path + File.separator + "Log"
        } else {
            throw RuntimeException("[TinyLog Exception]: there is no storage!")
        }

    private val CipherMode = "AES/CFB/NoPadding"

    @Throws(Exception::class)
    fun encrypt(key: String, data: String): String? {
        try {
            val cipher = Cipher.getInstance(CipherMode)
            val keyspec = SecretKeySpec(key.toByteArray(), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, IvParameterSpec(
                ByteArray(cipher.blockSize)))
            val encrypted = cipher.doFinal(data.toByteArray())
            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @Throws(Exception::class)
    fun decrypt(key: String, data: String): String? {
        try {
            val encrypted1 = Base64.decode(data.toByteArray(), Base64.DEFAULT)
            val cipher = Cipher.getInstance(CipherMode)
            val keyspec = SecretKeySpec(key.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, keyspec, IvParameterSpec(
                ByteArray(cipher.blockSize)))
            val original = cipher.doFinal(encrypted1)
            return String(original, Charset.forName("UTF-8"))
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
