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
  # JAVA_HOME MacOS
  export JAVA_HOME=$HOME/sdks/jdk-19.jdk/Contents/Home

  # JAVA_HOME Linux
  export JAVA_HOME=$HOME/sdks/jdk-19
  
  # PATH
  export PATH=$JAVA_HOME/bin:$PATH
```
- Windows Powershell
```bash
  # JAVA_HOME Windows
  $env:JAVA_HOME = "$env:homedrive$env:homepath/sdks/jdk-19"
  
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
Usage: jextract <options> <header file>                                  

Option                         Description                               
------                         -----------                               
-?, -h, --help                 print help                                
-D <macro>                     define a C preprocessor macro             
-I <path>                      specify include files path                
--dump-includes <file>         dump included symbols into specified file 
--header-class-name <name>     name of the header class                  
--include-function <name>      name of function to include               
--include-macro <name>         name of constant macro to include         
--include-struct <name>        name of struct definition to include      
--include-typedef <name>       name of type definition to include        
--include-union <name>         name of union definition to include       
--include-var <name>           name of global variable to include        
-l <library>                   specify a library name or absolute library path   
--output <path>                specify the directory to place generated files    
--source                       generate java sources                     
-t, --target-package <package> target package for specified header files 
--version                      print version information and exit 
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