env JAVA_LIBRARY_PATH=:/usr/local/lib java \
  -cp .:classes \
  -XstartOnFirstThread \
  --enable-native-access=ALL-UNNAMED \
  --add-modules=jdk.incubator.foreign \
  src/SDLFoo.java
