# clean up
rm -rf classes
rm *.dylib

# Create a swift library
swiftc touchid.swift -emit-library -o libtouchidswift.dylib

# Create a c/c++ library
gcc -dynamiclib -current_version 1.0 ./libtouchidswift.dylib -o libtouchidcpp.dylib touchid.cpp


# jextract header to generate sources. // -l <name of library> is lib<name>.dylib. just use the name below not the full file name. 
jextract --source -d classes -t touchid -l touchidcpp -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include touchid.h

# jextract header to generate classes.
jextract -d classes -t touchid -l touchidcpp -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include touchid.h

# Run java class
java -cp .:classes --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign TouchID.Java
