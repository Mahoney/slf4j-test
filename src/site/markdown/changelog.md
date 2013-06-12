## Changelog

### Version 1.0.1

Fixed an issue where null arguments to a logging event and null values in the MDC caused a NullPointerException due to Guava's
strict attitude to nulls. Null arguments now appear as Optional.absent() and null values in the MDC as the String "null".
