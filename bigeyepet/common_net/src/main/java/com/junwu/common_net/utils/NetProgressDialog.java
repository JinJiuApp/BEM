package com.junwu.common_net.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaryjun.common_base.component.dialog.BaseDialogFragment;
import com.jaryjun.common_base.component.dialog.DialogExchangeModel;
import com.junwu.common_net.R;
import com.junwu.common_net.server.LFServiceError;
import com.junwu.common_net.server.ViewServiceListener;

public class NetProgressDialog extends BaseDialogFragment implements ViewServiceListener {

    public static NetProgressDialog getInstance(Bundle bundle) {
        NetProgressDialog baseDialogFragment = new NetProgressDialog();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            DialogExchangeModel.DialogExchangeModelBuilder builder =
                    (DialogExchangeModel.DialogExchangeModelBuilder) bundle.getSerializable(TAG);
            DialogExchangeModel dialogExchangeModel = null;
            if (null != builder) {
                dialogExchangeModel = builder.create();
            }
            if (dialogExchangeModel != null) {
                mDialogTag = dialogExchangeModel.getTag();
                mSingleBtnTxt = dialogExchangeModel.getSingleText();
                mContentTxt = dialogExchangeModel.getDialogContext();
                gravity = dialogExchangeModel.getGravity();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.base_main_progress_dialog_layout, container, false);
        contentView.setOnClickListener(mSpaceClickListener);
        return contentView;
    }

    @Override
    public void processView(boolean isFail, LFServiceError serviceError, boolean isProcessServiceError) {
        dismissSelf();

    }

    @Override
    public void dismissSelf() {
        super.dismissSelf();
    }
}
