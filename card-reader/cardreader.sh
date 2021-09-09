# clean up
rm -rf classes
rm *.dylib

# Compile C programing using. -c creates an object file. 
# No need to specify -l ctacs the library was already moved to /usr/local/lib
# gcc -c -g -I . -o cardreader.o cardreader.c

# This builds a macos linked library.
# gcc -dynamiclib -current_version 1.0 -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include -I /usr/local/include -I . -l ctacs -o libcardreader.dylib cardreader.c

gcc -dynamiclib -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include -I /usr/local/include -I . -l ctacs -o libcardreader.dylib cardreader.c

# jextract header to generate sources. // -l <name of library> is lib<name>.dylib. just use the name below not the full file name. 
jextract --source -d generated/src -t cardreader -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include -I /usr/local/include -l cardreader cardreader.h

# jextract header to generate classes.
jextract -d classes -t cardreader -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include -I /usr/local/include -l cardreader cardreader.h

# Run java class
java -cp .:classes --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign src/CardReader.java
