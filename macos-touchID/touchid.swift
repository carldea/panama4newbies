import LocalAuthentication

@_cdecl("authenticate_user")
public func authenticateUser() {

   // Create the Local Authentication Context
   let context = LAContext()
   context.localizedCancelTitle = "Enter Username/Password"
   if context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: nil) {
      let reason = "Identify yourself!"
      print(reason)
      var runme = true
      context.evaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, localizedReason: reason, reply: {
          (success: Bool, error: Error?) -> Void in
             if success {
                print(" You may enter")
                runme = false
             } else {
                print(" Authentication failed")
                print(error?.localizedDescription ?? "Failed to authenticate")
                runme = false
             }
      })

    while runme {}

   } else {
      let ac = "Touch ID not available, Or Your device is not configured for Touch ID."
      print(ac)
   }
}
