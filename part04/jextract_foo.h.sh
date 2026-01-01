# Generate panama source files
jextract --output src \
  -t org.unix \
  -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
  -I . \
  -l mylib \
  foo.h