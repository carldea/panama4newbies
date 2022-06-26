# Generate class files
jextract --output classes \
  -t org.unix \
  -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
  -I . \
  -l mylib \
  foo.h

# Generate Java source code
jextract --source \
  --output src \
  -t org.unix \
  -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
  -I . \
  -l mylib \
  foo.h

