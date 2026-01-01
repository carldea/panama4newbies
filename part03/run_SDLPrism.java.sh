# Linux OS
#env JAVA_LIBRARY_PATH=:/usr/local/lib java \
#  -cp classes \
#  -XstartOnFirstThread \
#  --enable-native-access=ALL-UNNAMED \
#  SDLFoo

# MacOS Apple Silicon - brew install sdl2
export DYLD_LIBRARY_PATH=/opt/homebrew/lib/:$DYLD_LIBRARY_PATH

java -cp classes \
  -XstartOnFirstThread \
  --enable-native-access=ALL-UNNAMED \
  SDLPrism
