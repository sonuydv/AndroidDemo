package android.form.avss.hotspotmanager;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.appcompat.app.AppCompatActivity;

public class MainWAP extends AppCompatActivity {

    WifiManager wifiManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wap);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        openWifi();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    public void openWifi() {
        createWifiAccessPoint();
    }

    private void createWifiAccessPoint() {
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
        Method[] wmMethods = wifiManager.getClass().getDeclaredMethods();
        boolean methodFound = false;
        for (Method method : wmMethods) {
            if (method.getName().equals("setWifiApEnabled")) {
                methodFound = true;
                WifiConfiguration netConfig = new WifiConfiguration();
                netConfig.SSID = "AccessPoint";
                netConfig.allowedAuthAlgorithms.set(
                        WifiConfiguration.AuthAlgorithm.OPEN);
                try {
                    boolean apstatus = (Boolean) method.invoke(
                            wifiManager, netConfig, true);
                    for (Method isWifiApEnabledmethod : wmMethods) {
                        if (isWifiApEnabledmethod.getName().equals(
                                "isWifiApEnabled")) {
                            while (!(Boolean) isWifiApEnabledmethod.invoke(
                                    wifiManager)) {
                            }
                            ;
                            for (Method method1 : wmMethods) {
                                if (method1.getName().equals(
                                        "getWifiApState")) {
                                    int apstate;
                                    apstate = (Integer) method1.invoke(
                                            wifiManager);
                                }
                            }
                        }
                    }
                    if (apstatus) {
                        Log.d("Splash Activity",
                                "Access Point created");
                    } else {
                        Log.d("Splash Activity",
                                "Access Point creation failed");
                    }

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!methodFound) {
            Log.d("Splash Activity",
                    "cannot configure an access point");
        }
    }
}
