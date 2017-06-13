package com.jaryjun.common_base.component.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaryjun.common_base.R;
import com.jaryjun.common_base.user.Avoid2Click;


/***
 * ExecuteInfoDialogFragment
 */
public class ExecuteInfoDialogFragment extends BaseDialogFragment {

    private ExecuteDialogFragmentCallBack mExecuteDialogCallBack;
    private DialogExchangeModel mDialogExchangeModel = null;

    public static ExecuteInfoDialogFragment getInstance(Bundle bundle) {
        ExecuteInfoDialogFragment baseDialogFragment = new ExecuteInfoDialogFragment();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    public ExecuteInfoDialogFragment() {

    }

    public void setExecuteDialogCallBack(ExecuteDialogFragmentCallBack mExecuteDialogCallBack) {
        this.mExecuteDialogCallBack = mExecuteDialogCallBack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            DialogExchangeModel.DialogExchangeModelBuilder builder =
                    (DialogExchangeModel.DialogExchangeModelBuilder) bundle.getSerializable(TAG);
            if (null != builder) {
                mDialogExchangeModel = builder.create();
            }
            if (mDialogExchangeModel != null) {
                mDialogTag = mDialogExchangeModel.getTag();
                mPositiveBtnTxt = mDialogExchangeModel.getPositiveText();
                mNegativeBtnTxt = mDialogExchangeModel.getNegativeText();
                mContentTxt = mDialogExchangeModel.getDialogContext();
                gravity = mDialogExchangeModel.getGravity();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_main_execute_dialog_layout, container, false);
        view.setOnClickListener(mSpaceClickListener);
        TextView dlgContent = (TextView) view.findViewById(R.id.content_text);
        if (!TextUtils.isEmpty(mContentTxt)) {
            dlgContent.setText(mContentTxt);
            if (gravity != -1) {
                dlgContent.setGravity(gravity);
            }
        }
        View btnLeft = view.findViewById(R.id.lef_btn);
        TextView btnLeftTxt = (TextView) view.findViewById(R.id.lef_btn_txt);
        View rightBtn = view.findViewById(R.id.right_btn);
        TextView rightBtnTxt = (TextView) view.findViewById(R.id.right_btn_txt);

        OnClickListener executePositiveListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Avoid2Click.isFastDoubleClick()) return;
                if (null != mExecuteDialogCallBack)
                    mExecuteDialogCallBack.onPositiveBtnClick(mDialogTag);
                dismissSelf();
            }
        };

        OnClickListener executeNegativeListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Avoid2Click.isFastDoubleClick()) return;
                if (null != mExecuteDialogCallBack)
                    mExecuteDialogCallBack.onNegativeBtnClick(mDialogTag);
                dismissSelf();
            }
        };

        if (!TextUtils.isEmpty(mPositiveBtnTxt)) {
            rightBtnTxt.setText(mPositiveBtnTxt);
        } else {
            rightBtnTxt.setText(R.string.ok);
        }
        if (mDialogExchangeModel.getPositiveTextStyle() != -1)
            rightBtnTxt.setTextAppearance(view.getContext(), mDialogExchangeModel.getPositiveTextStyle());

        rightBtn.setOnClickListener(executePositiveListener);

        if (!TextUtils.isEmpty(mNegativeBtnTxt)) {
            btnLeftTxt.setText(mNegativeBtnTxt);
        } else {
            btnLeftTxt.setText(R.string.cancel);
        }
        if (mDialogExchangeModel.getNegativeTextStyle() != -1)
            btnLeftTxt.setTextAppearance(view.getContext(), mDialogExchangeModel.getNegativeTextStyle());

        btnLeft.setOnClickListener(executeNegativeListener);
        return view;
    }
}
