
import sdl2.SDL_Event;
import sdl2.SDL_TextInputEvent;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Objects;


import static sdl2.LibSDL2.*;

/**
 * Example from <a href="https://lazyfoo.net/tutorials/SDL/50_SDL_and_opengl_2/index.php">SDL and OpenGL tutorial</a>.
 *
 * To run this example :
 * - One need to install XCode on macOs
 * - {@code brew install sdl2}
 *
 * Then create a file {@code sdl-foo.h} with the following content
 * (trick to extract mapping for multiple headers):
 *
 * <pre><code>
 * #include <SDL.h>
 * #include <SDL_opengl.h>
 * </code></pre>
 *
 * Then extract the mapping from this file
 * <pre><code>
 * jextract --source -d src/main/java -t sdl2 \
 *     -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include \
 *     -I /usr/local/include/SDL2 -l SDL2 \
 *     --header-class-name LibSDL2 \
 *     sdl-foo.h
 * </code></pre>
 *
 * Finally run the existing code that uses the above mappings.
 * <pre><code>
 * env JAVA_LIBRARY_PATH=:/usr/local/lib java \
 *   -cp classes \
 *   -XstartOnFirstThread \
 *   --enable-native-access=ALL-UNNAMED \
 *   --add-modules=jdk.incubator.foreign \
 *   src/SDLCube.java
 * </code></pre>
 */

public class SDLCube {
  private static final int SCREEN_WIDTH = 640;
  private static final int SCREEN_HEIGHT = 480;
  private long gWindow;
  private long gContext;

  static float[] color = new float[]{
          1.0f, 1.0f, 0.0f,
          1.0f, 0.0f, 0.0f,
          0.0f, 0.0f, 0.0f,
          0.0f, 1.0f, 0.0f,
          0.0f, 1.0f, 1.0f,
          1.0f, 1.0f, 1.0f,
          1.0f, 0.0f, 1.0f,
          0.0f, 0.0f, 1.0f
  };
  static float[] cube = {
          0.5f,  0.5f, -0.5f,
          0.5f, -0.5f, -0.5f,
          -0.5f, -0.5f, -0.5f,
          -0.5f,  0.5f, -0.5f,
          -0.5f,  0.5f,  0.5f,
          0.5f,  0.5f,  0.5f,
          0.5f, -0.5f,  0.5f,
          -0.5f, -0.5f,  0.5f
  };

  void main() {
    try (var arena = Arena.ofConfined()) {
      var sdlCube = new SDLCube();

      // Start up SDL and create window
      if (!sdlCube.init(arena)) {
        System.out.println("Failed to initialize!");
        System.exit(1);
      }

//        arena.addCloseAction(sdlCube::close);

      // Event handling
      // Allocate SDL_Event sdlEvent; which is a union type
      var sdlEvent = arena.allocate(SDL_Event.sizeof());

      // Enable text input
      SDL_StartTextInput();

      // While application is running
      boolean quit = false;
      var colorMemSeg = arena.allocateFrom(C_FLOAT, color);
      var cubeMemSeg = arena.allocateFrom(C_FLOAT, cube);

      while (!quit) {

        // Handle events on queue
        while (SDL_PollEvent(sdlEvent) != 0) {
          // User clicked the quit button
          if (SDL_Event.type(sdlEvent) == SDL_QUIT()) {
            quit = true;
          }

          // Handle keypress with current mouse position
          else if (SDL_Event.type(sdlEvent) == SDL_TEXTINPUT()) {
            // e.text.text[ 0 ]
            char c = SDL_TextInputEvent.asSlice(sdlEvent, 0).getString(0).charAt(0);
            if (c == 'q') {
              quit = true;
            }
          }
        }

        sdlCube.render(arena, colorMemSeg, cubeMemSeg);
        sdlCube.update(arena);
      }

      //Disable text input
      SDL_StopTextInput();
      close();
    }


  }

  private void update(SegmentAllocator arena) {
    // Update a window with OpenGL rendering
      MemorySegment gWindowMemSeg = MemorySegment.ofAddress(gWindow);
    SDL_GL_SwapWindow(gWindowMemSeg);
  }

  private boolean init(SegmentAllocator arena) {
    if (SDL_Init(SDL_INIT_VIDEO()) < 0) {
      String errMsg = SDL_GetError().getString(0);
      System.out.printf("SDL could not initialize! SDL Error: %s\n", errMsg);
      return false;
    } else {
      SDL_GL_SetAttribute(SDL_GL_CONTEXT_MAJOR_VERSION(), 2);
      SDL_GL_SetAttribute(SDL_GL_CONTEXT_MINOR_VERSION(), 1);


       MemorySegment gWindowMemSeg = SDL_CreateWindow(arena.allocateFrom("SDL Cube for Panama"),
                                 SDL_WINDOWPOS_UNDEFINED(),
                                 SDL_WINDOWPOS_UNDEFINED(),
                                 SCREEN_WIDTH,
                                 SCREEN_HEIGHT,
                                 SDL_WINDOW_OPENGL() | SDL_WINDOW_SHOWN());
       gWindow = gWindowMemSeg.address();
      if (Objects.equals(NULL(), gWindowMemSeg)) {
        System.out.printf("Window could not be created! SDL Error: %s\n", SDL_GetError().getString(0));
        return false;
      } else {
        // Initialize opengl
          MemorySegment gContextMemorySeg = SDL_GL_CreateContext(gWindowMemSeg);
        gContext = gContextMemorySeg.address();
        if (Objects.equals(NULL(), gContextMemorySeg)) {
          System.out.printf("OpenGL context could not be created! SDL Error: %s\n", SDL_GetError().getString(0));
          return false;
        } else {
          //Use Vsync
          if (SDL_GL_SetSwapInterval(1) < 0) {
            System.out.printf("Warning: Unable to set VSync! SDL Error: %s\n", SDL_GetError().getString(0));
          }


          //Initialize OpenGL
          if (!initGL()) {
            System.out.println("Unable to initialize OpenGL!\n");
            return false;
          }
        }
      }

      return true;
    }
  }

  private boolean initGL() {
    boolean success = true;
    int error = GL_NO_ERROR();

    // Initialize Projection Matrix
    glMatrixMode(GL_PROJECTION());
    glLoadIdentity();

    // Check for error
    error = glGetError();
    if (error != GL_NO_ERROR()) {
      success = false;
    }
    glOrtho(-2.0, 2.0, -2.0, 2.0, -20.0, 20.0);
    // Initialize Modelview Matrix
    glMatrixMode(GL_MODELVIEW());
    glLoadIdentity();

    glEnable(GL_DEPTH_TEST());
    glDepthFunc(GL_LESS());
    glShadeModel(GL_SMOOTH());
    // Check for error
    error = glGetError();
    if (error != GL_NO_ERROR()) {
      success = false;
    }

    // Initialize clear color
    glClearColor(0.f, 0.f, 0.f, 1.f);

    // Check for error
    error = glGetError();
    if (error != GL_NO_ERROR()) {
      success = false;
    }

    return success;
  }

  private void close() {
    SDL_DestroyWindow(MemorySegment.ofAddress(gWindow));
    SDL_Quit();
  }


  private static void cubeEdge(MemorySegment colorMemSeg, MemorySegment cubeMemSeg, long index) {
    glColor3fv(colorMemSeg.asSlice(index * 12)); // 3 floats = 3 X 4 bytes = 12 bytes
    glVertex3fv(cubeMemSeg.asSlice(index * 12));
  }
  private void render(Arena arena, MemorySegment colorMemSeg, MemorySegment cubeMemSeg) {

    /* Do our drawing, too. */
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT() | GL_DEPTH_BUFFER_BIT());

    glBegin(GL_QUADS());

    cubeEdge(colorMemSeg, cubeMemSeg, 0);
    cubeEdge(colorMemSeg, cubeMemSeg, 1);
    cubeEdge(colorMemSeg, cubeMemSeg, 2);
    cubeEdge(colorMemSeg, cubeMemSeg, 3);

    cubeEdge(colorMemSeg, cubeMemSeg, 3);
    cubeEdge(colorMemSeg, cubeMemSeg, 4);
    cubeEdge(colorMemSeg, cubeMemSeg, 7);
    cubeEdge(colorMemSeg, cubeMemSeg, 2);

    cubeEdge(colorMemSeg, cubeMemSeg, 0);
    cubeEdge(colorMemSeg, cubeMemSeg, 5);
    cubeEdge(colorMemSeg, cubeMemSeg, 6);
    cubeEdge(colorMemSeg, cubeMemSeg, 1);

    cubeEdge(colorMemSeg, cubeMemSeg, 5);
    cubeEdge(colorMemSeg, cubeMemSeg, 4);
    cubeEdge(colorMemSeg, cubeMemSeg, 7);
    cubeEdge(colorMemSeg, cubeMemSeg, 6);

    cubeEdge(colorMemSeg, cubeMemSeg, 5);
    cubeEdge(colorMemSeg, cubeMemSeg, 0);
    cubeEdge(colorMemSeg, cubeMemSeg, 3);
    cubeEdge(colorMemSeg, cubeMemSeg, 4);

    cubeEdge(colorMemSeg, cubeMemSeg, 6);
    cubeEdge(colorMemSeg, cubeMemSeg, 1);
    cubeEdge(colorMemSeg, cubeMemSeg, 2);
    cubeEdge(colorMemSeg, cubeMemSeg, 7);

    //Clear color buffer
    glEnd();
    glMatrixMode(GL_MODELVIEW());
    glRotatef(5.0f, 1.0f, 1.0f, 1.0f);
  }
}
