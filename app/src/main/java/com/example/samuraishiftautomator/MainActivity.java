package com.example.samuraishiftautomator;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startServiceButton = findViewById(R.id.start_service_button);
        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOverlayPermission();
            }
        });

        Button stopServiceButton = findViewById(R.id.stop_service_button);
        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopFloatingBubbleService();
            }
        });

        Button enableAccessibilityButton = findViewById(R.id.enable_accessibility_button);
        enableAccessibilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccessibilitySettings();
            }
        });
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
        } else {
            startFloatingBubbleService();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startFloatingBubbleService();
            } else {
                Toast.makeText(this, R.string.overlay_permission_needed, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startFloatingBubbleService() {
        startService(new Intent(MainActivity.this, FloatingBubbleService.class));
        Toast.makeText(this, "Floating Bubble Service Started", Toast.LENGTH_SHORT).show();
    }

    private void stopFloatingBubbleService() {
        stopService(new Intent(MainActivity.this, FloatingBubbleService.class));
        Toast.makeText(this, "Floating Bubble Service Stopped", Toast.LENGTH_SHORT).show();
    }

    private void openAccessibilitySettings() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }
}
