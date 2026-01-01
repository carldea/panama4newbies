echo "Make sure jextract_sdlfoo.h.sh was ran"

# Linux OS
#env JAVA_LIBRARY_PATH=:/usr/local/lib java \
#  -cp classes \
#  -XstartOnFirstThread \
#  --enable-native-access=ALL-UNNAMED \
#  src/SDLCube.java

# MacOS Apple Silicon (homebrew installed)
export DYLD_LIBRARY_PATH=/opt/homebrew/lib/:$DYLD_LIBRARY_PATH

java -cp classes \
  -XstartOnFirstThread \
  --enable-native-access=ALL-UNNAMED \
  SDLCube
#env JAVA_LIBRARY_PATH=:/opt/homebrew/Cellar/sdl2/2.32.10/lib/ \
#  java \
#  -cp classes \
#  -XstartOnFirstThread \
#  --enable-native-access=ALL-UNNAMED \
#  SDLCube
