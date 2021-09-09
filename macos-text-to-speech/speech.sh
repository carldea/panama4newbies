# clean up
rm -rf classes
rm *.dylib

# Create a swift library
swiftc speech.swift -emit-library -o libspeechswift.dylib

# Create a c/c++ library
gcc -dynamiclib -current_version 1.0 ./libspeechswift.dylib -o libspeechcpp.dylib speech.cpp


# jextract header to generate sources. // -l <name of library> is lib<name>.dylib. just use the name below not the full file name. 
jextract --source -d classes -t speech -l speechcpp -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include speech.h

# jextract header to generate classes.
jextract -d classes -t speech -l speechcpp -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include speech.h

# Run java class
java -cp .:classes --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign Speech.java $1
