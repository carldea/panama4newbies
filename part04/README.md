# Panama 4 Newbies
This is a quick intro to OpenJDK's Project Panama.
- Part 4  https://foojay.io/today/project-panama-for-newbies-part-4


 Todos: Create instructions and scripts for the Windows platform.

## Part 4 - Learning about Callbacks

Clean up executables and generated code from jextract.
```bash
$ sh clean.sh
```

Compile the mylib.c into a dynamic library
```shell
$ sh compile_mylib.c.sh
```
Jextract foo.h 
```shell
$ sh jextract_foo.h.sh
```
Compile PanamaCallback.java 
```shell
$ sh compile_PanamaCallback.java.sh
```

Run PanamaCallback example
```shell
$ sh run_PanamaCallback.java.sh
```
Output shows the following:

```text
[Java] Callbacks! Panama style
[C] Inside mylib's C function my_callback_function().
[C]   Now invoking Java's callMePlease() static method.
[JAVA] Inside callMePlease() method - I'm being called from C.
[C] Inside mylib's C function my_callback_function2().
[C]   Now invoking Java's doubleMe(int) static method.
[JAVA] Inside doubleMe() method, 123 times 2 = 246.
```