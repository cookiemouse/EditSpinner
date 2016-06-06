package com.mouse.cookie.editspinnerlib;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cookie on 2015/12/26.
 */
public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater mLayoutInflater;
    private EditSpinner.OnDeletedListener listener;

    public ListAdapter(Context context, List<String> mList, EditSpinner.OnDeletedListener listener) {
        this.mList = mList;
        this.mContext = context;
        this.listener = listener;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = mLayoutInflater.inflate(R.layout.adapter_layout, null);
        }

        TextView mTextViewAccount = (TextView)convertView.findViewById(R.id.tv_adapterlayout_account);
        ImageView mImageViewDelete = (ImageView)convertView.findViewById(R.id.iv_adapterlayout_delete);
        mTextViewAccount.setText(mList.get(position));

        mImageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(position);
            }
        });

        return convertView;
    }

    private void showDeleteDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(android.R.drawable.ic_lock_idle_alarm);
        builder.setTitle("警告");
        builder.setMessage("删除已保存的帐号");
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mList.remove(position);
                if (null != listener){
                    listener.onDeletedListener(position);
                }
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("保留", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
