env JAVA_LIBRARY_PATH=:/usr/local/lib java \
  -cp classes \
  -XstartOnFirstThread \
  --enable-native-access=ALL-UNNAMED \
  --enable-preview --source 19 \
  src/SDLFoo.java
