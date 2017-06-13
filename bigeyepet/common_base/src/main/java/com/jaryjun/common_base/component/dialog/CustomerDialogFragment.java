package com.jaryjun.common_base.component.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jaryjun.common_base.ops.LFUiOps;


public class CustomerDialogFragment extends BaseDialogFragment {
    private CustomerFragmentCallBack mCustomerFragmentCallBack;

    public static CustomerDialogFragment getInstance(Bundle bundle) {
        CustomerDialogFragment baseDialogFragment = new CustomerDialogFragment();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    public CustomerDialogFragment() {

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
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mContentView;
        if (null != mCustomerFragmentCallBack) {
            mContentView = mCustomerFragmentCallBack.getCustomerView(mDialogTag);
        } else {
            throw new IllegalArgumentException("In CustomerDialogFragment, You must set mCustomerFragmentCallBack");
        }

        FrameLayout layout = new FrameLayout(getActivity());
        layout.setClickable(true);
        layout.setOnClickListener(mSpaceClickListener);
        if (mContentView != null && mContentView.getLayoutParams() != null) {
            layout.addView(mContentView, mContentView.getLayoutParams());
            mContentView.setClickable(true);
        } else if (mContentView != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.width = LFUiOps.getScreenWidth(null);
            mContentView.setLayoutParams(layoutParams);
            layout.addView(mContentView, mContentView.getLayoutParams());
            mContentView.setClickable(true);
        }
        return layout;
    }

    public void setCustomerFragmentCallBack(CustomerFragmentCallBack mCustomerFragmentCallBack) {
        this.mCustomerFragmentCallBack = mCustomerFragmentCallBack;
    }
}
