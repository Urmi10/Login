package com.app.discussit;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Name extends AppCompatActivity {

    /* renamed from: a */
    EditText f42a;
    String user;

    /* renamed from: v */
    TextView f43v;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_name);
        this.f42a = (EditText) findViewById(R.id.editText11);
        ((Button) findViewById(R.id.button)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Name.this.user = Name.this.f42a.getText().toString();
                Intent intent = new Intent(Name.this, Topic.class);
                intent.putExtra("userName", Name.this.user);
                Name.this.startActivity(intent);
            }
        });
    }
}
