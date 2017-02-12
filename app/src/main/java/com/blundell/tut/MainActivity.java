package com.blundell.tut;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class MainActivity extends Activity {

    private static final String TOUCH_BUTTON_A_PIN = "BCM21";

    private Gpio bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PeripheralManagerService service = new PeripheralManagerService();

        try {
            bus = service.openGpio(TOUCH_BUTTON_A_PIN);
        } catch (IOException e) {
            throw new IllegalStateException(TOUCH_BUTTON_A_PIN + " bus cannot be opened.", e);
        }

        try {
            bus.setDirection(Gpio.DIRECTION_IN);
            bus.setActiveType(Gpio.ACTIVE_LOW);
        } catch (IOException e) {
            throw new IllegalStateException(TOUCH_BUTTON_A_PIN + " bus cannot be configured.", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            bus.close();
        } catch (IOException e) {
            Log.e("TUT", TOUCH_BUTTON_A_PIN + " bus cannot be closed, you may experience errors on next launch.", e);
        }
    }
}
