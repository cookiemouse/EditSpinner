package com.mouse.cookie.editspinnerlib;

import android.content.Context;
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
public class EditSpinner extends RelativeLayout {

    private LayoutInflater mLayoutInflater;

    private Context mContext;
    private EditText mEditText;
    private ImageView mImageViewLeft, mImageViewRight;

    private ListView mListView;
    private ListAdapter mListAdapter;
    private List<String> mList;

    private boolean isOpen = false;

    private PopupWindow mPopupWindow;

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
        mListAdapter = new ListAdapter(mContext, mList);
        mListView = (ListView) contentView.findViewById(R.id.lv_editspinner);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = mList.get(position);
                mEditText.setText(content);
                mEditText.setSelection(content.length());
                mPopupWindow.dismiss();
            }
        });

        //PopupWindow
        int width = 2 * view.getWidth() + mEditText.getWidth();
        mPopupWindow = new PopupWindow(contentView, width, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_null));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isOpen = false;
                setDrawableRight(R.drawable.down_right);
            }
        });

        mPopupWindow.showAsDropDown(view);
    }
}
