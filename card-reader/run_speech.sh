# Run java class
java -cp .:classes --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign Speech.java "$*"
