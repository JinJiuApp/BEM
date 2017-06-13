package com.junwu.user.business.mine.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaryjun.common_base.component.widget.LFScrollView;
import com.jaryjun.common_base.component.widget.WKEditTextDel;
import com.jaryjun.common_base.model.UserInfoModel;
import com.jaryjun.common_base.ops.LFUserInfoOps;
import com.jaryjun.common_base.user.Avoid2Click;
import com.jaryjun.common_base.utils.SystemUtil;
import com.junwu.common_net.base.LFBaseServiceFragment;
import com.junwu.common_net.server.ViewServiceListener;
import com.junwu.user.R;
import com.junwu.user.business.bridge.iUi.ILoginFragUI;
import com.junwu.user.business.bridge.presenter.LoginFragPresenter;
import com.junwu.user.business.model.GuestInfo;
import com.junwu.user.business.servicemodel.response.LoginResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Qinbaoyuan on 2015/5/4.
 * Modify by wxy on 2015/11/13
 */
public class LoginFragment extends LFBaseServiceFragment implements ILoginFragUI {
    private LoginFragPresenter mPresenter;
    private TextView mSecurityCodeTxt;
    private WKEditTextDel mMobileNumberEdit;
    private WKEditTextDel mSecurityCodeEdit;
    private EditText mNumEdit;
    private EditText mSecurityEdit;
    private LinearLayout mRootLayout;
    private LinearLayout mVoiceToastView;
    private LFScrollView mRootScrollView;



    /**
     * View点击事件回调
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Avoid2Click.isFastDoubleClick()) return;
            if (v.getId() == R.id.id_login_root_view) {
                hideKeyboardView();
            } else if (v.getId() == R.id.bt_foget) {
                hideKeyboardView();

            } else if (v.getId() == R.id.bt_register) {
                hideKeyboardView();

            } else if (v.getId() == R.id.tv_login) {
                mPresenter.goToLogin();
            }
        }
    };

    private View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && mRootScrollView.getScrollY() < 200) {
                mRootScrollView.smoothScrollBy(0, 250);
            }
        }
    };

    private WKEditTextDel.TextChangedListener mMobileNumberEditWatcher = new WKEditTextDel.TextChangedListener() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mMobileNumberEdit.getEditText().getText().length() != 11) {
                mSecurityCodeTxt.setEnabled(false);
                mSecurityCodeTxt.setTextColor(getResources().getColor(R.color.app_text_color_nine));
                mSecurityCodeTxt.setText(getResources().getString(R.string.login_get_security_code));
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mMobileNumberEdit.getEditText().getText().length() == 11) {
                hideKeyboardView();
                mSecurityCodeTxt.setEnabled(true);
                mSecurityCodeTxt.setTextColor(getResources().getColor(android.R.color.white));
            } else {
                mSecurityCodeTxt.setEnabled(false);
                mSecurityCodeTxt.setTextColor(getResources().getColor(R.color.app_text_color_nine));
                mSecurityCodeTxt.setText(getResources().getString(R.string.login_get_security_code));
            }

        }
    };
    private TextView mTvFoget,mTvRegister,mTvLogin;

    /**
     * #########################Override方法#########################
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, null);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboardView();
    }

    @Override
    public void onDestroyView() {
        mPresenter.stopTimer();
        super.onDestroyView();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new LoginFragPresenter(getActivity(), this);
    }

    @Override
    public List<ViewServiceListener> getViewServiceListeners() {
        return new ArrayList<>();
    }


    @Override
    public void refreshTextView(int seconds) {
        if (null != mPresenter.getTimer()) {
            mSecurityCodeTxt.setText(seconds + getString(R.string.login_second));

        }
    }

    @Override
    public String getInputSecurityCode() {
        return mSecurityCodeEdit.getEditText().getText().toString();
    }



    @Override
    public String getInputMobileNumber() {
        return mMobileNumberEdit.getEditText().getText().toString();
    }

    @Override
    public void loginSucceed(LoginResponse response) {
        mPresenter.stopTimer();
        if (null != response && response.succeeded()) {
            GuestInfo guestInfo = response.getData().getGuestInfo();
            if (guestInfo.getGuestPhotoUrl() == null || TextUtils.isEmpty(guestInfo.getGuestPhotoUrl())) {

            }
            //更新存储信息
            updateUserInfo(guestInfo);

            //弹出消息提醒
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.login_succeed), Toast.LENGTH_SHORT).show();
            if (null == LFUserInfoOps.getUserInfo().getUserName()
                    || LFUserInfoOps.getUserInfo().getUserName().isEmpty()) {
                showUpdateGuestDialog();
            } else {
                //退出登录页面
                finishActivity();
            }
        } else {
            String errorMsg = null != response ? response.getMessage() : "";
            if (TextUtils.isEmpty(errorMsg))
                errorMsg = getActivity().getResources().getString(R.string.login_phone_num_or_security_code_wrong);
            focusEditTextView(LoginFragPresenter.FOCUS_SECURITY_EDIT);
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * #########################private方法#########################
     */
    public void showUpdateGuestDialog() {
       /* ConfirmGuestUpdateFragment confirmGuestUpdateFragment = ConfirmGuestUpdateFragment.getInts(null);
        confirmGuestUpdateFragment.setCallBack(new ConfirmGuestUpdateFragment.ICallBack() {
            @Override
            public void onFragmentClose(boolean isServiceSuccess) {
                finishActivity();
            }
        });
        LFFragmentOps.addFragmentNoAnim(getChildFragmentManager(), confirmGuestUpdateFragment, ConfirmGuestUpdateFragment.class.getCanonicalName(), R.id.frame_guest_update);
   */ }
    @Override
    public void loginFailed(String failMsg) {
        focusEditTextView(LoginFragPresenter.FOCUS_SECURITY_EDIT);
        if (!failMsg.isEmpty()) Toast.makeText(getActivity(), failMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 输入框聚焦
     */
    public void focusEditTextView(int type) {
        EditText edit = type == LoginFragPresenter.FOCUS_NUM_EDIT ? mNumEdit : mSecurityEdit;
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        edit.requestFocusFromTouch();
    }

    /**
     * 登录成功，关闭页面
     */
    public void finishActivity() {
        if (null != getActivity() && getActivity() instanceof LoginActivity) {
            Intent intent = new Intent();
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }




    private void initViews(View rootView) {
        mRootLayout = (LinearLayout) rootView.findViewById(R.id.id_login_root_view);
        mMobileNumberEdit = (WKEditTextDel) rootView.findViewById(R.id.id_mobile_number_edit);
        mSecurityCodeEdit = (WKEditTextDel) rootView.findViewById(R.id.id_security_code_edit);
        mRootScrollView = (LFScrollView) rootView.findViewById(R.id.frg_login_root_scroll_view);
        mTvFoget = (TextView) rootView.findViewById(R.id.bt_foget);
        mTvRegister = (TextView) rootView.findViewById(R.id.bt_register);
        mTvLogin = (TextView) rootView.findViewById(R.id.tv_login);
        initBaseViewsData();


    }

    /**
     * 设置页面View的基本数据
     */
    private void initBaseViewsData() {
        mRootLayout.setOnClickListener(mOnClickListener);
        mTvFoget.setOnClickListener(mOnClickListener);
        mTvRegister.setOnClickListener(mOnClickListener);
        mTvLogin.setOnClickListener(mOnClickListener);


        mNumEdit = mMobileNumberEdit.getEditText();
        setEditTextProperty(mNumEdit, 11);
        mMobileNumberEdit.setOnFocusChangeListener(mOnFocusChangeListener);
        mMobileNumberEdit.addTextChangedListener(mMobileNumberEditWatcher);

        mSecurityEdit = mSecurityCodeEdit.getEditText();
        mSecurityCodeEdit.setOnFocusChangeListener(mOnFocusChangeListener);
        clearFocus();
    }

    /***
     * 清除光标
     */
    private void clearFocus() {
        if (null != mNumEdit) mNumEdit.clearFocus();
        if (null != mSecurityEdit) mSecurityEdit.clearFocus();
        mRootLayout.requestFocus();
    }

    /***
     * 关闭键盘
     */
    private void hideKeyboardView() {
        clearFocus();
        mRootScrollView.scrollTo(0, 0);
        SystemUtil.hideSoftInput(getActivity());
    }

    /**
     * 设置输入框的属性
     *
     * @param editText
     * @param maxLength
     */
    private void setEditTextProperty(EditText editText, int maxLength) {
        editText.setTextColor(getResources().getColor(android.R.color.white));
        editText.setHintTextColor(getResources().getColor(R.color.app_text_color_nine));
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }


    @Override
    public void updateUserInfo(String name, int sex) {
        LFUserInfoOps.getUserInfo().setUserName(name);
        LFUserInfoOps.getUserInfo().setUserSex(sex);
        LFUserInfoOps.updateUserInfo(LFUserInfoOps.getUserInfo());
        finishActivity();
    }


    /**
     * 更新本地存储的用户信息
     *
     * @param guestInfo guestInfo
     */
    private void updateUserInfo(GuestInfo guestInfo) {
        UserInfoModel model = new UserInfoModel();
        model.setUserId(guestInfo.getGuestId());
        model.setUserPhoneNum(guestInfo.getPhoneNum());
        model.setUserName(guestInfo.getName());
        model.setUserSex(guestInfo.getSex());
        model.setLoginToken(guestInfo.getLoginKey());
        model.setPhotoUrl(guestInfo.getGuestPhotoUrl());
        model.setPushName(guestInfo.getPushAlias());
        LFUserInfoOps.updateUserInfo(model);
    }




}