# Panama 4 Newbies
This is a quick intro to OpenJDK's Project Panama.
- Part 4  https://foojay.io/today/project-panama-for-newbies-part-4


 Todos: Create instructions and scripts for the Windows platform.

## Part 4 - Learning about Callbacks
Let's setup your environment. 
1. Download Panama JDK from https://jdk.java.net/panama/
2. Extract JDK to a directory. E.g.: `/Users/jdoe/sdks` . I usually create a directory called **sdks** to switch between jdks.
    - MacOS    - `/Users/jdoe`
    - Linux    - `/home/jdoe`
    - Windows  - `C:\Users\jdoe`
3. Setup your environment variables 
- Unix/Linux
```bash
  # JAVA_HOME Unix/Linux
  export JAVA_HOME=$HOME/sdks/jdk-19.jdk/Contents/Home
  
  # PATH
  export PATH=$JAVA_HOME/bin:$PATH
```
- Windows Powershell
```bash
  # JAVA_HOME Windows
  $env:JAVA_HOME = "$env:homedrive$env:homepath/sdks/jdk-19.jdk/Contents/Home"
  
  # PATH
  $env:PATH = "$env:JAVA_HOME/bin:$env:PATH"
```

4. Verify `java` and `jextract`
 - `$ java -version`
```bash
openjdk version "19-panama" 2022-09-20
OpenJDK Runtime Environment (build 19-panama+1-13)
OpenJDK 64-Bit Server VM (build 19-panama+1-13, mixed mode, sharing)
```
 - `$ jextract -h`
```bash
WARNING: Using incubator modules: jdk.incubator.jextract, jdk.incubator.foreign
Non-option arguments:  
[String] -- header file

Option                         Description                              
------                         -----------                              
-?, -h, --help                 print help                               
-C <String>                    pass through argument for clang          
-I <String>                    specify include files path               
-d <String>                    specify where to place generated files   
--dump-includes <String>       dump included symbols into specified file
--header-class-name <String>   name of the header class                 
--include-function <String>    name of function to include              
--include-macro <String>       name of constant macro to include        
--include-struct <String>      name of struct definition to include     
--include-typedef <String>     name of type definition to include       
--include-union <String>       name of union definition to include      
--include-var <String>         name of global variable to include       
-l <String>                    specify a library                        
--source                       generate java sources                    
-t, --target-package <String>  target package for specified header files
```


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
[C] Inside mylib's C function my_callback_function().
[C]   Now invoking Java's callMePlease() static method.
[JAVA] Inside callMePlease() method - I'm being called from C.
[C] Inside mylib's C function my_callback_function2().
[C]   Now invoking Java's doubleMe(int) static method.
[JAVA] Inside doubleMe() method, 123 times 2 = 246.
```