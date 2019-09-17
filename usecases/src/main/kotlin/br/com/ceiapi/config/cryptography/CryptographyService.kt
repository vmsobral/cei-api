package br.com.ceiapi.config.cryptography

import br.com.ceiapi.config.async.CustomThreadFactory
import br.com.ceiapi.config.propertiesconfig.BaseConfigService
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.kms.AWSKMSClientBuilder
import com.amazonaws.services.kms.model.DecryptRequest
import com.amazonaws.services.kms.model.EncryptRequest
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import java.nio.ByteBuffer
import java.util.Base64
import java.util.concurrent.Callable
import java.util.concurrent.Executors.newCachedThreadPool
import java.util.concurrent.Future

@Service
class CryptographyService(configService: BaseConfigService) {

    private val executor = newCachedThreadPool(CustomThreadFactory("cryptography"))
    private val encryptionKey = configService.getString("data.encryption.key")
    private val kmsClient = AWSKMSClientBuilder.standard()
            .apply { setEndpointConfiguration(EndpointConfiguration("kms.us-east-1.amazonaws.com", "us-east-1")) }
            .build()!!

    fun decrypt(data: EncryptedData): Future<String> = executor.submit(Callable<String> {
        decryptSync(data)
    })

    fun encrypt(string: String): Future<EncryptedData> = executor.submit(Callable<EncryptedData> {
        EncryptedData(encryptSync(string))
    })

    private fun decryptSync(data: EncryptedData): String {
        try {
            val decryptRequest = DecryptRequest()
                    .withCiphertextBlob(decodeBase64(data.data))
            val decrypted = kmsClient.decrypt(decryptRequest)
            return String(decrypted.plaintext.array())
        } catch (e: Exception) {
            LOGGER.error("Error trying to decrypt", e)
            throw RuntimeException(e)
        }
    }

    private fun encryptSync(string: String): String {
        try {
            val encryptRequest = EncryptRequest()
                    .withKeyId(encryptionKey)
                    .withPlaintext(toByteBuffer(string.toByteArray()))
            val encrypted = kmsClient.encrypt(encryptRequest)
            return encodeBase64(encrypted.ciphertextBlob.array())
        } catch (e: Exception) {
            LOGGER.error("Error trying to encrypt", e)
            throw RuntimeException(e)
        }
    }

    private fun decodeBase64(string: String): ByteBuffer {
        return toByteBuffer(Base64.getDecoder().decode(string))
    }

    private fun encodeBase64(byteArray: ByteArray): String {
        return String(Base64.getEncoder().encode(byteArray))
    }

    private fun toByteBuffer(byteArray: ByteArray): ByteBuffer {
        val byteBuffer = ByteBuffer.allocate(byteArray.size)
        byteBuffer.put(byteArray)
        byteBuffer.flip()
        return byteBuffer
    }

    companion object {
        private val LOGGER = getLogger(CryptographyService::class.java)!!
    }

}