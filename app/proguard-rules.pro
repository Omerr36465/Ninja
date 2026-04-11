# ProGuard rules for SamuraiShiftAutomator

# Keep accessibility service class and its methods
-keep class com.example.samuraishiftautomator.SamuraiShiftAccessibilityService {
    public *;
    protected *;
}

# Keep floating bubble service
-keep class com.example.samuraishiftautomator.FloatingBubbleService {
    public *;
    protected *;
}

# Keep MainActivity
-keep class com.example.samuraishiftautomator.MainActivity {
    public *;
    protected *;
}

# Keep Android accessibility framework classes
-keep class android.accessibilityservice.** { *; }

# Keep AndroidX libraries
-keep class androidx.** { *; }
-dontwarn androidx.**

# Keep Material Design components
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static int d(...);
    public static int v(...);
    public static int i(...);
}
