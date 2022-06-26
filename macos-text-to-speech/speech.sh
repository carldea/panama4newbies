# clean up
rm *.dylib

# Create a swift library
swiftc speech.swift -emit-library -o libspeechswift.dylib

# Run java class
java --enable-native-access=ALL-UNNAMED --enable-preview --source 19 Speech.java "$*"
