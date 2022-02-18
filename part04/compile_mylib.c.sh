# Below is for macOS
#gcc -dynamiclib -o libmylib.dylib mylib.c
gcc -shared -o libmylib.dylib mylib.c