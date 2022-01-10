# Panama 4 Newbies
This is a quick intro to OpenJDK's Project Panama.
- Part 3  https://foojay.io/today/project-panama-for-newbies-part-3

## Part 3 - Learning Java Panama with 3rd party libraries
Clean up executables and generated code from jextract.
```bash
$ ./clean.sh
```
### Java Pid example using method handles (no jextract)
```shell
./run_PanamaPid.java.sh
```
Output should be the following:
```shell
MethodHandle calling getpid() (16465)
Jextract's calling getpid()   (16465)
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


### SDL Example: SDL Foo (Optional)
A CPP program demonstrating the use of the [SDL2](https://www.libsdl.org/) library
(a cross-platform development library designed to provide low level access to audio, keyboard, mouse, joystick, and graphics hardware via OpenGL and Direct3D).

Compile  program (optional)
```shell
$ ./compile_sdlfoo.cpp.sh
```

Run the SDL foo CPP program (optional)
```shell
$ ./sdlfoo_exe
```

The output will open a window with moving green flag.

![sdlfoo native screenshot](sdlfoo.cpp.png)

Either close the window with the close button or by typing `q`.

### SDL Example: SDL Foo with Panama
A Java program that will call into SDL library.

Before anything make sure to install the SDL2 library, 

```shell
$ brew install sdl2
```

Note this example leverages _Homebrew_ and the shell scripts relies on the paths
set up by homebrew, a different installation method is likely to require different paths. 

In order to run this example we need to make `jextract` generates the mappings 
for these two libraries
- `SDL.h`
- `SDL_opengl.h`

Use `jextract` tool to create the Java Source code and compile to the `classes` directory.
```shell
$ ./jextract_sdlfoo.h.sh
```

Run `SDLFoo.java`
```bash
$ ./run_SDLFoo.java.sh
```

You may notice the `-XstartOnFirstThread` option, this option is required on
macOs to run the `main()` method on the first (AppKit) thread.

The output is shown below:

![sdlfoo panama](sdlfoo-panama.gif)
