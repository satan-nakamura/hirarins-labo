package labo.hirarins.legacy.app.batch;

import java.io.IOException;
import java.util.Properties;

public class BatchPropertyLoader {

    private BatchPropertyLoader() {

    }

    public static Properties load(String propertyFileName) throws IOException {
        Properties props = new Properties();
        props.load(BatchPropertyLoader.class.getResourceAsStream(propertyFileName));
        return props;
    }
}