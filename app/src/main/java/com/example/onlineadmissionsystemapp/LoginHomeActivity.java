package com.example.onlineadmissionsystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeLoginActivity extends AppCompatActivity {
    Button logout;
    TextView name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        Intent intent=getIntent();
        String extraname=intent.getStringExtra("name");
        String extraemail=intent.getStringExtra("email");
        name.setText(extraname);
        email.setText(extraemail);

        //final SharedPreferences sharedPreferences=getSharedPreferences("UserInfo",MODE_PRIVATE);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*      SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.prefLoginState),"loggedout");
                editor.apply();*/
                startActivity(new Intent(HomeLoginActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}
