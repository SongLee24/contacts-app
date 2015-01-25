package com.songlee.activity;

import com.songlee.model.Contact;
import com.songlee.service.Service;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class DetailActivity extends ActionBarActivity {

    private EditText number=null;    // declare all EditText views
    private EditText name=null;
    private EditText phone=null;
    private EditText email=null;
    private EditText address=null;
    private EditText remark=null;
    private EditText gender=null;
    private EditText relationship=null;
    private ImageView image=null;
    private Button call=null;
    private Button sms=null;

    private Contact contact=null;
    private Service service=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        contact = new Contact();
        init();
        // get the Intent to receive the Id of the contact, return -1 while Id doesn't exist
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id == -1){
            finish();
        }else{
            service = new Service(this);
            contact = service.getById(id);

            number.setText(contact.getNumber());
            name.setText(contact.getName());
            phone.setText(contact.getPhone());
            email.setText(contact.getEmail());
            address.setText(contact.getAddress());
            remark.setText(contact.getRemark());
            gender.setText(contact.getGender());
            if(contact.getGender().equals("男")){
                image.setImageResource(R.drawable.icon_boy);
            }else{
                image.setImageResource(R.drawable.icon_girl);
            }
            relationship.setText(contact.getRelationship());
        }

        // 标题栏添加“返回”菜单
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void init(){
        number = (EditText)findViewById(R.id.contact_number);  // get all EditText views by Id
        name = (EditText)findViewById(R.id.contact_name);
        phone = (EditText)findViewById(R.id.contact_phone);
        email = (EditText)findViewById(R.id.contact_email);
        address = (EditText)findViewById(R.id.contact_address);
        remark = (EditText)findViewById(R.id.contact_remark);
        gender = (EditText)findViewById(R.id.contact_gender);
        relationship = (EditText)findViewById(R.id.contact_relationship);
        image = (ImageView)findViewById(R.id.image_button);
        call = (Button)findViewById(R.id.call);
        call.setOnClickListener(new ButtonCallListener());
        sms = (Button)findViewById(R.id.sms);
        sms.setOnClickListener(new ButtonSmsListener());
    }


    // create hint dialog
    private void dialog(){
        AlertDialog.Builder builder = new Builder(DetailActivity.this);
        builder.setMessage("确定删除吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                service.delete(contact.getId());
                finish();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_modify) {
            Intent intent = new Intent(DetailActivity.this, ModifyActivity.class);
            intent.putExtra("id", contact.getId());
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_delete) {
            dialog();
            return true;
        }
        if (id == android.R.id.home)  // 返回
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id == -1){
            finish();
        }else{
            service = new Service(this);
            contact = service.getById(id);

            number.setText(contact.getNumber());
            name.setText(contact.getName());
            phone.setText(contact.getPhone());
            email.setText(contact.getEmail());
            address.setText(contact.getAddress());
            remark.setText(contact.getRemark());
            gender.setText(contact.getGender());
            if(contact.getGender().equals("男")){
                image.setImageResource(R.drawable.icon_boy);
            }else{
                image.setImageResource(R.drawable.icon_girl);
            }
            relationship.setText(contact.getRelationship());
        }
        super.onRestart();
    }


    //**************** internal classes as Listener ********************
    class ButtonCallListener implements android.view.View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+contact.getPhone()));
            intent.addCategory("android.intent.category.DEFAULT");
            startActivity(intent);
        }
    }
    class ButtonSmsListener implements android.view.View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:"+contact.getPhone()));
            intent.addCategory("android.intent.category.DEFAULT");
            startActivity(intent);
        }
    }
}
