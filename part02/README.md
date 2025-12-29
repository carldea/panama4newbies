# Panama 4 Newbies
This is a quick intro to OpenJDK's Project Panama.
- Part 2  https://foojay.io/today/project-panama-for-newbies-part-2

## Part 2 - Learning Pointers and Structs
Clean up executables and generated code from jextract.
```bash
$ ./clean.sh
```

Compile the Pointers C program (optional)
```shell
$ ./compile_pointers.c.sh
```
Run Pointers C program (optional)
```shell
$ ./pointers_exe
```
Compile the Structs C program (optional)
```shell
$ ./compile_structs.c.sh
```
Run Structs C program (optional)
```shell
$ ./structs_exe
```

Generate Java Panama code (from stdio.h)
```shell
$ ./jextract_stdio.h.sh
```
Compile generated and example Java source code
```shell
$ ./compile_java_sources.sh
```

Run Java Panama Pointers example using C functions
```shell
$ ./run_Pointers.java.sh
```

Output shows the following:

```text
Creating Pointers:
           x = 5    address = 7fa7e36d4890 
 ptr's value = 5    address = 7fa7e36d4890 
 Changing x's value to: 10 
           x = 10    address = 7fa7e36d4890 
 ptr's value = 10    address = 7fa7e36d4890 

Create one Point struct:
cPoint = (100, 200)
```

Run Java Panama Structs example
```shell
$ ./run_Structs.java.sh
```

Output shows the following:
```text
Create one Point struct:
cPoint = (100, 200)
```

Run Java Panama StructsArray example
```shell
$ ./run_StructsArray.java.sh
```

Output shows the following:
```text
Create A Sequence of Point structs:
 points[0] = (48,  83) 
 points[1] = (15,  42) 
 points[2] = (34,  82) 
 points[3] = (62,  65) 
 points[4] = (84,  36) 
```