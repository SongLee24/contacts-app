package com.songlee.activity;

import com.songlee.model.Contact;
import com.songlee.service.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private ListView contact_list=null;
    private EditText search=null;

    private List contacts=null;
    private Contact contact=null;
    private Service service=null;

    public static final int OPTION_DIALOG = 1;

    private PopupWindow popupWindow;
    private ListView menuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.title_main_activity);

        service = new Service(this);
        init();
        getContent();

        // 初始化popup window
        initPopupWindow();
    }


    // init
    private void init(){
        contact_list = (ListView)findViewById(R.id.contact_list);
        contact_list.setCacheColorHint(Color.TRANSPARENT);
        contact_list.setOnItemClickListener(new ViewItemListener());
        search = (EditText)findViewById(R.id.search);
        search.addTextChangedListener(new SearchTextChangedListener());
    }


    // show data in the ListView
    private void getContent(){
        List mylist = new ArrayList();
        String queryName = search.getText().toString();
        contacts = service.getByName(queryName); // get an contacts array
        if(contacts != null){
            for(int i=0; i<contacts.size(); i++){
                Contact contact = (Contact)contacts.get(i);
                // HashMap
                HashMap map = new HashMap();
                if(contact.getGender().equals("男")){
                    map.put("tv_image", R.drawable.icon_boy);
                }else{
                    map.put("tv_image", R.drawable.icon_girl);
                }
                map.put("tv_name", contact.getName());
                map.put("tv_phone", contact.getPhone());
                mylist.add(map);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(this, mylist,R.layout.my_list_item,
                new String[] {"tv_image","tv_name","tv_phone"},
                new int[] {R.id.user_image,R.id.item_name,R.id.item_phone});
        contact_list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_contact) { // 添加联系人
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.more) {  // 弹出菜单
            if(popupWindow.isShowing())
                popupWindow.dismiss();
            else
                popUp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
        getContent();
        super.onRestart();
    }


    protected Dialog onCreateDialog(int id){
        Dialog dialog;
        switch(id){
            case OPTION_DIALOG:
                dialog = createOptionDialog();
                break;
            default:
                dialog = null;
        }
        return dialog;
    }

    private Dialog createOptionDialog(){
        final Dialog optionDialog;
        View optionDialogView = null;
        LayoutInflater li = LayoutInflater.from(this);
        optionDialogView = li.inflate(R.layout.option_dialog, null);

        optionDialog = new AlertDialog.Builder(this).setView(optionDialogView).create();
        ImageButton ibCall = (ImageButton)optionDialogView.findViewById(R.id.dialog_call);
        ImageButton ibView = (ImageButton)optionDialogView.findViewById(R.id.dialog_view);
        ImageButton ibSms = (ImageButton)optionDialogView.findViewById(R.id.dialog_sms);

        ibCall.setOnClickListener(new ImageButtonListener());
        ibView.setOnClickListener(new ImageButtonListener());
        ibSms.setOnClickListener(new ImageButtonListener());
        return optionDialog;
    }


    //实例化PopupWindow创建菜单
    private void initPopupWindow(){
        View view = getLayoutInflater().inflate(R.layout.popup_window, null);
        menuListView = (ListView)view.findViewById(R.id.popup_list_view);
        popupWindow = new PopupWindow(view, 160, WindowManager.LayoutParams.WRAP_CONTENT);
        // 数据
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("menu_about", "关于");
        data.add(map);
        // 创建适配器
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.popup_list_item,
                new String[]{"menu_about"}, new int[]{R.id.menu_about});
        menuListView.setAdapter(adapter);
        // 添加Item点击响应
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent);
                        popupWindow.dismiss();
                        break;
                }
            }
        });
        // 在popupWindow以外的区域点击后关闭popupWindow
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }


    //显示PopupWindow菜单
    private void popUp(){
        //设置位置
        popupWindow.showAsDropDown(this.findViewById(R.id.more), 0, 2);
    }


    //**************** internal class as Listener ******************
    class SearchTextChangedListener implements TextWatcher{

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            getContent();
        }

    }
    class ViewItemListener implements OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // get the contact from the contacts array.
            contact = (Contact)contacts.get(position);
            showDialog(OPTION_DIALOG);

        }
    }
    class ImageButtonListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.dialog_call:
                    if(contact.getPhone().equals("")){
                        Toast.makeText(MainActivity.this, "没有手机号码", Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+contact.getPhone()));
                        intent.addCategory("android.intent.category.DEFAULT");
                        startActivity(intent);
                    }
                    dismissDialog(OPTION_DIALOG);
                    break;
                case R.id.dialog_view:
                    // Send an intent, Active the detailActivity
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    // Send the id of the selected contact by the intent
                    intent.putExtra("id", contact.getId());
                    startActivity(intent);
                    dismissDialog(OPTION_DIALOG);
                    break;
                case R.id.dialog_sms:
                    if(contact.getPhone().equals("")){
                        Toast.makeText(MainActivity.this, "没有手机号码", Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent1 = new Intent();
                        intent1.setAction(Intent.ACTION_SENDTO);
                        intent1.setData(Uri.parse("smsto:"+contact.getPhone()));
                        intent1.addCategory("android.intent.category.DEFAULT");
                        startActivity(intent1);
                    }
                    dismissDialog(OPTION_DIALOG);
                    break;
            }
        }
    }
}
