echo "Make sure jextract_sdlfoo.h.sh was ran"
env JAVA_LIBRARY_PATH=:/usr/local/lib java \
  -cp classes \
  -XstartOnFirstThread \
  --enable-native-access=ALL-UNNAMED \
  --enable-preview --source 19 \
  src/SDLCube.java
