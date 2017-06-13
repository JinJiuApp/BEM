package com.jaryjun.common_base.component.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaryjun.common_base.R;


/***
 * SingleInfoDialogFragment
 */
public class SingleInfoDialogFragment extends BaseDialogFragment {

    private SingleDialogFragmentCallBack mSingleDialogCallBack;

    public static SingleInfoDialogFragment getInstance(Bundle bundle) {
        SingleInfoDialogFragment baseDialogFragment = new SingleInfoDialogFragment();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    public SingleInfoDialogFragment() {

    }

    public void setSingleDialogCallBack(SingleDialogFragmentCallBack mSingleDialogCallBack) {
        this.mSingleDialogCallBack = mSingleDialogCallBack;
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
        View contentView = inflater.inflate(R.layout.base_main_single_dialog_layout, container, false);
        contentView.setOnClickListener(mSpaceClickListener);

        TextView dlgContent = (TextView) contentView.findViewById(R.id.content_text);
        if (!TextUtils.isEmpty(mContentTxt)) {
            dlgContent.setText(mContentTxt);
            dlgContent.setGravity(gravity);
        }

        /*View dialogBtn = contentView.findViewById(R.id.single_btn);
        dialogBtn.setClickable(true);*/

        TextView dialogBtnTxt = (TextView) contentView.findViewById(R.id.single_btn_txt);
        if (!TextUtils.isEmpty(mSingleBtnTxt)) {
            dialogBtnTxt.setText(mSingleBtnTxt);
        }

        /*dialogBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Avoid2Click.isFastDoubleClick()) return;
                if (null != mSingleDialogCallBack)
                    mSingleDialogCallBack.onSingleBtnClick(mDialogTag);
                dismissSelf();
            }
        });*/
        return contentView;
    }
}
