package ddwu.com.mobile.example.lbs.ma01_20180215;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    final int UPDATE_CODE = 200;

    ListView listView;
    ArrayAdapter adapter;
    ArrayList<Diary> diaryList = null;
    DiaryDBManager diaryDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        diaryList = new ArrayList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, diaryList);
        listView.setAdapter(adapter);

        diaryDBManager = new DiaryDBManager(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Diary diary = diaryList.get(position);
                Intent intent = new Intent(ListActivity.this, UpdateActivity.class);
                intent.putExtra("diary", diary);
                startActivityForResult(intent, UPDATE_CODE);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("삭제 확인")
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(diaryDBManager.removeDiary(diaryList.get(pos).get_id())) {
                                    Toast.makeText(ListActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                    diaryList.clear();
                                    diaryList.addAll(diaryDBManager.getAllDiary());
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(ListActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CODE) {  // AddActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
//                    String food = data.getStringExtra("food");
//                    Toast.makeText(this, food + " 추가 완료", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        diaryList.clear();
        diaryList.addAll(diaryDBManager.getAllDiary());
        adapter.notifyDataSetChanged();
    }
}
