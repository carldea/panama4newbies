# Create a swift library
swiftc sayhello.swift -emit-library -o libsayhelloswift.dylib

# Run java class
java -Djava.library.path=. --enable-native-access=ALL-UNNAMED --enable-preview --source 19 SayHello.java
