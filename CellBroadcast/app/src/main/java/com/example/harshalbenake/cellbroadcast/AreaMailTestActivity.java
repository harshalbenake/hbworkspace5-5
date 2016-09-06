package com.example.harshalbenake.cellbroadcast;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import android.content.Intent;
import android.provider.Telephony.Sms.Intents;
import android.util.Log;

import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.IccUtils;

public class AreaMailTestActivity extends Activity {
    private static String TAG = "AreaMailTestActivity";

    private static final int DCS_7BIT_ENGLISH = 0x01;
    private static final int DCS_16BIT_UCS2 = 0x48;

    /* ETWS Test message including header */
    private static final byte[] etwsMessageNormal = IccUtils.hexStringToBytes("000011001101" +
            //   Line 1                  CRLFLine 2
            "EA307DCA602557309707901F58310D0A5BAE57CE770C531790E85C716CBF3044573065B930675730" +
            "9707767A751F30025F37304463FA308C306B5099304830664E0B30553044FF086C178C615E81FF09");

    private static final byte[] etwsMessageCancel = IccUtils.hexStringToBytes("000011001101" +
            //   Line 1                                  CRLFLine 2
            "EA307DCA602557309707901F5831002853D66D8800290D0A5148307B3069002800310030003A0035" +
            "00320029306E7DCA602557309707901F5831309253D66D883057307E3059FF086C178C615E81FF09");

    private static final byte[] etwsMessageTest = IccUtils.hexStringToBytes("000011031101" +
            //   Line 1                                  CRLFLine 2
            "EA3030108A137DF430117DCA602557309707573058310D0A5BAE57CE770C531790E85C716CBF3044" +
            "573065B9306757309707300263FA308C306B5099304830664E0B30553044FF086C178C615E81FF09");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    testSendEtwsMessageNormal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    testSendEtwsMessageCancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    byte[] encodeCellBroadcast(int serialNumber, int messageId, int dcs, String message) {
        byte[] pdu = new byte[88];
        pdu[0] = (byte) ((serialNumber >> 8) & 0xff);
        pdu[1] = (byte) (serialNumber & 0xff);
        pdu[2] = (byte) ((messageId >> 8) & 0xff);
        pdu[3] = (byte) (messageId & 0xff);
        pdu[4] = (byte) (dcs & 0xff);
        pdu[5] = 0x11;  // single page message
        try {
            byte[] encodedString;
            if (dcs == DCS_16BIT_UCS2) {
                encodedString = message.getBytes("UTF-16");
                System.arraycopy(encodedString, 0, pdu, 6, encodedString.length);
            } else {
                // byte 0 of encodedString is the length in septets (don't copy)
                encodedString = GsmAlphabet.stringToGsm7BitPacked(message);
                System.arraycopy(encodedString, 1, pdu, 6, encodedString.length-1);
            }
            return pdu;
        } catch (EncodeException e) {
            Log.e(TAG, "Encode Exception");
            return null;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Unsupported encoding exception for UTF-16");
            return null;
        }
    }

    public void testSendMessage7bit() throws Exception {
        Intent intent = new Intent(Intents.SMS_CB_RECEIVED_ACTION);
        byte[][] pdus = new byte[1][];
        pdus[0] = encodeCellBroadcast(0, 0, DCS_7BIT_ENGLISH, "Hello in GSM 7 bit");
        intent.putExtra("pdus", pdus);
        sendOrderedBroadcast(intent, "android.permission.RECEIVE_SMS");
    }

    public void testSendMessageUCS2() throws Exception {
        Intent intent = new Intent(Intents.SMS_CB_RECEIVED_ACTION);
        byte[][] pdus = new byte[1][];
        pdus[0] = encodeCellBroadcast(0, 0, DCS_16BIT_UCS2, "Hello in UCS2");
        intent.putExtra("pdus", pdus);
        sendOrderedBroadcast(intent, "android.permission.RECEIVE_SMS");
    }

    public void testSendEtwsMessageNormal() throws Exception {
        Intent intent = new Intent(Intents.SMS_EMERGENCY_CB_RECEIVED_ACTION);
        byte[][] pdus = new byte[1][];
        pdus[0] = etwsMessageNormal;
        intent.putExtra("pdus", pdus);
        sendOrderedBroadcast(intent,
                "android.permission.RECEIVE_EMERGENCY_BROADCAST");
    }

    public void testSendEtwsMessageCancel() throws Exception {
        Intent intent = new Intent(Intents.SMS_EMERGENCY_CB_RECEIVED_ACTION);
        byte[][] pdus = new byte[1][];
        pdus[0] = etwsMessageCancel;
        intent.putExtra("pdus", pdus);
        sendOrderedBroadcast(intent,
                "android.permission.RECEIVE_EMERGENCY_BROADCAST");
    }

    public void testSendEtwsMessageTest() throws Exception {
        Intent intent = new Intent(Intents.SMS_EMERGENCY_CB_RECEIVED_ACTION);
        byte[][] pdus = new byte[1][];
        pdus[0] = etwsMessageTest;
        intent.putExtra("pdus", pdus);
        sendOrderedBroadcast(intent,
                "android.permission.RECEIVE_EMERGENCY_BROADCAST");
    }
}