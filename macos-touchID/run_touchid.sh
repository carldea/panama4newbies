# Run java class
env JAVA_LIBRARY_PATH=:/usr/local/lib:. java -cp .:classes -Djava.library.path=. --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign TouchID
