 package com.example.onlineadmissionsystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

 public class RegisterActivity extends AppCompatActivity {
    Button register;
    RadioGroup radioGroup;
    EditText fname,lname,email,password,mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        password=findViewById(R.id.passw);
        mobile=findViewById(R.id.mobile);
        radioGroup=findViewById(R.id.radiogrp);
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtFname=fname.getText().toString();
                String txtLname=lname.getText().toString();
                String txtEmail=email.getText().toString();
                String txtPassword=password.getText().toString();
                String txtMobile=mobile.getText().toString();
                if(TextUtils.isEmpty(txtFname)||TextUtils.isEmpty(txtLname)||TextUtils.isEmpty(txtEmail)
                        ||TextUtils.isEmpty(txtPassword)||TextUtils.isEmpty(txtMobile)){
                    Toast.makeText(RegisterActivity.this, "ALL FIELDS Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    int genderId=radioGroup.getCheckedRadioButtonId();
                    RadioButton selected_gender=radioGroup.findViewById(genderId);
                    if(selected_gender==null){
                        Toast.makeText(RegisterActivity.this, "Select Gender Please", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String selectGender=selected_gender.getText().toString();
                        //String z=txtName+txtEmail+txtPassword+txtMobile;
                        //Toast.makeText(RegisterActivity.this,z, Toast.LENGTH_SHORT).show();
                        registerNewAccount(txtFname,txtLname,txtEmail,txtPassword,txtMobile,selectGender);
                    }

                }

            }
        });
    }
    private void registerNewAccount(final String fname,final String lname, final String email, final String password, final String mobile, final String gender){
        final ProgressDialog progressDialog=new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Registering New Account");
        progressDialog.show();
        String URL="http://192.168.43.186/OnlineAdmissionSystemApp/register.php";
        StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully Registered")) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>parm=new HashMap<>();
                parm.put("fname",fname);
                parm.put("lname",lname);
                parm.put("email",email);
                parm.put("password",password);
                parm.put("mobile",mobile);
                parm.put("gender",gender);
                return parm;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(request);
    }
}
