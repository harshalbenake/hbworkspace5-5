package com.example.harshalbenake.cellbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class CbReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //---get the CB message passed in---
        Bundle bundle = intent.getExtras();
        SmsCbMessage[] msgs = null;
        String str = "";
        if (bundle != null)  {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsCbMessage[pdus.length];
            for (int i=0; i<msgs.length; i++) {
                msgs[i] = SmsCbMessage.createFromPdu((byte[])pdus[i]);
                str += "CB lang " + msgs[i].getLanguageCode();
                str += " :";
                str += msgs[i].getMessageBody().toString();
                str += "\n";
            }
            //---display the new CB message---
            abortBroadcast();
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }
}