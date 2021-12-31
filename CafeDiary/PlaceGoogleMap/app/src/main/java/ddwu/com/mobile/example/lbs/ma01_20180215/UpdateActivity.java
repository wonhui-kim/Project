package ddwu.com.mobile.example.lbs.ma01_20180215;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    Diary diary;

    EditText etName;
    EditText etPlaceAddress;
    EditText etDate;
    EditText etWrite;

    DiaryDBManager diaryDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        diary = (Diary) getIntent().getSerializableExtra("diary");

        etName = findViewById(R.id.et_name);
        etPlaceAddress = findViewById(R.id.et_place_address);
        etDate = findViewById(R.id.et_date);
        etWrite = findViewById(R.id.et_write);

        etName.setText(diary.getName());
        etPlaceAddress.setText(diary.getAddress());
        etDate.setText(diary.getDate());
        etWrite.setText(diary.getWrite());

        diaryDBManager = new DiaryDBManager(this);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                diary.setName(etName.getText().toString());
                diary.setAddress(etPlaceAddress.getText().toString());
                diary.setDate(etDate.getText().toString());
                diary.setWrite(etWrite.getText().toString());

                if(diaryDBManager.modifyDiary(diary)) {
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                } else {
                    setResult(RESULT_CANCELED);
                }
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();
    }
}
