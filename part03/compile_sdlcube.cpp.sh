clang++ -std=c++17 -fexceptions -I /usr/local/include/SDL2 -l SDL2 -framework GLUT -framework OpenGL sdlcube.cpp -o sdlcube_exe
echo "Compiled sdlcube.cpp"
echo "To run executable on the commandline type ./sdlcube_exe"
