#!/bin/sh -x
rm -rf classes
rm -rf generated/src

jextract --source -d generated/src -t sdl2 \
    -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
    -I /usr/local/include/SDL2 -l SDL2 \
    --header-class-name LibSDL2 \
    sdlfoo.h

# add to classes (IMPORTANT: Notice namespace ..src/sdl2/..)
javac --add-modules=jdk.incubator.foreign -d classes generated/src/sdl2/*.java
