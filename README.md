# Panama 4 Newbies
This is a quick intro to OpenJDK's Project Panama.
- Part 1  https://foojay.io/today/project-panama-for-newbies-part-1

## Part 1 - Learning jextract and C primitive types
Clean up executables and generated code from jextract.
```bash
$ ./clean.sh
```

Compile a Hello World C program (optional)
```shell
$ ./compile_helloworld.c.sh
```
Run Hello World C program (optional)
```shell
$ ./a.out
```

Generate Java Panama code (from stdio.h)
```shell
$ ./compile_HelloWorld.java.sh
```
Run Java Panama Hello World using C functions
```shell
$ ./run_HelloWorld.java.sh
```
Output shows the following:

```shell
Hello World
Hello, Panama from a C string. 
A slice of 3.141593 
An array of data
 1.000000  2.000000  3.000000  4.000000 
 1.000000  1.000000  1.000000  1.000000 
 3.000000  4.000000  5.000000  6.000000 
 5.000000  6.000000  7.000000  8.000000 
```