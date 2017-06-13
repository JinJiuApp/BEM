package com.junwu.plugin.empty;

import android.os.Bundle;
import android.widget.TextView;

import com.jaryjun.common_base.user.LFBaseActivity;
import com.junwu.plugin.R;


/**
 * Created by Glen on 2016/6/8.
 * To modify the relevant code, please contact the developer Glen
 */
public class EmptyActivity extends LFBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        text.setText(getResources().getString(R.string.plugin_activity_empty_info));
        setContentView(text);
    }
}
