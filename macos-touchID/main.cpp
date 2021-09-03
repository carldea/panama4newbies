// main.cpp

// only "extern" when targeting C.
extern "C" void say_hello();

int main(){
    say_hello();    // Prints "Hello, World!"
    return 0;
}
