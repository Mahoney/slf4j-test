## Usage

### How it Works

### Making Assertions

### Resetting Stored State

### Parallel Testing

### Testing Multiple Threads Logging

### Printing log statements to System out and err

It can still be useful to print log messages to System out/err as appropriate. SLF4J Test will print messages using a standard
(non-configurable) format based on the value of the TestLoggerFactory's printLevel property. This can be set in any of the
following ways:

#### Programatically
    TestLoggerFactory.getInstance().setPrintLevel(Level.INFO);

#### Via a System Property
Run JVM with the following:
    -Dslf4jtest.print.level=INFO

#### Via a properties file
Place a file called slf4jtest.properties on the classpath with the following line in it:
    print.level=INFO
