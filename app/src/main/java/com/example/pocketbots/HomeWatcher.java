package com.example.pocketbots;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/******************************************
 *   HomeWatcher Class
 *   - Checks User Activity
 *   - Suspends, Resumes, Nulls System
 *   Resources
 ******************************************/
public class HomeWatcher {

    //static final String TAG = "hg";
    private Context mContext;
    private IntentFilter mFilter;
    private OnHomePressedListener mListener;
    private InnerReceiver mReceiver;

    /******************************************
     *   HomeWatcher
     ******************************************/
    public HomeWatcher(Context context) {
        mContext = context;
        mFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
    }

    /******************************************
     *   Set On Home Pressed Listener
     ******************************************/
    public void setOnHomePressedListener(OnHomePressedListener listener) {
        mListener = listener;
        mReceiver = new InnerReceiver();
    }

    /******************************************
     *   Start Watch
     ******************************************/
    public void startWatch() {
        if (mReceiver != null) {
            mContext.registerReceiver(mReceiver, mFilter);
        }
    }

    /******************************************
     *   Stop Watch
     ******************************************/
    public void stopWatch() {
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    /******************************************
     *   Inner Receiver Class
     *   - Acts as a transmitter
     *   - Notifies program if User leaves
     *   the game, closes it, or suspends it.
     ******************************************/
    class InnerReceiver extends BroadcastReceiver {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        /******************************************
         *   On Receive
         ******************************************/
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    //Log.e(TAG, "action:" + action + ",reason:" + reason);
                    if (mListener != null) {
                        if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                            mListener.onHomePressed();
                        } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                            mListener.onHomeLongPressed();
                        }
                    }
                }
            }
        }
    }

    /******************************************
     *   On Home Pressed Listener
     ******************************************/
    public interface OnHomePressedListener {
        void onHomePressed();

        void onHomeLongPressed();
    }
}
