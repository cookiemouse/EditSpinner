package com.mouse.cookie.editspinnerlib;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cookie on 2015/12/26.
 */
public class EditSpinner extends RelativeLayout{

    private LayoutInflater mLayoutInflater;

    private Context mContext;
    private EditText mEditText;
    private ImageView mImageViewLeft, mImageViewRight;

    private ListView mListView;
    private ListAdapter mListAdapter;
    private List<String> mList;

    private boolean isOpen = false;

    private PopupWindow mPopupWindow;

    private OnDeletedListener listener;
    private OnEditSpinnerItemClickListener itemClickListener;
    private OnEditTextChangeListener onEditTextChangeListener;

    //构造函数
    public EditSpinner(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    //构造函数
    public EditSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
    }

    //构造函数
    public EditSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context);
    }

    //初始化视图，并设置相关事件
    private void initView(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mLayoutInflater.inflate(R.layout.editspinner_layout, this);

        mList = new ArrayList<>();

        mEditText = (EditText) findViewById(R.id.et_editspinner);
        mImageViewLeft = (ImageView)findViewById(R.id.iv_editspinner_left);
        mImageViewRight = (ImageView) findViewById(R.id.iv_editspinner_right);
        setDrawableRight(R.drawable.down_right);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != onEditTextChangeListener){
                    onEditTextChangeListener.onEditActionListener();
                }
            }
        });

        mImageViewRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
//                    关闭
                    setDrawableRight(R.drawable.down_right);
                    isOpen = false;
                } else {
//                    打开
                    showList(mImageViewLeft);
                    setDrawableRight(R.drawable.up_right);
                    isOpen = true;
                }
            }
        });
    }

    //设置hint
    public void setHint(String text) {
        mEditText.setHint(text);
    }

    //设置hint
    public void setHint(int resourcesId) {
        mEditText.setHint(getResources().getText(resourcesId));
    }

    //设置text
    public void setText(String text) {
        mEditText.setText(text);
    }

    //设置text
    public void setText(int resourcesId) {
        mEditText.setText(getResources().getText(resourcesId));
    }

    //获取文本
    public String getText() {
        return mEditText.getText().toString();
    }

    //向下拉列表中添加帐号数据
    public void addAccount(String account) {
        mList.add(account);
    }

    //设置右边的图标
    private void setDrawableRight(int resourcesId) {
        mImageViewRight.setImageResource(resourcesId);
    }

    //弹出popUpWindow
    private void showList(View view) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, null);

        //下拉列表
        mListAdapter = new ListAdapter(mContext, mList, listener);
        mListView = (ListView) contentView.findViewById(R.id.lv_editspinner);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = mList.get(position);
                mEditText.setText(content);
                mEditText.setSelection(content.length());
                itemClickListener.onEditSpinnerItemClickListener(position);
                mPopupWindow.dismiss();
            }
        });

        //PopupWindow
        int width = 2 * view.getWidth() + mEditText.getWidth();
        mPopupWindow = new PopupWindow(contentView, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_null));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isOpen = false;
                setDrawableRight(R.drawable.down_right);
            }
        });

        mPopupWindow.showAsDropDown(view, 0, 5);
    }

    //删除回调方法
    public interface OnDeletedListener{
        void onDeletedListener(int position);
    }

    //贴号点击事件
    public interface OnEditSpinnerItemClickListener{
        void onEditSpinnerItemClickListener(int position);
    }

    //设置删除回调监听
    public void setOnDeletedListener(OnDeletedListener listener){
        this.listener = listener;
    }

    //设置帐号点击事件
    public void setOnEditSpinnerItemClickListener(OnEditSpinnerItemClickListener listener){
        this.itemClickListener = listener;
    }

    //内容改变监听器
    public interface OnEditTextChangeListener{
        void onEditActionListener();
    }

    public void setOnEditTextChangeListener(OnEditTextChangeListener listener){
        this.onEditTextChangeListener = listener;
    }
}
