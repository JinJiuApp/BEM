package com.jaryjun.common_base.component.dialog;

import android.view.Gravity;

import java.io.Serializable;

/***
 * DialogExchangeModel
 */
public class DialogExchangeModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3685432164096360692L;
    public DialogExchangeModelBuilder dialogExchangeModelBuilder;

    public DialogExchangeModel(DialogExchangeModelBuilder dialogExchangeModelBuilder) {
        this.dialogExchangeModelBuilder = dialogExchangeModelBuilder;
    }

    public DialogType getDialogType() {
        return dialogExchangeModelBuilder.dialogType;
    }

    public String getDialogContext() {
        return dialogExchangeModelBuilder.dialogContext;
    }

    public String getPositiveText() {
        return dialogExchangeModelBuilder.positiveText;
    }

    public int getPositiveTextStyle() {
        return dialogExchangeModelBuilder.positiveTextStyle;
    }

    public String getNegativeText() {
        return dialogExchangeModelBuilder.negativeText;
    }

    public int getNegativeTextStyle() {
        return dialogExchangeModelBuilder.negativeTextStyle;
    }

    public String getTag() {
        return dialogExchangeModelBuilder.tag;
    }

    public String getSingleText() {
        return dialogExchangeModelBuilder.singleText;
    }

    public boolean isBackAble() {
        return dialogExchangeModelBuilder.isBackAble;
    }

    public boolean isSpaceAble() {
        return dialogExchangeModelBuilder.isSpaceAble;
    }

    public int getGravity() {
        return dialogExchangeModelBuilder.gravity;
    }

    public SingleDialogFragmentCallBack getSingleDialogCallBack() {
        return dialogExchangeModelBuilder.singleDialogFragmentCallBack;
    }

    public ExecuteDialogFragmentCallBack getExecuteDialogCallBack() {
        return dialogExchangeModelBuilder.executeDialogFragmentCallBack;
    }

    public CustomerFragmentCallBack getCustomerFragmentCallBack() {
        return dialogExchangeModelBuilder.customerFragmentCallBack;
    }

    public static class DialogExchangeModelBuilder implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = -3685432164096360693L;
        /**
         * 弹出框类型
         */
        private DialogType dialogType = DialogType.SINGLE;
        /**
         * 弹出框内容
         */
        private String dialogContext = "";
        /**
         * 确认按键
         */
        private String positiveText = "";
        /**
         * 确认按键字体style
         */
        private int positiveTextStyle = -1;
        /**
         * 取消按键
         */
        private String negativeText = "";
        /**
         * 取消按键字体style
         */
        private int negativeTextStyle = -1;
        /**
         * 单按键
         */
        private String singleText = "";
        /**
         * tag
         */
        private String tag = "";
        /**
         * back可点（默认可点）
         */
        private boolean isBackAble = true;
        /**
         * 空白可点（默认可点）
         */
        private boolean isSpaceAble = true;

        private int gravity = Gravity.CENTER;

        private transient SingleDialogFragmentCallBack singleDialogFragmentCallBack;

        private transient ExecuteDialogFragmentCallBack executeDialogFragmentCallBack;

        private transient CustomerFragmentCallBack customerFragmentCallBack;

        public DialogExchangeModelBuilder(DialogType dialogType, String tag) {
            this.dialogType = dialogType;
            this.tag = tag;
        }

        public DialogExchangeModelBuilder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public DialogExchangeModelBuilder setDialogContext(String dialogContext) {
            this.dialogContext = dialogContext;
            return this;
        }

        public DialogExchangeModelBuilder setPositiveText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public DialogExchangeModelBuilder setPositiveTextStyle(int positiveTextStyleRes) {
            this.positiveTextStyle = positiveTextStyleRes;
            return this;
        }

        @SuppressWarnings("unused")
        public DialogExchangeModelBuilder setNegativeTextStyle(int negativeTextStyle) {
            this.negativeTextStyle = negativeTextStyle;
            return this;
        }

        public DialogExchangeModelBuilder setNegativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public DialogExchangeModelBuilder setSingleText(String singleText) {
            this.singleText = singleText;
            return this;
        }

        public DialogExchangeModelBuilder setBackAble(boolean isBackAble) {
            this.isBackAble = isBackAble;
            return this;
        }

        public DialogExchangeModelBuilder setSpaceAble(boolean isSpaceAble) {
            this.isSpaceAble = isSpaceAble;
            return this;
        }

        public DialogExchangeModelBuilder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public DialogExchangeModelBuilder setSingleDialogFragmentCallBack(SingleDialogFragmentCallBack singleDialogFragmentCallBack) {
            this.singleDialogFragmentCallBack = singleDialogFragmentCallBack;
            return this;
        }

        public DialogExchangeModelBuilder setExecuteDialogFragmentCallBack(ExecuteDialogFragmentCallBack executeDialogFragmentCallBack) {
            this.executeDialogFragmentCallBack = executeDialogFragmentCallBack;
            return this;
        }

        public DialogExchangeModelBuilder setCustomerFragmentCallBack(CustomerFragmentCallBack customerFragmentCallBack) {
            this.customerFragmentCallBack = customerFragmentCallBack;
            return this;
        }

        public DialogExchangeModel create() {
            return new DialogExchangeModel(this);
        }
    }
}
