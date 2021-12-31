package ddwu.com.mobile.example.lbs.ma01_20180215;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    final static String TAG = "AddActivity";

    EditText etName;
    EditText etPlaceAddress;
    EditText etDate;
    ImageView ivPhoto;
    EditText etWrite;

    DiaryDBManager diaryDBManager;
    DatePickerDialog datePickerDialog;

    private final static int REQUEST_TAKE_THUMBNAIL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Calendar cal = Calendar.getInstance();

        etName = findViewById(R.id.et_name);
        etPlaceAddress = findViewById(R.id.et_place_address);
        etDate = findViewById(R.id.et_date);
        ivPhoto = findViewById(R.id.ivPhoto);
        etWrite = findViewById(R.id.et_write);

        etName.setText(getIntent().getStringExtra("name"));
        etPlaceAddress.setText(getIntent().getStringExtra("address"));
        etDate.setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));

        diaryDBManager = new DiaryDBManager(this);

        ivPhoto.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_THUMBNAIL);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_date:
                // DATE Picker가 처음 떴을 때, 오늘 날짜가 보이도록 설정.
                Calendar cal = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                datePickerDialog.show();
                break;
                
            case R.id.btn_ok:
                boolean result = diaryDBManager.addNewDiary(
                        new Diary(etName.getText().toString(), etPlaceAddress.getText().toString(),
                                etDate.getText().toString(), etWrite.getText().toString()));
                if(result) { //정상 수행 처리
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(this, "추가 실패!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    // Date Picker에서 선택한 날짜를 TextView에 설정
                    etDate = findViewById(R.id.et_date);
                    etDate.setText(String.format("%d-%d-%d", yy,mm+1,dd));
                }
            };

    protected void onDestroy() {
        super.onDestroy();
        //다이얼로그가 띄워져 있는 상태(showing)인 경우 dismiss() 호출
        if (datePickerDialog != null) {
            datePickerDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_THUMBNAIL && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extra.get("data");
            ivPhoto.setImageBitmap(imageBitmap);
        }
    }
}
