
import Speech
enum SpeechStatus {
    case ready
    case recognizing
    case unavailable
}

class SpeechRec {
    let audioEngine = AVAudioEngine()
    let speechRecognizer: SFSpeechRecognizer? = SFSpeechRecognizer()
    let request = SFSpeechAudioBufferRecognitionRequest()
    var recognitionTask: SFSpeechRecognitionTask?

    var recognizedText = "Huh?"

    var status = SpeechStatus.ready
    var requestAuth = false
    init() {
        switch SFSpeechRecognizer.authorizationStatus() {
            case .notDetermined:
                self.askSpeechPermission()
            case .authorized:
                self.status = .ready
            case .denied, .restricted:
                self.status = .unavailable
            default:
                self.status = .unavailable
        }

    }


    /// Ask permission to the user to access their speech data.
    func askSpeechPermission() {
        SFSpeechRecognizer.requestAuthorization { authState in
            OperationQueue.main.addOperation {
                if authState == .authorized {
                    print("is authorized") // <---- is printed!
                    self.requestAuth = true
                    self.recognizedText = "Authorized"
                    // do other things based on authorization status
                } else {
                    self.requestAuth = false
                    self.recognizedText = "Not authorized"
                }
            }
        }



//         print("askSpeechPermission 1")
//         SFSpeechRecognizer.requestAuthorization { status in
//             OperationQueue.main.addOperation {
//             print("askSpeechPermission 2")
//                 switch status {
//                 case .authorized:
//                     self.status = .ready
//                 default:
//                     self.status = .unavailable
//                 }
//             }
//         }
//         print("askSpeechPermission 3")
    }

    /// Start streaming the microphone data to the speech recognizer to recognize it live.
    func startRecording() {
        print("1")
        // Setup audio engine and speech recognizer
        let node = audioEngine.inputNode
        print("2")
        let recordingFormat = node.outputFormat(forBus: 0)
        print("3")
        node.installTap(onBus: 0, bufferSize: 1024, format: recordingFormat) { buffer, _ in
            self.request.append(buffer)
        }
        print("4")
        // Prepare and start recording
        audioEngine.prepare()
        print("5")
        do {
            try audioEngine.start()
            self.status = .recognizing
        } catch {
            print("6")
            return print(error)
        }

        // Analyze the speech
        recognitionTask = speechRecognizer?.recognitionTask(with: request, resultHandler: { result, error in
            if let result = result {
                self.recognizedText = result.bestTranscription.formattedString
                print(self.recognizedText)
            } else if let error = error {
                print(error)
            }
            print("recognitionTask executed")
        })
        print("7")
    }

    func cancelRecording() {
        audioEngine.stop()
        let node = audioEngine.inputNode
        node.removeTap(onBus: 0)
        recognitionTask?.cancel()
        print(self.recognizedText)
    }
}
let speechRec = SpeechRec()
speechRec.startRecording()
print("Listening")

do {
    sleep(4)
}
print("Did you say: ")
speechRec.cancelRecording()

print("hello there")
//print("hello")