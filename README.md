
# Panama 4 Newbies 

This is a quick intro to OpenJDK's Project Panama.

- Part 1  https://foojay.io/today/project-panama-for-newbies-part-1
- Part 2  https://foojay.io/today/project-panama-for-newbies-part-2
- Part 3  https://foojay.io/today/project-panama-for-newbies-part-3
- Part 4  https://foojay.io/today/project-panama-for-newbies-part-4

## Part 1 - Learning Java Panama and C primitive types
Part 1 is where you'll learn about jextract and Panama APIs to create C primitive type data and use the printf() function from stdio.h.
https://github.com/carldea/panama4newbies/tree/main/part01

## Part 2 - Learning Java Panama and C Pointers and Structs
Part 2 you'll increase your knowledge on using Panama APIs to mimic C Pointers and create structs to hold complex data.
https://github.com/carldea/panama4newbies/tree/main/part02

## Part 3 - Learning Java Panama and C based Third Party Libraries
Part 3 is a chance to use Panama APIs to access third party libraries.
https://github.com/carldea/panama4newbies/tree/main/part03

## Part 4 - Learning Java Panama and C based Callbacks (Function Pointers)
Part 4 is a chance to use Panama APIs to allow C code to talk to Java code.
https://github.com/carldea/panama4newbies/tree/main/part04


## Requirements
1. Java JDK 25+
2. JExtract 25+ https://jdk.java.net/jextract/
3. Clang

### Installing C compiler
#### MacOs
```bash
xcode-select --install
````
#### Debian
```bash
sudo apt update
sudo apt install clang
```
#### Fedora/RHEL:
```bash
# For RHEL 8 or newer/Fedora
sudo dnf install clang
```
```bash
# Or using yum for older RHEL versions
sudo yum install llvm-toolset
```
#### Arch Linux
```bash
sudo pacman -S clang
```

#### Windows MSYS2 package manager
```bash
pacman -S mingw-w64-clang-x86_64-clang
```

## Setting up your environment
1. Download Jextract for your OS (https://jdk.java.net/jextract/) and unzip to a directory.
2. Download JDK for your OS (https://www.azul.com/downloads/?package=jdk#zulu)
3. Setup environment variables `JAVA_HOME` and `JEXTRACT_HOME` and run the following:
 
View and modify the file `setup.sh`.
```bash
JEXTRACT_HOME="<path to jextract>" sh setup.sh
```

**Note/Warning**: The `setup.sh` script is for your convenience, but feel free to skip to the next section if you already installed your JDK and Jextract downloads and setup things to your liking. 

Assuming things are set up you should verify with the following commands:
```shell
$ java -version
$ jextract -h 
```

## Getting Started:
1. Clone this project 
```
git clone git@github.com:carldea/panama4newbies.git
```
2. Change directory to **panama4newbies/Part01**.
3. Go to Part 1 and follow the steps ([readme.md](https://github.com/carldea/panama4newbies/blob/main/part01/README.md))
4. Blog article can be found here: - Part 1  https://foojay.io/today/project-panama-for-newbies-part-1