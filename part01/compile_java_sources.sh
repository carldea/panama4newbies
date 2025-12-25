# Compile Jextract generated Java code in generatedsrc
javac generatedsrc/org/**/*.java -d classes

# Compile Example Java code in src
javac -cp .:classes src/*.java -d classes