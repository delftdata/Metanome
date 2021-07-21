package de.metanome.backend.input.minio;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingMinIOInput;
import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.MinIOInputGenerator;
import de.metanome.algorithm_integration.input.RelationalInput;
import de.metanome.backend.input.file.FileIterator;
import io.minio.GetObjectArgs;
import io.minio.errors.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class DefaultMinIOInputGenerator implements MinIOInputGenerator {


//    private DefaultDatabaseConnectionGenerator defaultDatabaseConnectionGenerator;
//
//
//    private MinioClient client;
//
    private String objectName;

    private String bucketName;
//
    private ConfigurationSettingMinIOInput setting;
//
//

    private DefaultMinIOConnectionGenerator defaultMinIOConnectionGenerator;

    public DefaultMinIOInputGenerator() {
    }

    public DefaultMinIOInputGenerator(String bucketName, String objectName, DefaultMinIOConnectionGenerator defaultMinIOConnectionGenerator)
             {
        this.setting = new ConfigurationSettingMinIOInput();
        this.defaultMinIOConnectionGenerator = defaultMinIOConnectionGenerator;

        this.objectName = objectName;
        this.bucketName = bucketName;
    }


    public DefaultMinIOInputGenerator(ConfigurationSettingMinIOInput setting)
             {
        this.setting = setting;

        this.defaultMinIOConnectionGenerator = new DefaultMinIOConnectionGenerator(
                setting.getMinIOConnection().getUrl(),
                setting.getMinIOConnection().getKey(),
                setting.getMinIOConnection().getSecretKey()
        );

        this.objectName = setting.getObject();
        this.bucketName = setting.getBucket();
    }

    public DefaultMinIOInputGenerator(ConfigurationSettingMinIOInput setting, DefaultMinIOConnectionGenerator connection)
    {
        this.setting = setting;
        this.defaultMinIOConnectionGenerator = connection;
        this.objectName = setting.getObject();
        this.bucketName = setting.getBucket();
    }
    public DefaultMinIOInputGenerator(ConfigurationSettingMinIOInput setting, DefaultMinIOConnectionGenerator connection, String objectName)
    {
        this.setting = setting;
        this.defaultMinIOConnectionGenerator = connection;
        this.objectName = objectName;
        this.bucketName = setting.getBucket();
    }


    @Override
    public InputStream loadInputStream() {
        try {
            return defaultMinIOConnectionGenerator.getClient().getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (InputStream) Stream.empty();
    }

    @Override
    public RelationalInput generateNewCopy() throws InputGenerationException, AlgorithmConfigurationException {
        try {
            return new FileIterator(objectName, new InputStreamReader(loadInputStream()), new ConfigurationSettingFileInput(objectName));
        } catch (InputIterationException e) {
            throw new InputGenerationException("Could not read MinIO stream", e);
        }
    }

    @Override
    public void close() throws Exception {
        defaultMinIOConnectionGenerator.close();
    }

}
