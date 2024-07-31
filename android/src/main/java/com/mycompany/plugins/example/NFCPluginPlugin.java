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
            call.reject("No NFC tag found");
            return;
        }

        Ndef ndef = Ndef.get(nfcTag);
        if (ndef == null) {
            call.reject("NFC tag is not NDEF formatted");
            return;
        }

        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            NdefRecord[] records = ndefMessage.getRecords();
            StringBuilder result = new StringBuilder();
            for (NdefRecord record : records) {
                result.append(new String(record.getPayload()));
            }
            JSObject ret = new JSObject();
            ret.put("message", result.toString());
            call.resolve(ret);
        } catch (IOException | android.nfc.FormatException e) {
            call.reject("Error reading NFC tag", e);
        } finally {
            try {
                ndef.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PluginMethod
    public void writeTag(PluginCall call) {
        if (nfcTag == null) {
            call.reject("No NFC tag found");
            return;
        }

        String message = call.getString("message");
        if (message == null || message.isEmpty()) {
            call.reject("No message provided");
            return;
        }

        Ndef ndef = Ndef.get(nfcTag);
        if (ndef == null) {
            call.reject("NFC tag is not NDEF formatted");
            return;
        }

        NdefRecord record = NdefRecord.createTextRecord("en", message);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] { record });

        try {
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
    protected void handleOnNewIntent(Intent intent) {
        super.handleOnNewIntent(intent);
        nfcTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (nfcTag != null) {
            Log.d(TAG, "NFC Tag detected: " + nfcTag.toString());
        } else {
            Log.d(TAG, "No NFC Tag detected");
        }
    }
}
