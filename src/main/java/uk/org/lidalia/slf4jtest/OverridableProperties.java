package uk.org.lidalia.slf4jtest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import uk.org.lidalia.lang.Exceptions;

import static com.google.common.base.Optional.fromNullable;

class OverridableProperties {
    private static final Properties EMPTY_PROPERTIES = new Properties();
    private final String propertySourceName;
    private final Properties properties;

    OverridableProperties(final String propertySourceName) throws IOException {
        this.propertySourceName = propertySourceName;
        this.properties = getProperties();
    }

    private Properties getProperties() throws IOException {
        final Optional<InputStream> resourceAsStream = fromNullable(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(propertySourceName + ".properties"));
        return resourceAsStream.transform(new Function<InputStream, Properties>() {
            @Override
            public Properties apply(final InputStream propertyResource) {
                Properties loadedPropertoes = new Properties();
                try (InputStream closablePropertyResource = propertyResource) {
                    loadedPropertoes.load(closablePropertyResource);
                } catch (IOException ioe) {
                    Exceptions.throwUnchecked(ioe);
                }
                return loadedPropertoes;
            }
        }).or(EMPTY_PROPERTIES);
    }

    String getProperty(final String propertyKey, final String defaultValue) {
        final String propertyFileProperty = properties.getProperty(propertyKey, defaultValue);
        return System.getProperty(propertySourceName + "." + propertyKey, propertyFileProperty);
    }
}
