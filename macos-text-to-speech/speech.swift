import Cocoa
import AVFoundation
@_cdecl("say_something")
public func say_something(something: UnsafePointer<CChar>) {
//    let speechSynthesizer = NSSpeechSynthesizer()
//    speechSynthesizer.startSpeaking(String(cString: something))

   // new way:
   let utterance = AVSpeechUtterance(string: String(cString: something))

   // Configure the utterance.
   utterance.rate = 1.0
   utterance.pitchMultiplier = 0.8
   utterance.postUtteranceDelay = 0.2
   utterance.volume = 0.8
   AVSpeechSynthesisVoice.speechVoices()
   // Retrieve the British English voice.
   let voice = AVSpeechSynthesisVoice(language: "en-GB")

   // Assign the voice to the utterance.
   utterance.voice = voice
    // Create a speech synthesizer.
    let synthesizer = AVSpeechSynthesizer()
    // Tell the synthesizer to speak the utterance.
    synthesizer.speak(utterance)
}
func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didFinish utterance: AVSpeechUtterance) {
   print("Speech finished")
}
//say_something(something: "Hello there")

