package com.example.lym.searchlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @Description：简略搜索框
 * @author：Bux on 2017/12/25 15:14
 * @email: 471025316@qq.com
 */

public class SearchLayout extends LinearLayout {

    private int defaultSize = 32;//DIP
    private TextView tvTag;
    private EditText etInput;
    private ImageView ivCancel;
    private ImageView ivTag;


    private int tagResId = 0;// R.drawable.ic_search;

    private String tagStr = "";
    private int tagSize = 16;//sp
    private int tagColor = R.color.color_black;

    private int inputSize = 16;//sp
    private int inputColor = R.color.color_black;
    private int inputHintSize = 13;
    private String inputHint = "";
    private int inputHintColor = 0;

    private int cancelResId = R.drawable.cancel;

    private OnSearchListener mListener;


    private InputMethodManager iManager;


    public SearchLayout(Context context) {
        this(context, null);
    }

    public SearchLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
        setGravity(Gravity.CENTER_VERTICAL);
        defaultSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defaultSize, getResources().getDisplayMetrics());
        LayoutInflater.from(context).inflate(R.layout.search_layout, this, true);
        // addView(view);
        tvTag = findViewById(R.id.tv_tag);
        etInput = findViewById(R.id.et_input);
        ivCancel = findViewById(R.id.iv_cancel);
        ivTag = findViewById(R.id.iv_tag);

        tvTag.setVisibility(GONE);
        ivTag.setVisibility(GONE);
        ivCancel.setVisibility(GONE);

        initAttr(context, attrs);
        initListener();
        iManager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchLayout, 0, 0);
        tagResId = array.getResourceId(R.styleable.SearchLayout_tag_src, tagResId);

        tagStr = array.getString(R.styleable.SearchLayout_tag_text);
        tagSize = array.getDimensionPixelSize(R.styleable.SearchLayout_tag_Size, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, tagSize, getResources().getDisplayMetrics()));
        tagColor = array.getResourceId(R.styleable.SearchLayout_tag_Color, tagColor);

        inputSize = array.getDimensionPixelSize(R.styleable.SearchLayout_input_Size, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, inputSize, getResources().getDisplayMetrics()));
        inputColor = array.getResourceId(R.styleable.SearchLayout_input_Color, inputColor);
        inputHintSize = array.getDimensionPixelSize(R.styleable.SearchLayout_input_Hint_Size, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, inputHintSize, getResources().getDisplayMetrics()));
        inputHint = array.getString(R.styleable.SearchLayout_input_Hint);
        inputHintColor = array.getResourceId(R.styleable.SearchLayout_input_Hint_color, inputHintColor);

        cancelResId = array.getResourceId(R.styleable.SearchLayout_cancel_resId, cancelResId);


        setLeftImg(tagResId);

        tvTag.setTextSize(TypedValue.COMPLEX_UNIT_PX, tagSize);
        tvTag.setTextColor(ContextCompat.getColor(context, tagColor));
        setLeftText(tagStr);

        if (inputHintColor > 0) {
            etInput.setHintTextColor(ContextCompat.getColor(context, inputHintColor));
        }
        etInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, inputSize);
        etInput.setTextColor(ContextCompat.getColor(context, inputColor));
        etInput.setText("");
        setHint(inputHint);

        setCancel(cancelResId);

        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(defaultSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void initListener() {
        ivCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etInput.setText("");
                if (mListener != null) {
                    mListener.onCancel();
                }
            }
        });

        etInput.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String searchStr = etInput.getText().toString();
                    mListener.onSearchKeyLister(searchStr);
                    return true;
                }
                return false;
            }
        });

/*        etInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int length = etInput.getText().toString().trim().length();
                if (hasFocus && length > 0) {
                    ivCancel.setVisibility(View.VISIBLE);
                } else {
                    ivCancel.setVisibility(View.GONE);
                }


            }
        });*/

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    ivCancel.setVisibility(View.VISIBLE);
                } else {
                    ivCancel.setVisibility(View.GONE);
                }
            }
        });
    }


    public void setListener(OnSearchListener listener) {
        mListener = listener;
    }


    public void showSoftInput() {
        etInput.setFocusable(true);
        etInput.requestFocus();
        iManager.showSoftInput(etInput, InputMethodManager.SHOW_FORCED);
    }


    public void hideSoftInput() {
        iManager.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
    }

    public String getSearchText() {
        return etInput.getText().toString().trim();
    }

    public void setSearchText(String text) {
        if (text != null) {
            etInput.setText(text);
        }
    }

    /**
     * 设置查询框左边图片
     *
     * @param resId
     */
    public SearchLayout setLeftImg(int resId) {
        if (resId > 0) {
            ivTag.setVisibility(VISIBLE);
            tvTag.setVisibility(GONE);
            ivTag.setImageResource(resId);
        }
        return this;
    }

    /**
     * 设置查询框左边文字
     *
     * @param tag
     */
    public SearchLayout setLeftText(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            tvTag.setVisibility(VISIBLE);
            ivTag.setVisibility(GONE);
            tvTag.setText(tag);
        }
        return this;
    }


    private void setCancel(int resId) {
        if (cancelResId > 0) {
            ivCancel.setImageResource(resId);
        }
    }

    /**
     * 设置Hint
     *
     * @param text
     * @return
     */
    public SearchLayout setHint(String text) {
        if (!TextUtils.isEmpty(text)) {
            SpannableString ss = new SpannableString(text);
            AbsoluteSizeSpan ass = new AbsoluteSizeSpan(inputHintSize);
            ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            etInput.setHint(new SpannedString(ss));
        }
        return this;
    }

    /**
     * 查询框左边文字控件
     *
     * @return
     */
    public TextView getTvDesc() {
        return tvTag;
    }

    /**
     * 查询 输入框控件
     *
     * @return
     */
    public EditText getEtInput() {
        return etInput;
    }

    /**
     * 清除按钮控件
     *
     * @return
     */
    public ImageView getIvCancel() {
        return ivCancel;
    }

    /**
     * 查询框左边图片控件
     *
     * @return
     */
    public ImageView getIvTag() {
        return ivTag;
    }

    public interface OnSearchListener {
        /**
         * 清除按钮
         */
        void onCancel();

        /**
         * 键盘回车键
         */
        void onSearchKeyLister(String input);
    }

}
