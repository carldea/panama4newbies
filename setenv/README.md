What is SetEnv?
In C you can set and get environment variables. This way child processes can run with different value.

```shell
# export JAVA_HOME Panama JDK. download from https://jdk.java.net/panama/ 
$ export JAVA_HOME=<java_home_dir>
$ export PATH=$JAVA_HOME/bin:$PATH

$ bash jextract_foo.h.sh
$ bash run_SetEnv.java.sh
```
You should see the following:
```shell
$ bash run_SetEnv.java.sh 
WARNING: Using incubator modules: jdk.incubator.foreign
warning: using incubating module(s): jdk.incubator.foreign
1 warning

[Before] JAVA_HOME=/Users/cdea/sdks/jdk-17.jdk/Contents/Home/
java -version
openjdk version "17-panama" 2021-09-14
OpenJDK Runtime Environment (build 17-panama+3-167)
OpenJDK 64-Bit Server VM (build 17-panama+3-167, mixed mode, sharing)

[After] JAVA_HOME=/Downloads/sdks/zulu16.0.89-ea-jdk16.0.0-ea.36-macosx_x64
java -version
openjdk version "17-panama" 2021-09-14
OpenJDK Runtime Environment (build 17-panama+3-167)
OpenJDK 64-Bit Server VM (build 17-panama+3-167, mixed mode, sharing)
```
**NOTE:**
Not working as expected?
Notice the After section should be using java 16.
Child process' environmental variables should take on current settings.
Help me figure why?