# EditSpinner
这是一个自定义控件,其功能是实现帐号的输入与选择。
简单来说就是一个EditText与Spinner的结合控件，当然其实现不是用的这两个控件，而是一个EditText与ImageView的组合。

使用方法：
1、在xml文件中添加该控件
    <com.mouse.cookie.editspinnerlib.EditSpinner
        android:id="@+id/es_mainactivity"
        android:background="@drawable/background_edittext"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </com.mouse.cookie.editspinnerlib.EditSpinner>
2、在Java文件中找到该控件，并添加内容
        EditSpinner mEditSpinner = (EditSpinner)findViewById(R.id.es_mainactivity);
        
        //setHint
        mEditSpinner.setHint("请输入帐号");
        
        //添加内容
        mEditSpinner.addAccount("464678986");
        mEditSpinner.addAccount("412345678");
        mEditSpinner.addAccount("4098765");

就是这样简单。

![image](https://github.com/cookiemouse/EditSpinner/tree/master/picture/edit_spinner_1)
![image](https://github.com/cookiemouse/EditSpinner/tree/master/picture/edit_spinner_2)
![image](https://github.com/cookiemouse/EditSpinner/tree/master/picture/edit_spinner_3)
