#!/bin/sh -x
rm -rf classes
rm -rf generated/src

# jextract stdio.h
jextract --source -d generated/src -t org.unix \
  -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
  foo.h

# add to classes (IMPORTANT: Notice namespace ..src/org/unix/time/..)
javac -d classes generated/src/org/unix/*.java
