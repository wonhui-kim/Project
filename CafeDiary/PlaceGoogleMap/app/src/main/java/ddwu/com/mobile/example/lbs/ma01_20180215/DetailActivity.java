package ddwu.com.mobile.example.lbs.ma01_20180215;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    final int REQ_CODE = 100;

    String placeName;
    String placeAddress;

    EditText etName;
    EditText etPhone;
    EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);

        placeName = getIntent().getStringExtra("name");
        placeAddress = getIntent().getStringExtra("address");

        etName.setText(placeName);
        etAddress.setText(placeAddress);
        etPhone.setText(getIntent().getStringExtra("phone"));

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWrite:
                Intent intent = new Intent(this, AddActivity.class);
                intent.putExtra("name", placeName);
                intent.putExtra("address", placeAddress);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.btnClose:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {  // AddActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
//                    String food = data.getStringExtra("food");
//                    Toast.makeText(this, food + " 추가 완료", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "추가 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "추가 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
