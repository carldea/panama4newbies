#!/bin/sh -x
rm -rf classes
rm -rf src/org

# jextract foo.h While the following still a preferred way to group a bunch of header files in one header file (foo.h)
# new in jextract allows you to specify one to many header files using double quotes.
#
#jextract --output src -t org.unix \
#  -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
#  foo.h

# In favor of the new way to specify multiple header files. But this requires the --header-class-name to be supplied.
jextract --output src --header-class-name foolib -t org.unix "<time.h>" "<stdio.h>" "<sys/types.h>" "<unistd.h>"

# Just compile the basic examples that begin with Panama. This allows the reader to incrementally follow steps before
# working on the advanced example code using SDL and OpenGL.
javac -cp .:classes -d classes src/org/**/*.java src/Panama*.java