package com.junwu.plugin.empty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junwu.common_net.base.LFBaseServiceFragment;
import com.junwu.plugin.R;


/**
 * Created by Glen on 2016/6/8.
 * To modify the relevant code, please contact the developer Glen
 */
public class EmptyFragment extends LFBaseServiceFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView text = new TextView(inflater.getContext());
        text.setText(getResources().getString(R.string.plugin_fragment_empty_info));
        return text;
    }
}
