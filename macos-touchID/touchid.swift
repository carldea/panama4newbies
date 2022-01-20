import LocalAuthentication

@_cdecl("authenticate_user")
public func authenticateUser() -> Bool  {

   // Create the Local Authentication Context
   let context = LAContext()
   context.localizedCancelTitle = "Enter Username/Password"
   if context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: nil) {
       let reason = "Identify yourself!"
      var runme = true
      var auth = false
      context.evaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, localizedReason: reason, reply: {
          (success: Bool, error: Error?) -> Void in
             if success {
                runme = false
                auth = true
             } else {
                print(error?.localizedDescription ?? "Failed to authenticate")
                runme = false
                auth = false
             }
      })

    while runme {
       //print("inside runme")
    }

    return auth
   } else {
      let ac = "Touch ID not available, Or Your device is not configured for Touch ID."
      print(ac)
      return true
   }
}
