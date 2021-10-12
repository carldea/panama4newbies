<a href="https://foojay.io/works-with-openjdk">
   <img align="left" 
        src="https://github.com/foojayio/badges/raw/main/works_with_openjdk/Works-with-OpenJDK.png"   
        width="100">
</a>

# Panama 4 Newbies 

This is a quick intro to OpenJDK's Project Panama.

- Part 1  https://foojay.io/today/project-panama-for-newbies-part-1
- Part 2  https://foojay.io/today/project-panama-for-newbies-part-2
- Part 3  https://foojay.io/today/project-panama-for-newbies-part-3

## Part 1 - Learning Java Panama and C primitive types
Part 1 is where you'll learn about jextract and Panama APIs to create C primitive type data and use the printf() function from stdio.h.
https://github.com/carldea/panama4newbies/tree/main/part01

## Part 2 - Learning Java Panama and C Pointers and Structs
Part 2 you'll increase your knowledge on using Panama APIs to mimic C Pointers and create structs to hold complex data.
https://github.com/carldea/panama4newbies/tree/main/part02

## Part 3 - Learning Java Panama and C based Third Party Libraries
Part 3 is a chance to use Panama APIs to access third party libraries.
https://github.com/carldea/panama4newbies/tree/main/part03


## Setting up your IDE 
1. Make sure you know where you've downloaded the Panama Early Access Build and directory.
2. Setup environment variables and run the following:
```shell
$ java -version
$ jextract -h 
```
3. Change directories to Part01 as a project you'll want to setup as the working directory.
4. Run script to jextract for part 1
```shell
$ ./jextract_HelloWorld.java.sh
```
5. Create/Open an IntelliJ project in that directory.
6. Setup up JDK File -> Project Structure -> Project SDK
   Select the downloaded the early access JDK from https://jdk.java.net/panama/
   
   ![Panama Builds](https://github.com/carldea/panama4newbies/raw/main/IntelliJ-PanamaJDK.png)

7. In preferences you'll need to add  --add-modules jdk.incubator.foreign.

IntelliJ you'll need to do the following:

![Add modules](https://github.com/carldea/panama4newbies/raw/main/IntelliJ-Preferences.png)

8. Ensure that directory `generated/src` is marked as generated sources root.
9. Setup Run configurations and JVM options 

![Add JVM Options](https://github.com/carldea/panama4newbies/raw/main/IntelliJ-RunConfiguration.png)
