# Panama 4 Newbies
This is a quick intro to OpenJDK's Project Panama.
- Part 4  https://foojay.io/today/project-panama-for-newbies-part-4

## Part 4 - Learning Callbacks
Clean up executables and generated code from jextract.
```bash
$ bash clean.sh
```

Compile the mylib.c into a dynamic library
```shell
$ bash compile_mylib.c.sh
```
Jextract foo.h 
```shell
$ bash jextract_foo.h.sh
```
Compile PanamaCallback.java 
```shell
$ bash compile_PanamaCallback.java.sh
```

Run PanamaCallback example
```shell
$ bash run_PanamaCallback.java.sh
```
Output shows the following:

```text
[Java] Callbacks! Panama style
[C] Inside mydb's C function my_callback_function().
[C]   Now invoking Java's callMePlease() static method.
[JAVA] Inside callMePlease() method - I'm being called from C.
[C] Inside mydb's C function my_callback_function2().
[C]   Now invoking Java's doubleMe(int) static method.
[JAVA] Inside doubleMe() method, 123 times 2 = 246.
```