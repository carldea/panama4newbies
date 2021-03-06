#!/bin/sh -x
rm -rf classes
rm -rf generated/src
rm -rf src/org

# jextract stdio.h
jextract --source --output src -t org.unix \
  -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
  foo.h

# add to classes (IMPORTANT: Notice namespace ..src/org/unix/time/..)
#javac --add-modules=jdk.incubator.foreign -d classes generated/src/org/unix/*.java
jextract --output classes -t org.unix \
  -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
  foo.h
