package uk.org.lidalia.slf4jtest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.org.lidalia.lang.Task;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static uk.org.lidalia.test.ShouldThrow.shouldThrow;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OverridableProperties.class)
public class OverridablePropertiesTests {

    private static final String PROPERTY_SOURCE_NAME = "test";
    private static final String PROPERTY_IN_BOTH = "bothprop";
    private static final String PROPERTY_IN_SYSTEM_PROPS = "sysprop";

    @After
    public void resetLoggerFactory() {
        System.getProperties().remove(PROPERTY_SOURCE_NAME+"."+PROPERTY_IN_BOTH);
        System.getProperties().remove(PROPERTY_SOURCE_NAME+"."+PROPERTY_IN_SYSTEM_PROPS);
    }

    @Test
    public void propertyNotInEither() throws IOException {
        mockPropertyFileToContain("");

        final String defaultValue = "sensible_default";
        OverridableProperties properties = new OverridableProperties(PROPERTY_SOURCE_NAME);
        assertThat(properties.getProperty("notpresent", defaultValue),
                is(defaultValue));
    }

    @Test
    public void propertyInFileNotInSystemProperties() throws IOException {
        final String propName = "infile";
        final String propValue = "file value";
        mockPropertyFileToContain(propName + "=" + propValue);

        OverridableProperties properties = new OverridableProperties(PROPERTY_SOURCE_NAME);
        assertThat(properties.getProperty(propName, "default"),
                is(propValue));
    }

    @Test
    public void propertyNotInFileInSystemProperties() throws IOException {
        final String expectedValue = "system value";
        mockPropertyFileToContain("");
        System.setProperty(PROPERTY_SOURCE_NAME+"."+PROPERTY_IN_SYSTEM_PROPS, expectedValue);

        OverridableProperties properties = new OverridableProperties("test");
        assertThat(properties.getProperty(PROPERTY_IN_SYSTEM_PROPS, "default"),
                is(expectedValue));
    }

    @Test
    public void propertyInBothFileAndSystemProperties() throws IOException {
        final String expectedValue = "system value";
        mockPropertyFileToContain(PROPERTY_IN_BOTH+"=file value");
        System.setProperty(PROPERTY_SOURCE_NAME+"."+PROPERTY_IN_BOTH, expectedValue);

        OverridableProperties properties = new OverridableProperties(PROPERTY_SOURCE_NAME);
        assertThat(properties.getProperty(PROPERTY_IN_BOTH, "default"),
                is(expectedValue));
    }

    @Test
    public void noPropertyFile() throws IOException {
        mockPropertyFileInputStreamToBe(null);

        OverridableProperties properties = new OverridableProperties(PROPERTY_SOURCE_NAME);

        final String defaultValue = "sensible_default";
        assertThat(properties.getProperty("blah", defaultValue), is(defaultValue));
    }

    @Test
    public void ioExceptionLoadingProperties() throws IOException {
        final IOException ioException = new IOException();
        final InputStream inputStreamMock = mock(InputStream.class);
        mockPropertyFileInputStreamToBe(inputStreamMock);
        when(inputStreamMock.read(any(byte[].class))).thenThrow(ioException);

        final IOException actual = shouldThrow(IOException.class, new Task() {
            @Override
            public void perform() throws Exception {
                new OverridableProperties(PROPERTY_SOURCE_NAME);
            }
        });
        assertThat(actual, is(ioException));
    }

    @Test
    public void ioExceptionClosingPropertyStream() throws IOException {
        final IOException ioException = new IOException();
        final InputStream inputStreamMock = mock(InputStream.class);
        mockPropertyFileInputStreamToBe(inputStreamMock);
        doThrow(ioException).when(inputStreamMock).close();


        final IOException actual = shouldThrow(IOException.class, new Task() {
            @Override
            public void perform() throws Exception {
                new OverridableProperties(PROPERTY_SOURCE_NAME);
            }
        });
        assertThat(actual, is(ioException));
    }

    @Test
    public void ioExceptionLoadingAndClosingPropertyStream() throws IOException {
        final IOException loadException = new IOException("exception on load");
        final IOException closeException = new IOException("exception on close");
        final InputStream inputStreamMock = mock(InputStream.class);
        mockPropertyFileInputStreamToBe(inputStreamMock);
        when(inputStreamMock.read(any(byte[].class))).thenThrow(loadException);
        doThrow(closeException).when(inputStreamMock).close();

        final IOException finalException = shouldThrow(IOException.class, new Task() {
            @Override
            public void perform() throws Exception {
                new OverridableProperties(PROPERTY_SOURCE_NAME);
            }
        });
        assertThat(finalException, sameInstance(loadException));
        assertThat(finalException.getSuppressed(), is(new Throwable[]{closeException}));
    }

    private void mockPropertyFileToContain(String propertyFileContents) {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(propertyFileContents.getBytes());
        mockPropertyFileInputStreamToBe(inputStream);
    }

    private void mockPropertyFileInputStreamToBe(InputStream inputStream) {
        mockStatic(Thread.class);
        Thread threadMock = mock(Thread.class);
        when(Thread.currentThread()).thenReturn(threadMock);
        ClassLoader classLoaderMock = mock(ClassLoader.class);
        when(threadMock.getContextClassLoader()).thenReturn(classLoaderMock);

        when(classLoaderMock.getResourceAsStream(PROPERTY_SOURCE_NAME + ".properties"))
                .thenReturn(inputStream);
    }
}
