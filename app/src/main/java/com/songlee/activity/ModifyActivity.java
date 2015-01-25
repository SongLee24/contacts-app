package com.songlee.activity;

import com.songlee.model.Contact;
import com.songlee.service.Service;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class ModifyActivity extends ActionBarActivity {

    private EditText number=null;    // declare all EditText views
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
    private Contact contact=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        contact = new Contact();
        service = new Service(this);
        init();  //init

        // add data source to Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,relationship);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter); // add the adapter to spinner

        // show the detail of the contact
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id == -1){
            finish();
        }else{
            contact = service.getById(id);

            number.setText(contact.getNumber());
            name.setText(contact.getName());
            phone.setText(contact.getPhone());
            email.setText(contact.getEmail());
            address.setText(contact.getAddress());
            remark.setText(contact.getRemark());
            if(contact.getGender().equals("男")){
                group.check(R.id.male);
            }else{
                group.check(R.id.female);
            }
            int pos = adapter.getPosition(contact.getRelationship());
            spinner.setSelection(pos);
        }

        // 标题栏添加“返回”菜单
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    // init
    private void init(){
        number = (EditText)findViewById(R.id.number);  // get all EditText views by id
        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        email = (EditText)findViewById(R.id.email);
        address = (EditText)findViewById(R.id.address);
        remark = (EditText)findViewById(R.id.remark);
        spinner = (Spinner)findViewById(R.id.spinner);  // get Spinner
        group = (RadioGroup)findViewById(R.id.group);  // get RadioGroup
        group.setOnCheckedChangeListener(new GroupListener());
        image = (ImageView)findViewById(R.id.image_view);
    }


    // get Input text
    private Contact getContent(){
        gender = (RadioButton)findViewById(group.getCheckedRadioButtonId()); //get the selected RadioButton
        Contact c = new Contact();
        c.setId(contact.getId());
        c.setNumber(number.getText().toString());
        c.setName(name.getText().toString());
        c.setPhone(phone.getText().toString());
        c.setEmail(email.getText().toString());
        c.setAddress(address.getText().toString());
        c.setRemark(remark.getText().toString());
        c.setGender(gender.getText().toString());
        c.setRelationship(spinner.getSelectedItem().toString());
        return c;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if(number.getText().toString().equals(""))
                Toast.makeText(this, "编号不能为空", Toast.LENGTH_LONG).show();
            else if(name.getText().toString().equals(""))
                Toast.makeText(this, "姓名不能为空", Toast.LENGTH_LONG).show();
            else if(phone.getText().toString().equals(""))
                Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
            else {
                boolean flag = service.update(getContent());
                if(flag)
                    Toast.makeText(ModifyActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ModifyActivity.this, "修改失败", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (id == android.R.id.home)  // 返回
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // *************** internal classes as listeners ******************
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
