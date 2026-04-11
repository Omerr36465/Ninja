package com.example.samuraishiftautomator;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.example.samuraishiftautomator.BuildConfig;

import java.util.List;

public class SamuraiShiftAccessibilityService extends AccessibilityService {

    private static final String TAG = "SamuraiShiftAccService";
    private static final String SAMURAI_PACKAGE_NAME = "delivery.samurai.android";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getPackageName() == null || !event.getPackageName().equals(SAMURAI_PACKAGE_NAME)) {
            return;
        }

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) {
            if (BuildConfig.DEBUG) Log.e(TAG, "Root node is null");
            return;
        }

        if (BuildConfig.DEBUG) Log.d(TAG, "Event Type: " + event.getEventType() + ", Package Name: " + event.getPackageName());

        // Example: Find a button by text and click it
        // This is a placeholder. Actual implementation will depend on Samurai app's UI structure.
        List<AccessibilityNodeInfo> buttons = rootNode.findAccessibilityNodeInfosByText("Book Shift");
        if (!buttons.isEmpty()) {
            for (AccessibilityNodeInfo button : buttons) {
                if (button.isClickable()) {
                    button.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    if (BuildConfig.DEBUG) Log.d(TAG, "Clicked 'Book Shift' button");
                    Toast.makeText(this, "Attempting to book shift!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        // You would need to analyze the Samurai app's UI to find specific elements
        // For example, to find an element by its ID (if available):
        // List<AccessibilityNodeInfo> nodesWithId = rootNode.findAccessibilityNodeInfosByViewId("com.samurai.app:id/shift_area_selector");
        // if (!nodesWithId.isEmpty()) {
        //     AccessibilityNodeInfo shiftAreaSelector = nodesWithId.get(0);
        //     shiftAreaSelector.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        // }

        // To find input fields and set text:
        // List<AccessibilityNodeInfo> inputFields = rootNode.findAccessibilityNodeInfosByClassName("android.widget.EditText");
        // if (!inputFields.isEmpty()) {
        //     Bundle arguments = new Bundle();
        //     arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "Desired Shift Area");
        //     inputFields.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        // }

        rootNode.recycle();
    }

    @Override
    public void onInterrupt() {
        if (BuildConfig.DEBUG) Log.d(TAG, "Accessibility Service Interrupted");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_SCROLLED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS | AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        info.packageNames = new String[]{SAMURAI_PACKAGE_NAME};
        setServiceInfo(info);
        if (BuildConfig.DEBUG) Log.d(TAG, "Samurai Shift Accessibility Service Connected");
        Toast.makeText(this, "Samurai Shift Accessibility Service Connected", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Samurai Shift Accessibility Service Unbound");
        Toast.makeText(this, "Samurai Shift Accessibility Service Unbound", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }
}
