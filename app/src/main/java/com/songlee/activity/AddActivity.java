package com.songlee.activity;

import com.songlee.model.Contact;
import com.songlee.service.Service;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends ActionBarActivity {

    private EditText number=null;    // declare EditText
    private EditText name=null;
    private EditText phone=null;
    private EditText email=null;
    private EditText address=null;
    private EditText remark=null;
    private Spinner spinner=null;
    private String[] relationship = {"同学","同事","家人","朋友"};
    private RadioButton gender=null;
    private RadioGroup group=null;
    private ImageView image=null;

    private Service service=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        service = new Service(this);
        init();  // init

        // add data source to Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,relationship);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter); // add the adapter to spinner

        // 标题栏添加“返回”菜单
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    // init - get views by Id
    private void init(){
        number = (EditText)findViewById(R.id.contact_number);  // get all EditText views
        name = (EditText)findViewById(R.id.contact_name);
        phone = (EditText)findViewById(R.id.contact_phone);
        email = (EditText)findViewById(R.id.contact_email);
        address = (EditText)findViewById(R.id.contact_address);
        remark = (EditText)findViewById(R.id.contact_remark);
        spinner = (Spinner)findViewById(R.id.spinner);  // get Spinner view by Id
        group = (RadioGroup)findViewById(R.id.group);  // get RadioGroup view by Id
        group.setOnCheckedChangeListener(new GroupListener());
        image = (ImageView)findViewById(R.id.image_view);
    }


    // get Input text
    private Contact getContent(){
        gender = (RadioButton)findViewById(group.getCheckedRadioButtonId()); // get the selected RadioButton
        Contact contact = new Contact();
        contact.setNumber(number.getText().toString());
        contact.setName(name.getText().toString());
        contact.setPhone(phone.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setAddress(address.getText().toString());
        contact.setRemark(remark.getText().toString());
        contact.setGender(gender.getText().toString());
        contact.setRelationship(spinner.getSelectedItem().toString());
        return contact;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {  // 保存
            if(number.getText().toString().equals(""))
                Toast.makeText(this, "编号不能为空", Toast.LENGTH_LONG).show();
            else if(name.getText().toString().equals(""))
                Toast.makeText(this, "姓名不能为空", Toast.LENGTH_LONG).show();
            else if(phone.getText().toString().equals(""))
                Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
            else {
                boolean flag = service.save(getContent());
                if(flag)
                    Toast.makeText(this, "联系人添加成功", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "联系人添加失败", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (id == android.R.id.home)  // 返回
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    //**************** internal classes as Listener ********************
    class GroupListener implements OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(group.getCheckedRadioButtonId() == R.id.male)
                image.setImageResource(R.drawable.icon_boy);
            else
                image.setImageResource(R.drawable.icon_girl);
        }
    }
}
