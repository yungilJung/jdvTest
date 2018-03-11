package com.example.gili.jdvtest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gili.comutil.CommonUtils;
import com.example.gili.comutil.DBHelper;

public class WriteDBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_db);

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText txtTilte = CommonUtils.getEditText(WriteDBActivity.this, R.id.txtTitle);
                EditText txtContent = CommonUtils.getEditText(WriteDBActivity.this, R.id.txtContent);
                if(!CommonUtils.checkEmptyValidation(txtTilte, "제목을 입력해주세요")){return;};
                if(!CommonUtils.checkEmptyValidation(txtContent, "내용을 입력해주세요")){return;};

                DBHelper helper = new DBHelper(WriteDBActivity.this);
              /*  try (SQLiteDatabase db = helper.getWritableDatabase()) {
                    db.execSQL("insert into tb_memo(title, content) values(?,?)", new String[]{txtTilte.getText().toString(), txtContent.getText().toString()});
                    db.close();
                }*/

                // 화면 전환...
                Intent intent = new Intent(WriteDBActivity.this, ReadDBActivity.class);
                startActivity(intent);
            }

        });
    }
}
