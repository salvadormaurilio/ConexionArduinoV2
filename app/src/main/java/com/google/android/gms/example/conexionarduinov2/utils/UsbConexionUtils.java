package com.google.android.gms.example.conexionarduinov2.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.services.ArduinoCommunicatorService;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by sati on 20/05/2015.
 */
public class UsbConexionUtils {


    public static boolean findDevice(Activity context) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);

        UsbDevice usbDevice = null;
        HashMap<String, UsbDevice> usbDeviceList = usbManager.getDeviceList();
        if (ConstantsService.DEBUG) Log.d(ConstantsService.TAG, "length: " + usbDeviceList.size());

        Iterator<UsbDevice> deviceIterator = usbDeviceList.values().iterator();

        if (deviceIterator.hasNext()) {
            UsbDevice tempUsbDevice = deviceIterator.next();

            // Print device information. If you think your device should be able
            // to communicate with this app, add it to accepted products below.
            if (ConstantsService.DEBUG)
                Log.d(ConstantsService.TAG, "VendorId: " + tempUsbDevice.getVendorId());
            if (ConstantsService.DEBUG)
                Log.d(ConstantsService.TAG, "ProductId: " + tempUsbDevice.getProductId());
            if (ConstantsService.DEBUG)
                Log.d(ConstantsService.TAG, "DeviceName: " + tempUsbDevice.getDeviceName());
            if (ConstantsService.DEBUG)
                Log.d(ConstantsService.TAG, "DeviceId: " + tempUsbDevice.getDeviceId());
            if (ConstantsService.DEBUG)
                Log.d(ConstantsService.TAG, "DeviceClass: " + tempUsbDevice.getDeviceClass());
            if (ConstantsService.DEBUG)
                Log.d(ConstantsService.TAG, "DeviceSubclass: " + tempUsbDevice.getDeviceSubclass());
            if (ConstantsService.DEBUG)
                Log.d(ConstantsService.TAG, "InterfaceCount: " + tempUsbDevice.getInterfaceCount());
            if (ConstantsService.DEBUG)
                Log.d(ConstantsService.TAG, "DeviceProtocol: " + tempUsbDevice.getDeviceProtocol());

            if (tempUsbDevice.getVendorId() == ConstantsService.ARDUINO_USB_VENDOR_ID) {
                if (ConstantsService.DEBUG) Log.i(ConstantsService.TAG, "Arduino device found!");

                switch (tempUsbDevice.getProductId()) {
                    case ConstantsService.ARDUINO_UNO_USB_PRODUCT_ID:
//                        Toast.makeText(getBaseContext(), "Arduino Uno " + getString(R.string.found), Toast.LENGTH_SHORT).show();
                        usbDevice = tempUsbDevice;
                        break;
                    case ConstantsService.ARDUINO_MEGA_2560_USB_PRODUCT_ID:
//                        Toast.makeText(getBaseContext(), "Arduino Mega 2560 " + getString(R.string.found), Toast.LENGTH_SHORT).show();
                        usbDevice = tempUsbDevice;
                        break;
                    case ConstantsService.ARDUINO_MEGA_2560_R3_USB_PRODUCT_ID:
//                        Toast.makeText(getBaseContext(), "Arduino Mega 2560 R3 " + getString(R.string.found), Toast.LENGTH_SHORT).show();
                        usbDevice = tempUsbDevice;
                        break;
                    case ConstantsService.ARDUINO_UNO_R3_USB_PRODUCT_ID:
//                        Toast.makeText(getBaseContext(), "Arduino Uno R3 " + getString(R.string.found), Toast.LENGTH_SHORT).show();
                        usbDevice = tempUsbDevice;
                        break;
                    case ConstantsService.ARDUINO_MEGA_2560_ADK_R3_USB_PRODUCT_ID:
//                        Toast.makeText(getBaseContext(), "Arduino Mega 2560 ADK R3 " + getString(R.string.found), Toast.LENGTH_SHORT).show();
                        usbDevice = tempUsbDevice;
                        break;
                    case ConstantsService.ARDUINO_MEGA_2560_ADK_USB_PRODUCT_ID:
//                        Toast.makeText(getBaseContext(), "Arduino Mega 2560 ADK " + getString(R.string.found), Toast.LENGTH_SHORT).show();
                        usbDevice = tempUsbDevice;
                        break;
                    case ConstantsService.ARDUINO_DUE_USB_PRODUCT_ID:
//                        Toast.makeText(getBaseContext(), "Arduino Due " + getString(R.string.found), Toast.LENGTH_SHORT).show();
                        usbDevice = tempUsbDevice;
                }
            }
        }

        if (usbDevice == null) {
            if (ConstantsService.DEBUG) Log.i(ConstantsService.TAG, "No device found!");
            Toast.makeText(context, R.string.no_device_found, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (ConstantsService.DEBUG) Log.i(ConstantsService.TAG, "Device found!");
            Toast.makeText(context, R.string.device_found, Toast.LENGTH_SHORT).show();
            Intent startIntent = new Intent(context, ArduinoCommunicatorService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, startIntent, 0);
            usbManager.requestPermission(usbDevice, pendingIntent);
            return true;
        }
    }

    public static void sendData(Context context, byte[] data) {

        Intent intent = new Intent(ConstantsService.SEND_DATA_INTENT);
        intent.putExtra(ConstantsService.DATA_EXTRA, data);
        context.sendBroadcast(intent);

    }
}
