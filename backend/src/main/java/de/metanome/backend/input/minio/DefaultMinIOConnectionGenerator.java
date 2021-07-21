package de.metanome.backend.input.minio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingMinIOConnection;
import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.MinIOConnectionGenerator;
import de.metanome.algorithm_integration.input.RelationalInput;
import de.metanome.backend.input.file.FileIterator;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.errors.*;

import javax.persistence.Transient;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DefaultMinIOConnectionGenerator implements MinIOConnectionGenerator {

    @Transient
    @JsonIgnore
    protected transient MinioClient client;

    protected String minIOUrl;
    protected String key;
    protected String secretKey;


    public DefaultMinIOConnectionGenerator() {
    }

    public DefaultMinIOConnectionGenerator(String minIOUrl, String key, String secretKey) {
        this.minIOUrl = minIOUrl;
        this.key = key;
        this.secretKey = secretKey;
    }

    public DefaultMinIOConnectionGenerator(ConfigurationSettingMinIOConnection setting)
            throws AlgorithmConfigurationException {
        this(setting.getUrl(), setting.getKey(), setting.getSecretKey());
    }

    @Override
    public RelationalInput generateRelationalInputFromMinIOObject(String bucketName, String objectName) throws InputGenerationException, AlgorithmConfigurationException {
        if (this.client == null) {
            this.connect();
        }

        FileIterator fileIterator;
        try {
            InputStream obj = getClient().getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
            fileIterator = new FileIterator(objectName, new InputStreamReader(obj), new ConfigurationSettingFileInput(objectName));
        } catch (Exception e) {
            throw new InputGenerationException("Could not construct MinIO input", e);
        }

        return fileIterator;
    }

    @Override
    public ArrayList<String> getMinIOObjectNames(String bucketName) {
        ArrayList<String> objectList = new ArrayList<>();
        if (this.client == null) {
            this.connect();
        }
        client.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .recursive(true)
                .build()
        ).forEach(o -> {
            try {
                objectList.add(o.get().objectName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return objectList;
    }

    private void connect() {
        this.client = MinioClient.builder()
                .endpoint(minIOUrl)
                .credentials(key, secretKey)
                .build();
    }

    @Override
    public MinioClient getClient() {
        if (this.client == null) {
            this.connect();
        }
        return this.client;
    }

    @Override
    public void close() throws Exception {
        // DOOR STUCK
    }
}
