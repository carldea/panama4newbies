# Create a swift library
swiftc sayhello.swift -emit-library -o libsayhelloswift.dylib

# Create a c/c++ library
gcc -dynamiclib -current_version 1.0 ./libsayhelloswift.dylib -o libsayhellocpp.dylib sayhello.cpp


# jextract header to generate sources.
jextract --source -d classes -t sayhello -l sayhellocpp -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include sayhello.h

# jextract header to generate classes.
jextract -d classes -t sayhello -l sayhellocpp -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include sayhello.h

# Run java class
java -cp .:classes --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign SayHello.Java
