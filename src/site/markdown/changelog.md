## Changelog

### Version 1.2.0

Allows construction of standalone instances to facilitate logging in different
contexts.

### Version 1.1.0

Fixes https://github.com/Mahoney/slf4j-test/issues/4 - Detect throwable as last
varargs element if the format string has n-1 parameters.

With thanks to https://github.com/philipa

### Version 1.0.1

Fixed an issue where null arguments to a logging event and null values in the
MDC caused a NullPointerException due to Guava's strict attitude to nulls. Null
arguments now appear as Optional.absent() and null values in the MDC as the
String "null".
