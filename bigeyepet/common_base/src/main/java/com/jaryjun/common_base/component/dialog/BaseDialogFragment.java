package com.jaryjun.common_base.component.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.jaryjun.common_base.R;


/***
 * BaseDialogFragment
 */
public class BaseDialogFragment extends DialogFragment {
    public final static String TAG = "BaseDialogFragment";
    protected String mDialogTag;// 标记
    protected String mPositiveBtnTxt;// 确认操作
    protected String mNegativeBtnTxt;// 取消操作
    protected String mSingleBtnTxt;// 单个button文字
    protected String mContentTxt;// 内容
    public boolean bIsBackAble;// 是否back取消
    public boolean bIsSpaceAble;// 是否空白取消
    public int gravity = Gravity.CENTER;
    protected OnClickListener mSpaceClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (bIsSpaceAble) {
                dismissSelf();
            }
        }
    };

    public static BaseDialogFragment getInstance(Bundle bundle) {
        BaseDialogFragment baseDialogFragment = new BaseDialogFragment();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    public BaseDialogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogTheme);
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
                bIsBackAble = dialogExchangeModel.isBackAble();
                bIsSpaceAble = dialogExchangeModel.isSpaceAble();
                mContentTxt = dialogExchangeModel.getDialogContext();
                setCancelable(bIsBackAble);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.60f;
        //windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return show(transaction, tag, true);
    }

    public int show(FragmentTransaction transaction, String tag, boolean allowStateLoss) {
        transaction.add(this, tag);
        int mBackStackId;
        if (allowStateLoss) mBackStackId = transaction.commitAllowingStateLoss();
        else mBackStackId = transaction.commit();
        return mBackStackId;
    }

    public void dismissSelf() {
        dismiss();
    }

    @SuppressWarnings("unused")
    public void setOnSpaceClickListener(OnClickListener onSpaceClickListener) {
        mSpaceClickListener = onSpaceClickListener;
    }

}
