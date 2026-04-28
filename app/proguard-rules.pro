# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Razorpay rules
-keep class com.razorpay.** {*;}
-dontwarn com.razorpay.**
-dontwarn proguard.annotation.**

# Firebase Data Models
# Keep the classes in the model package to prevent R8 from obfuscating them.
# Firebase uses reflection to map database fields to class properties.
-keep class com.example.myecomartapp.domain.model.** { *; }
-keep class com.example.myecomartapp.domain.remote.** { *; }

# Keep GMS and Firebase classes
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Maintain annotations used by Firebase and Serialization
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod,InnerClasses
