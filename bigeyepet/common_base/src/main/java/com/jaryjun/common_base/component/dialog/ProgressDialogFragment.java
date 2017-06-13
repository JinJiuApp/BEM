package com.jaryjun.common_base.component.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaryjun.common_base.R;


public class ProgressDialogFragment extends BaseDialogFragment {

    public static ProgressDialogFragment getInstance(Bundle bundle) {
        ProgressDialogFragment baseDialogFragment = new ProgressDialogFragment();
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
    public void dismissSelf() {
        super.dismissSelf();
    }
}
