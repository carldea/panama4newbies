#!/bin/sh -x
rm -rf classes
rm -rf generated/src
rm -rf src/sdl2
rm -rf src/org

jextract --source --output src -t sdl2 \
    -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
    -I /usr/local/include/SDL2 -l SDL2 \
    --header-class-name LibSDL2 \
    sdlfoo.h

# add to classes (IMPORTANT: Notice namespace ..src/sdl2/..)
javac --enable-preview --source 19 -d classes src/sdl2/*.java

jextract --source --output src -t org.unix \
  -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
  foo.h

jextract --output classes -t org.unix \
  -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
  foo.h
