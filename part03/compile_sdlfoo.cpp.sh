clang++ -std=c++17 -fexceptions -I /usr/local/include/SDL2 -l SDL2 -framework GLUT -framework OpenGL sdlfoo.cpp -o sdlfoo_exe
echo "Compiled sdlfoo.cpp"
echo "To run executable on the commandline type ./sdlfoo_exe"
