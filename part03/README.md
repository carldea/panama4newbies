# Panama 4 Newbies
This is a quick intro to OpenJDK's Project Panama.
- Part 3  https://foojay.io/today/project-panama-for-newbies-part-3

## Part 3 - Learning Java Panama with 3rd party libraries
Clean up executables and generated code from jextract.
```bash
$ ./clean.sh
```

### C Example: Local time (Optional)
A C program demonstrating the use of time.h 

Compile  program (optional)
```shell
$ ./compile_local_time.c.sh
```

Run the local_time C program (optional)
```shell
$ ./local_time_exe
```

The output will show something similar as the following:
```shell
A C program to display local date time.
  Seconds since the epoch: 1629679836
  Local Time: Sun Aug 22 20:50:36 2021
```

### PanamaTime.java Example
A Java program that will call into C's time.h library.
The following list of headers are libraries jextract will generate Java classes and source files.
- stdio.h
- time.h

Use jextract tool to create the Java Source code and compile to the classes directory.
```shell
$ ./jextract_stdio.h_and_time.h.sh
```

Run PanamaTime.java
```bash
$ ./run_PanamaTime.java.sh
```

The output is shown below:
```text
1. Java DateTime from C time function: Sun Aug 22 20:47:08 EDT 2021
2. C's printf & tm Struct of local time. 20:47:08
3. C's tm struct getters tm_hour, tm_min, tm_sec. 20:47:08
4. C's asctime() function to display date time: Sun Aug 22 20:47:08 2021
```
