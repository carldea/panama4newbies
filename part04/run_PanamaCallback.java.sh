env JAVA_LIBRARY_PATH=:/usr/local/lib:. \
  java -cp .:classes \
  --enable-native-access=ALL-UNNAMED \
  src/PanamaCallback.java