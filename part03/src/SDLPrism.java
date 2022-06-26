
import sdl2.SDL_Event;
import sdl2.SDL_TextInputEvent;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.util.Objects;

import static java.lang.foreign.MemoryAddress.NULL;
import static sdl2.LibSDL2.*;

/**
 * Example from <a href="https://lazyfoo.net/tutorials/SDL/50_SDL_and_opengl_2/index.php">SDL and OpenGL tutorial</a>.
 *
 * To run this example :
 * - Install XCode on macOS
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
 *   src/SDLPrism.java
 * </code></pre>
 */

public class SDLPrism {
  private static final int SCREEN_WIDTH = 640;
  private static final int SCREEN_HEIGHT = 480;
  private MemoryAddress gWindow;
  private MemoryAddress gContext;

  static float[] color = new float[]{
          1.0f, 1.0f, 0.0f,
          1.0f, 0.0f, 0.0f,
          0.0f, 0.0f, 0.0f,
          0.0f, 1.0f, 0.0f,
          0.0f, 1.0f, 1.0f,
          1.0f, 1.0f, 1.0f,
          1.0f, 0.0f, 1.0f,
          0.0f, 0.0f, 1.0f,
          1.0f, 1.0f, 0.0f,
          1.0f, 0.0f, 0.0f,
          0.0f, 0.0f, 0.0f,
          0.0f, 1.0f, 0.0f,
          0.0f, 1.0f, 1.0f,
          1.0f, 1.0f, 1.0f,
          1.0f, 0.0f, 1.0f,
          0.0f, 0.0f, 1.0f,
  };

  static float[] prism = {
          // front Face
           0.0f,  1.0f, 0.0f,
          -1.0f, -1.0f, 1.0f,
           1.0f, -1.0f, 1.0f,

          // Right face
           0.0f, 1.0f, 0.0f,
           1.0f, -1.0f, 1.0f,
           1.0f, -1.0f, -1.0f,

          // Back face
           0.0f, 1.0f, 0.0f,
           1.0f, -1.0f, -1.0f,
          -1.0f, -1.0f, -1.0f,
          // Left face
           0.0f, 1.0f, 0.0f,
          -1.0f,-1.0f,-1.0f,
          -1.0f,-1.0f, 1.0f
  };

  public static void main(String[] args) {
    try (var memorySession = MemorySession.openConfined()) {
      var sdlObject = new SDLPrism();

      // Start up SDL and create window
      if (!sdlObject.init(memorySession)) {
        System.out.println("Failed to initialize!");
        System.exit(1);
      }

      memorySession.addCloseAction(sdlObject::close);

      // Event handling
      // Allocate SDL_Event sdlEvent; which is a union type
      var sdlEvent = MemorySegment.allocateNative(SDL_Event.sizeof(), memorySession);
      
      // Enable text input
      SDL_StartTextInput();

      // While application is running
      boolean quit = false;
      var colorMemSeg = memorySession.allocateArray(C_FLOAT, color);
      var verticesMemSeg = memorySession.allocateArray(C_FLOAT, prism);

      while (!quit) {

        // Handle events on queue
        while (SDL_PollEvent(sdlEvent) != 0) {
          // User clicked the quit button
          if (SDL_Event.type$get(sdlEvent) == SDL_QUIT()) {
            quit = true;
          }

          // Handle keypress with current mouse position
          else if (SDL_Event.type$get(sdlEvent) == SDL_TEXTINPUT()) {
            // e.text.text[ 0 ]
            char c = SDL_TextInputEvent.text$slice(sdlEvent).getUtf8String(0).charAt(0);
            if (c == 'q') {
              quit = true;
            }
          }
        }

        sdlObject.render(memorySession, colorMemSeg, verticesMemSeg);
        sdlObject.update(memorySession);
      }

      //Disable text input
      SDL_StopTextInput();
    }
  }

  private void update(MemorySession memorySession) {
    // Update a window with OpenGL rendering
    SDL_GL_SwapWindow(gWindow);
  }

  private boolean init(MemorySession memorySession) {
    if (SDL_Init(SDL_INIT_VIDEO()) < 0) {
      String errMsg = SDL_GetError().getUtf8String(0);
      System.out.printf("SDL could not initialize! SDL Error: %s\n", errMsg);
      return false;
    } else {
      SDL_GL_SetAttribute(SDL_GL_CONTEXT_MAJOR_VERSION(), 2);
      SDL_GL_SetAttribute(SDL_GL_CONTEXT_MINOR_VERSION(), 1);


      gWindow = SDL_CreateWindow(memorySession.allocateUtf8String("SDL Prism for Panama"),
                                 SDL_WINDOWPOS_UNDEFINED(),
                                 SDL_WINDOWPOS_UNDEFINED(),
                                 SCREEN_WIDTH,
                                 SCREEN_HEIGHT,
                                 SDL_WINDOW_OPENGL() | SDL_WINDOW_SHOWN());

      if (Objects.equals(NULL, gWindow)) {
        System.out.printf("Window could not be created! SDL Error: %s\n", SDL_GetError().getUtf8String(0));
        return false;
      } else {
        // Initialize opengl
        gContext = SDL_GL_CreateContext(gWindow);
        if (Objects.equals(NULL, gContext)) {
          System.out.printf("OpenGL context could not be created! SDL Error: %s\n", SDL_GetError().getUtf8String(0));
          return false;
        } else {
          //Use Vsync
          if (SDL_GL_SetSwapInterval(1) < 0) {
            System.out.printf("Warning: Unable to set VSync! SDL Error: %s\n", SDL_GetError().getUtf8String(0));
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
    SDL_DestroyWindow(gWindow);
    SDL_Quit();
  }


  private static void colorEdge(MemorySegment colorMemSeg, MemorySegment cubeMemSeg, long index) {
    glColor3fv(colorMemSeg.asSlice(index * 12)); // 3 floats = 3 X 4 bytes = 12 bytes
    glVertex3fv(cubeMemSeg.asSlice(index * 12));
  }
  private void render(MemorySession memorySession, MemorySegment colorMemSeg, MemorySegment vertexMemSeg) {

    /* Do our drawing, too. */
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT() | GL_DEPTH_BUFFER_BIT());

    glBegin(GL_TRIANGLES());

    // Front face
    colorEdge(colorMemSeg, vertexMemSeg, 0);
    colorEdge(colorMemSeg, vertexMemSeg, 1);
    colorEdge(colorMemSeg, vertexMemSeg, 2);

    // Right face
    colorEdge(colorMemSeg, vertexMemSeg, 3);
    colorEdge(colorMemSeg, vertexMemSeg, 4);
    colorEdge(colorMemSeg, vertexMemSeg, 5);

    // Back
    colorEdge(colorMemSeg, vertexMemSeg, 6);
    colorEdge(colorMemSeg, vertexMemSeg, 7);
    colorEdge(colorMemSeg, vertexMemSeg, 8);

    // Left
    colorEdge(colorMemSeg, vertexMemSeg, 9);
    colorEdge(colorMemSeg, vertexMemSeg, 10);
    colorEdge(colorMemSeg, vertexMemSeg, 11);

    //Clear color buffer
    glEnd();
    glMatrixMode(GL_MODELVIEW());
    glRotatef(5.0f, 0.0f, -0.5f, 0.0f);
  }
}
