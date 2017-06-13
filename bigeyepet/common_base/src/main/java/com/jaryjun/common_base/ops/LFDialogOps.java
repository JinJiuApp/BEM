package com.jaryjun.common_base.ops;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jaryjun.common_base.component.dialog.BaseDialogFragment;
import com.jaryjun.common_base.component.dialog.CustomerDialogFragment;
import com.jaryjun.common_base.component.dialog.DialogExchangeModel;
import com.jaryjun.common_base.component.dialog.DialogType;
import com.jaryjun.common_base.component.dialog.ExecuteInfoDialogFragment;
import com.jaryjun.common_base.component.dialog.ProgressDialogFragment;
import com.jaryjun.common_base.component.dialog.SingleInfoDialogFragment;


public class LFDialogOps {
    public final static int DIALOG_REQUEST_CODE = 0x3001;

    /**
     * 弹框方法
     *
     * @param fragmentManager     (必传字段)
     * @param dialogExchangeModel (必传字段)
     * @return BaseDialogFragment
     */
    public static BaseDialogFragment showDialogFragment(FragmentManager fragmentManager, DialogExchangeModel dialogExchangeModel) {
        return showDialogFragment(fragmentManager, dialogExchangeModel, null);
    }

    /**
     * 弹框方法
     *
     * @param fragmentManager     (必传字段)
     * @param dialogExchangeModel (必传字段)
     * @param fragment            (选传)
     * @return BaseDialogFragment
     */
    public static BaseDialogFragment showDialogFragment(FragmentManager fragmentManager, DialogExchangeModel dialogExchangeModel,
                                                        Fragment fragment) {

        BaseDialogFragment baseDialogFragment = null;
        if (dialogExchangeModel != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(BaseDialogFragment.TAG, dialogExchangeModel.dialogExchangeModelBuilder);
            DialogType dialogType = dialogExchangeModel.getDialogType();
            if (dialogType == DialogType.SINGLE) {
                baseDialogFragment = SingleInfoDialogFragment.getInstance(bundle);
                ((SingleInfoDialogFragment) baseDialogFragment).setSingleDialogCallBack(dialogExchangeModel.getSingleDialogCallBack());
            } else if (dialogType == DialogType.EXECUTE) {
                baseDialogFragment = ExecuteInfoDialogFragment.getInstance(bundle);
                ((ExecuteInfoDialogFragment) baseDialogFragment).setExecuteDialogCallBack(dialogExchangeModel.getExecuteDialogCallBack());
            } else if (dialogType == DialogType.CUSTOMER) {
                baseDialogFragment = CustomerDialogFragment.getInstance(bundle);
                ((CustomerDialogFragment) baseDialogFragment).setCustomerFragmentCallBack(dialogExchangeModel.getCustomerFragmentCallBack());
            } else if (dialogType == DialogType.PROGRESS) {
                baseDialogFragment = ProgressDialogFragment.getInstance(bundle);
            }
        }
        if (baseDialogFragment != null) {
            if (fragment != null) {
                baseDialogFragment.setTargetFragment(fragment, DIALOG_REQUEST_CODE);
            }
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(baseDialogFragment, dialogExchangeModel.getTag());
            ft.commitAllowingStateLoss();
        }
        return baseDialogFragment;
    }
}
