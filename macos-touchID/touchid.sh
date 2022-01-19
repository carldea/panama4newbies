# clean up
rm *.dylib

# Create a swift library
swiftc touchid.swift -emit-library -o libtouchidswift.dylib

# Run java class
java -cp .:classes --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign src/TouchID.java
