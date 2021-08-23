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
$ ./jextract_PointersAndStructs.java.sh
```

Run Java Panama PointersAndStructs example using C functions
```shell
$ ./run_PointersAndStructs.java.sh
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