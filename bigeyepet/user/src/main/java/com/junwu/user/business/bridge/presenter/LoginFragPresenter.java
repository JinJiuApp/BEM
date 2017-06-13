package com.junwu.user.business.bridge.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.jaryjun.common_base.component.dialog.DialogExchangeModel;
import com.jaryjun.common_base.component.dialog.DialogType;
import com.jaryjun.common_base.ops.LFDialogOps;
import com.junwu.common_net.base.Presenter;
import com.junwu.common_net.server.LFServiceError;
import com.junwu.common_net.server.OnServiceListener;
import com.junwu.user.R;
import com.junwu.user.business.bridge.iUi.ILoginFragUI;
import com.junwu.user.business.servicemodel.response.LoginResponse;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Qinbaoyuan on 2015/5/4.
 */
public class LoginFragPresenter extends Presenter {
    public static final int FOCUS_NUM_EDIT = 0x001001;
    public static final int FOCUS_SECURITY_EDIT = 0x001002;
    private static final int START_COUNT_DOWN = 0x01003;
    private static final int STOP_COUNT_DOWN = 0x01004;
    private static final int GET_SECURITY_CODE_ERROR = 0x001005; //获取验证码失败，发此消息重新获取，不用等到一分钟

    private Context mContext;
    private ILoginFragUI mLoginUi;

    private int mCountNumber = 0;

    private Timer mTimer;
    private String mName;  //用户称谓
    private int mSex;  //用户性别

    public enum BUTTON_TYPE {
        BTN_SECURITY_CODE, BTN_LOGIN,
    }



    public LoginFragPresenter(Context context, ILoginFragUI ui) {
        mContext = context;
        mLoginUi = ui;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_COUNT_DOWN:
                    int second = msg.getData().getInt("show_time");
                    mLoginUi.refreshTextView(second);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * #########################public 方法#########################
     */



    /**
     * 点击登录按钮
     */
    public void goToLogin() {
        String warning = getWarningString(mLoginUi.getInputMobileNumber(), mLoginUi.getInputSecurityCode(), BUTTON_TYPE.BTN_LOGIN);
        if (warning != null) {
            showDialog(DialogType.SINGLE, warning);
        } else {
            doLogin(mLoginUi.getInputSecurityCode(), mLoginUi.getInputMobileNumber());
        }
    }

    /**
     * 计时器开始60秒倒计时
     *
     * @param seconds
     */
    public void startTimer(int seconds) {
        mCountNumber = seconds;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (mCountNumber > 0) {
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putInt("show_time", mCountNumber);
                    msg.setData(b);
                    msg.what = START_COUNT_DOWN;
                    mHandler.sendMessage(msg);
                } else {
                    mHandler.sendEmptyMessage(STOP_COUNT_DOWN);
                }
                mCountNumber--;
            }
        };
        mTimer = new Timer();
        mTimer.schedule(timerTask, 0, 1000);
    }

    /**
     * 获取Timer对象
     *
     * @return
     */
    public Timer getTimer() {
        return mTimer;
    }

    /**
     * 停止计时器
     */
    public void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * #########################private 方法#########################
     */

    /**
     * 手机号11位简单判断
     *
     * @param number
     * @return
     */
    private boolean isMobileNumber(String number) {
        if (number.isEmpty() || number.length() != 11) {
            return false;
        }
        return true;
    }

    /**
     * 验证码6位简单判断
     *
     * @param code
     * @return
     */
    private boolean isCorrectSecurityCode(String code) {
        if (code.isEmpty() || code.length() != 6) {
            return false;
        }
        return true;
    }

    /**
     * 点击验证码、登录按钮时对输入内容进行判断
     */
    private String getWarningString(String number, String code, BUTTON_TYPE type) {
        String mobileNumber = (number != null) ? number.trim() : null;
        String securityCode = (code != null) ? code.trim() : null;
        switch (type) {
            case BTN_SECURITY_CODE: {
                if (mobileNumber == null || mobileNumber.isEmpty()) {
                    return mContext.getString(R.string.login_mobile_number_cannot_null);
                } else if (!isMobileNumber(mobileNumber)) {
                    return mContext.getString(R.string.login_input_correct_mobile_number);
                }
                break;
            }

            case BTN_LOGIN: {
                if (mobileNumber == null || mobileNumber.isEmpty()) {
                    return mContext.getString(R.string.login_mobile_number_cannot_null);
                } else if (!isMobileNumber(mobileNumber)) {
                    return mContext.getString(R.string.login_input_correct_mobile_number);
                } else if (securityCode == null || securityCode.isEmpty()) {
                    return mContext.getString(R.string.login_input_security_code);
                } else if (!isCorrectSecurityCode(securityCode)) {
                    return mContext.getString(R.string.login_security_code_error_format);
                }
                break;
            }
            default:
                return null;
        }
        return null;
    }

    /**
     * 弹出错误提示框
     *
     * @param type
     * @param content
     */
    public void showDialog(DialogType type, String content) {
        DialogExchangeModel.DialogExchangeModelBuilder builder = new DialogExchangeModel.DialogExchangeModelBuilder(type, "");
        builder.setDialogContext(content)
                .setBackAble(true)
                .setSingleText(mContext.getString(R.string.ok))
                .setSpaceAble(true);
        LFDialogOps.showDialogFragment(((Fragment) mLoginUi).getChildFragmentManager(), builder.create());
    }
    /**
     * ##############################发服务 [S]##############################
     */


    /**
     * 登录 服务回调接口
     *
     * @param verifyCode
     * @param mobileNum
     */
    private OnServiceListener<LoginResponse> mLoginServiceListener = new OnServiceListener<LoginResponse>() {
        @Override
        public void onServiceSuccess(LoginResponse response, String token) {
            if (null != response) {
                if (response.succeeded()) {
                    mLoginUi.loginSucceed(response);
                    //TIPS: 15/11/21 这里是用户在我们的体系登录成功后，需要登录第三方IM SDK
                     // ImUserUtils.login(response.getData(), null);
                } else {
                    mLoginUi.loginFailed(response.getMessage());
                }
            } else {
                mLoginUi.loginFailed(mContext.getString(R.string.login_failed));
            }
        }

        @Override
        public void onServiceFail(LFServiceError error, String token) {
            super.onServiceFail(error, token);
            mLoginUi.loginFailed(mContext.getString(R.string.net_loading_failed));
        }
    };

    /**
     * 登录
     *
     * @param verifyCode
     * @param mobileNum
     */
    private void doLogin(String verifyCode, String mobileNum) {
       /* final LoginRequest request = new LoginRequest();
        request.guestTelPhoneNum = mobileNum;
        request.validateCode = verifyCode;

        LFServiceReqModel.Builder builder = new LFServiceReqModel.Builder();
        builder.setRequest(request)
                .setResponseClass(LoginResponse.class)
                .setShowCoverProgress(true)
                .setServiceListener(mLoginServiceListener);
        LFServiceOps.loadData(builder.build(), mLoginUi);
    }
    private OnServiceListener<UploadGuestHeadPhotoResponse> uploadListener = new OnServiceListener<UploadGuestHeadPhotoResponse>() {
        @Override
        public void onServiceSuccess(UploadGuestHeadPhotoResponse response, String token) {
            if (response.succeeded()) {
                LFUserInfoOps.getUserInfo().setPhotoUrl(response.getData());
            }
        }

        @Override
        public void onServiceFail(LFServiceError error, String token) {
        }
    };*/
    }
}
