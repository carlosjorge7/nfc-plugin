package com.mycompany.plugins.example;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import java.io.IOException;

@CapacitorPlugin(name = "NFCPlugin", permissions = { @Permission(strings = { android.Manifest.permission.NFC }, alias = "nfc") })
public class NFCPluginPlugin extends Plugin {

    private static final String TAG = "NFCPlugin";
    private NfcAdapter nfcAdapter;
    private Tag nfcTag;

    @Override
    public void load() {
        super.load();
        Activity activity = getActivity();
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdapter == null) {
            Log.e(TAG, "NFC is not supported on this device.");
        }
    }

    @PluginMethod
    public void readTag(PluginCall call) {
        if (nfcTag == null) {
            Log.e(TAG, "No NFC tag found");
            call.reject("No NFC tag found");
            return;
        }

        Ndef ndef = Ndef.get(nfcTag);
        if (ndef == null) {
            Log.e(TAG, "NFC tag is not NDEF formatted");
            call.reject("NFC tag is not NDEF formatted");
            return;
        }

        try {
            ndef.connect();
            Log.d(TAG, "Connected to NFC tag");
            NdefMessage ndefMessage = ndef.getNdefMessage();
            Log.d(TAG, "NDEF message read");
            NdefRecord[] records = ndefMessage.getRecords();
            StringBuilder result = new StringBuilder();
            for (NdefRecord record : records) {
                result.append(new String(record.getPayload()));
            }
            JSObject ret = new JSObject();
            ret.put("message", result.toString());
            call.resolve(ret);
            Log.d(TAG, "NFC tag reading successful");
        } catch (IOException | android.nfc.FormatException e) {
            Log.e(TAG, "Error reading NFC tag", e);
            call.reject("Error reading NFC tag", e);
        } finally {
            try {
                ndef.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing NFC connection", e);
            }
        }
    }

    @PluginMethod
    public void writeTag(PluginCall call) {
        if (nfcTag == null) {
            call.reject("No NFC tag found");
            return;
        }

        String url = call.getString("url");
        if (url == null || url.isEmpty()) {
            call.reject("No url provided");
            return;
        }

        Ndef ndef = Ndef.get(nfcTag);
        if (ndef == null) {
            call.reject("NFC tag is not NDEF formatted");
            return;
        }

        try {
            // Crear un NdefRecord con el tipo URI
            NdefRecord uriRecord = NdefRecord.createUri(url);
            NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] { uriRecord });

            ndef.connect();
            ndef.writeNdefMessage(ndefMessage);
            call.resolve();
        } catch (IOException | android.nfc.FormatException e) {
            call.reject("Error writing NFC tag", e);
        } finally {
            try {
                ndef.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleOnNewIntent(Intent intent) {
        super.handleOnNewIntent(intent);

        // Actualiza el intent dentro de la actividad
        Activity activity = getActivity();
        if (activity != null) {
            activity.setIntent(intent); // Actualiza el intent en la actividad
        }

        // Procesa el intent de NFC
        nfcTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (nfcTag != null) {
            byte[] tagId = nfcTag.getId();
            String tagIdString = bytesToHex(tagId);
            Log.d(TAG, "NFC Tag detected: " + tagIdString);

            // Crear un JSObject con el ID del tag
            JSObject ret = new JSObject();
            ret.put("tagId", tagIdString);
            notifyListeners("nfcTagDetected", ret); // Notifica a los listeners de la app
        } else {
            Log.d(TAG, "No NFC Tag detected");
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
