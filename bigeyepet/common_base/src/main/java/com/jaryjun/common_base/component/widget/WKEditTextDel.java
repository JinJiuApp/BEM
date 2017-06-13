package com.jaryjun.common_base.component.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jaryjun.common_base.R;


/**
 * Created by Ted on 2015/3/3.
 * EditTextWithDeleteButton
 * ��ɾ��icon��EditText
 */
public class WKEditTextDel extends LinearLayout {
    protected EditText editText;
    protected ImageView clearTextButton;
    protected boolean isHaveDelBtn = true;

    public interface TextChangedListener extends TextWatcher {
    }

    private TextChangedListener editTextListener = null;

    private OnFocusChangeListener mOnFocusChangeListener = null;

    public void addTextChangedListener(TextChangedListener listener) {
        this.editTextListener = listener;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.mOnFocusChangeListener = onFocusChangeListener;
    }

    public WKEditTextDel(Context context) {
        super(context);
    }

    public WKEditTextDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public WKEditTextDel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WKEditTextDel);
        String hintText;
        int deleteButtonRes;
        try {
            hintText = a.getString(R.styleable.WKEditTextDel_hintText);
            deleteButtonRes = a.getResourceId(R.styleable.WKEditTextDel_deleteButtonRes, R.drawable.edit_text_clear_btn);
            isHaveDelBtn = a.getBoolean(R.styleable.WKEditTextDel_show_delete_btn, true);
        } finally {
            a.recycle();
        }
        editText = createEditText(context, hintText);
        clearTextButton = createImageButton(context, deleteButtonRes);
        this.addView(editText);
        this.addView(clearTextButton);

        editText.addTextChangedListener(mTextWatcher);
        editText.setOnFocusChangeListener(mEditOnFocusChangeListener);
        clearTextButton.setOnClickListener(mOnClearClickListener);
    }

    private OnFocusChangeListener mEditOnFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && editText.getText().toString().length() > 0) {
                if (isHaveDelBtn)
                    clearTextButton.setVisibility(View.VISIBLE);
            } else clearTextButton.setVisibility(View.INVISIBLE);
            if (null != mOnFocusChangeListener)
                mOnFocusChangeListener.onFocusChange(v, hasFocus);
        }
    };

    private OnClickListener mOnClearClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            editText.setText("");
            clearTextButton.setVisibility(INVISIBLE);
        }
    };

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (editTextListener != null)
                editTextListener.onTextChanged(s, start, before, count);

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (editTextListener != null)
                editTextListener.afterTextChanged(s);
            if (editText.getText().toString().length() > 0) {
                if (isHaveDelBtn) {
                    clearTextButton.setVisibility(View.VISIBLE);
                } else {
                    clearTextButton.setVisibility(View.INVISIBLE);
                }
            }else{
                if (isHaveDelBtn) {
                    clearTextButton.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (editTextListener != null)
                editTextListener.beforeTextChanged(s, start, count, after);
        }

    };

    private EditText createEditText(Context context, String hintText) {
        editText = (EditText) LayoutInflater.from(context).inflate(R.layout.base_view_edit_text, this,false);
        editText.setHint(hintText);
        return editText;
    }

    private ImageView createImageButton(Context context, int deleteButtonRes) {
        clearTextButton = new ImageView(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.CENTER_VERTICAL;
        clearTextButton.setLayoutParams(params);
        clearTextButton.setBackgroundResource(deleteButtonRes);
        clearTextButton.setVisibility(View.INVISIBLE);
        return clearTextButton;
    }

    @SuppressWarnings("unused")
    public void hideClearBtn() {
        if (null != clearTextButton) clearTextButton.setVisibility(GONE);
    }

    public EditText getEditText() {
        return editText;
    }
}
