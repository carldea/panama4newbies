# Panama 4 Newbies
This is a quick intro to OpenJDK's Project Panama.
- Part 1  https://foojay.io/today/project-panama-for-newbies-part-1

## Part 1 - Learning jextract and C strings, primitives and arrays types
Clean up executables and generated code from jextract.
```bash
sh clean.sh
```

Compile a Hello World C program (optional)
```shell
sh compile_helloworld.c.sh
```

Run Hello World C program (optional)
```shell
./a.out
```
Output:
```
Hello, World!
```

Generate Java Panama code (from stdio.h)
```shell
sh jextract_stdio.h.sh
```

Run Java Panama Hello World using C functions
```shell
sh run_HelloWorld.java.sh
```

Output shows the following:

```shell
Hello World! Panama style
```

Run Java Panama program creating C double.
```shell
sh run_Primitive.java.sh
```

Output shows the following:
```
A slice of 3.141593 
```

Run Java Panama program creating an array of type C double.
```shell
sh run_PrimitiveArray.java.sh
```

Output shows the following:
```shell
An array of data
 1.000000  2.000000  3.000000  4.000000 
 1.000000  1.000000  1.000000  1.000000 
 3.000000  4.000000  5.000000  6.000000 
 5.000000  6.000000  7.000000  8.000000 
```
