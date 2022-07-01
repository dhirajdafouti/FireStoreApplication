# FireStoreApplication

This application is the basic application for the authentication and firebase store basic.

The application will demonstrate
1-Firebase Cloud Store.
2-Firebase Authentication.
3-Firebase Security Rule.
4-App Check for accessing the firestore by authencicated user.

Below is the application Signing.
https://www.youtube.com/watch?v=akDXw9n3gFY 

https://www.youtube.com/watch?v=g1fByAsqQRk

https://docs.flutter.dev/deployment/android

Cloud Firestore documentation
1-https://firebase.google.com/docs/firestore/query-data/index-overview?hl=en&authuser=0
2-https://github.com/firebase/friendlyeats-android/blob/master/app/src/main/java/com/google/firebase/example/fireeats/MainActivity.java
3-https://firebase.google.com/codelabs/firestore-android#0

Generate the SH1 KEY

Microsoft Windows [Version 10.0.22000.739]
(c) Microsoft Corporation. All rights reserved.

C:\Users\dhira>keytool -list -v -keystore C:\Users\dhira\.android\debug.keystore -alias androiddebugkey -storepass android -keypass android
Picked up _JAVA_OPTIONS: -Xmx1024M
Alias name: androiddebugkey
Creation date: 15 Jul, 2021
Entry type: PrivateKeyEntry
Certificate chain length: 1
Certificate[1]:
Owner: C=US, O=Android, CN=Android Debug
Issuer: C=US, O=Android, CN=Android Debug
Serial number: 1
Valid from: Thu Jul 15 23:06:38 IST 2021 until: Sat Jul 08 23:06:38 IST 2051
Certificate fingerprints:
         SHA1: 0F:14:DC:36:3B:FE:B9:D6:08:05:5A:72:A6:AB:0E:70:B6:76:C8:22
         SHA256: 5D:97:1F:1A:48:11:83:91:76:9C:E8:2C:0F:A9:B3:CF:F2:29:9A:56:6E:23:81:CC:4C:FB:28:06:46:06:B0:27
Signature algorithm name: SHA1withRSA (weak)
Subject Public Key Algorithm: 2048-bit RSA key
Version: 1

Warning:
The certificate uses the SHA1withRSA signature algorithm which is considered a security risk. This algorithm will be disabled in a future update.
