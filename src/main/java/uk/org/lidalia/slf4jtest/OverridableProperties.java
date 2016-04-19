package uk.org.lidalia.slf4jtest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import static com.google.common.base.Optional.fromNullable;
import static uk.org.lidalia.lang.Exceptions.throwUnchecked;

class OverridableProperties {
    private static final Properties EMPTY_PROPERTIES = new Properties();
    private final String propertySourceName;
    private final Properties properties;

    private static final Function<InputStream, Properties> loadProperties = new Function<InputStream, Properties>() {
        @Override
        public Properties apply(final InputStream propertyResource) {
            try (InputStream closablePropertyResource = propertyResource) {
                final Properties loadedProperties = new Properties();
                loadedProperties.load(closablePropertyResource);
                return loadedProperties;
            } catch (IOException ioException) {
                return throwUnchecked(ioException, null);
            }
        }
    };

    OverridableProperties(final String propertySourceName) throws IOException {
        this.propertySourceName = propertySourceName;
        this.properties = getProperties();
    }

    private Properties getProperties() throws IOException {
        final Optional<InputStream> resourceAsStream = fromNullable(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(propertySourceName + ".properties"));
        return resourceAsStream.transform(loadProperties).or(EMPTY_PROPERTIES);
    }

    String getProperty(final String propertyKey, final String defaultValue) {
        final String propertyFileProperty = properties.getProperty(propertyKey, defaultValue);
        return System.getProperty(propertySourceName + "." + propertyKey, propertyFileProperty);
    }
}
