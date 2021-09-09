import Cocoa

@_cdecl("say_something")
public func say_something(something: UnsafePointer<CChar>) {
   let speechSynthesizer = NSSpeechSynthesizer()
   speechSynthesizer.startSpeaking(String(cString: something))

}
//say_something(something: "Hello there")

