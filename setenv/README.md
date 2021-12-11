What is SetEnv?
In C you can set and get environment variables. This way child processes can run with different value.

```shell
# export JAVA_HOME Panama JDK. download from https://jdk.java.net/panama/ 
$ export JAVA_HOME=<java_home_dir>
$ export PATH=$JAVA_HOME/bin:$PATH

# generate panama code
$ bash jextract_foo.h.sh

# run example pass in a new JAVA_HOME 
$ bash run_SetEnv.java.sh ~/sdks/zulu11.43.55-ca-fx-jdk11.0.9.1-macosx_x64
```
You should see the following:
```shell
WARNING: Using incubator modules: jdk.incubator.foreign
warning: using incubating module(s): jdk.incubator.foreign
1 warning

[Before] JAVA_HOME=/Users/jdoe/sdks/jdk-17.jdk/Contents/Home/
[Before] PATH=/Users/jdoe/sdks/jdk-17.jdk/Contents/Home//bin:/Users/cdea/sdks/zulu11.43.55-ca-fx-jdk11.0.9.1-macosx_x64/bin:/Users/cdea/.sdkman/candidates/micronaut/current/bin:/Users/cdea/.sdkman/candidates/java/current/bin:/Users/cdea/.sdkman/candidates/gradle/current/bin:/Users/cdea/projects/project-harvey/server/bin:/bin:/Users/cdea/sdks/apache-maven-3.6.3/bin:/bin:/usr/local/opt/openssl/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/MacGPG2/bin:/Library/Apple/usr/bin:.bach/bin
Executing java -version
openjdk version "17-panama" 2021-09-14
OpenJDK Runtime Environment (build 17-panama+3-167)
OpenJDK 64-Bit Server VM (build 17-panama+3-167, mixed mode, sharing)

[After] JAVA_HOME=/Users/jdoe/sdks/zulu11.43.55-ca-fx-jdk11.0.9.1-macosx_x64
[After] PATH=/Users/jdoe/sdks/zulu11.43.55-ca-fx-jdk11.0.9.1-macosx_x64/bin:/Users/jdoe/sdks/jdk-17.jdk/Contents/Home//bin:/Users/jdoe/sdks/zulu11.43.55-ca-fx-jdk11.0.9.1-macosx_x64/bin:/Users/jdoe/.sdkman/candidates/micronaut/current/bin:/Users/jdoe/.sdkman/candidates/java/current/bin:/Users/jdoe/.sdkman/candidates/gradle/current/bin:/Users/jdoe/projects/project-harvey/server/bin:/bin:/Users/jdoe/sdks/apache-maven-3.6.3/bin:/bin:/usr/local/opt/openssl/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/MacGPG2/bin:/Library/Apple/usr/bin:.bach/bin
java -version
openjdk version "11.0.9.1" 2020-11-04 LTS
OpenJDK Runtime Environment Zulu11.43+55-CA (build 11.0.9.1+1-LTS)
OpenJDK 64-Bit Server VM Zulu11.43+55-CA (build 11.0.9.1+1-LTS, mixed mode)
```
**Note:**
The example changes the environment variable for child processes. For example in Panama code the following is executed subsequently:
```java
    // code to change PATH environment variable.
    MemorySegment java_cstr2 = CLinker.toCString("java -version", allocator);
    foo_h.system(java_cstr2);
```
The child process `system()` function will run using the new PATH value.