package ddwu.com.mobile.example.lbs.ma01_20180215;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    final static String TAG = "MainActivity";
    final static int PERMISSION_REQ_CODE = 400;

    /*UI*/
    private EditText etKeyword;
    private GoogleMap mGoogleMap;

    private MarkerOptions markerOptions;
    private MarkerOptions options;
    LatLng latLng;

    /*DATA*/
    private PlacesClient placesClient; //장소 상세 정보

    private LatLngResultReceiver latLngResultReceiver;

    ArrayList<String> addressList = null;
    DiaryDBManager diaryDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etKeyword = findViewById(R.id.etKeyword);

        mapLoad();

        Places.initialize(getApplicationContext(), getString(R.string.api_key));
        placesClient = Places.createClient(this);

        addressList = new ArrayList();
        diaryDBManager = new DiaryDBManager(this);

        latLngResultReceiver = new LatLngResultReceiver(new Handler());
        mark();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressList.clear();
        addressList.addAll(diaryDBManager.getAllAddress());
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:

                if(etKeyword.getText().toString().equals("카페")){
                    searchStart(PlaceType.CAFE);
                }

                break;

            case R.id.btnList: //글 목록 버튼 클릭
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                break;
        }
    }

    
    /*입력된 유형의 주변 정보를 검색*/
    private void searchStart(String type) {
        new NRPlaces.Builder().listener(placesListener)
                .key(getResources().getString(R.string.api_key))
                .latlng(Double.valueOf(getResources().getString(R.string.init_lat)),
                        Double.valueOf(getResources().getString(R.string.init_lng)))
                .radius(100)
                .type(type)
                .build()
                .execute();
    }


    /*Place ID 의 장소에 대한 세부정보 획득*/
    private void getPlaceDetail(String placeId) {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.PHONE_NUMBER, Place.Field.ADDRESS);

        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();

        placesClient.fetchPlace(request).addOnSuccessListener(
                new OnSuccessListener<FetchPlaceResponse>() {
                    @Override
                    public void onSuccess(FetchPlaceResponse response) {
                        Place place = response.getPlace();
                        callDetailActivity(place); //activity_detail 호출
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(e instanceof ApiException){
                            ApiException apiException = (ApiException) e;
                            int statusCode = apiException.getStatusCode();
                            Log.d(TAG, "Place not found: " + statusCode + " " + e.getMessage());
                        }
                    }
                }
        );
    }


    private void callDetailActivity(Place place) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("name",place.getName());
                        intent.putExtra("phone",place.getPhoneNumber());
                        intent.putExtra("address",place.getAddress());

        startActivity(intent);
    }


    PlacesListener placesListener = new PlacesListener() {

        @Override
        public void onPlacesSuccess(final List<noman.googleplaces.Place> places) {
            Log.d(TAG, "Adding Markers");

            //마커 추가
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(noman.googleplaces.Place place:places){
                        markerOptions.title(place.getName());
                        markerOptions.position(new LatLng(place.getLatitude(), place.getLongitude()));
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_RED
                        ));
                        Marker newMarker = mGoogleMap.addMarker(markerOptions);
                        newMarker.setTag(place.getPlaceId()); //장소 아이디 보관
                        Log.d(TAG, place.getName() + " : " + place.getPlaceId());
                    }
                }
            });
        }

        @Override
        public void onPlacesFailure(PlacesException e) {}

        @Override
        public void onPlacesStart() {}

        @Override
        public void onPlacesFinished() {}
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        markerOptions = new MarkerOptions();
        options = new MarkerOptions();
        Log.d(TAG, "Map ready");

        if(checkPermission()){
            mGoogleMap.setMyLocationEnabled(true);
        }

        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mGoogleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                Toast.makeText(MainActivity.this,
                        String.format("현재위치: (%f, %f)", location.getLatitude(), location.getLongitude()),
                        Toast.LENGTH_SHORT).show();
            }
        });

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() { //마커 윈도우 클릭 시
            @Override
            public void onInfoWindowClick(Marker marker) {
                String placeId = marker.getTag().toString();
                getPlaceDetail(placeId);

            }
        });
    }


    /*구글맵을 멤버변수로 로딩*/
    private void mapLoad() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);      // 매개변수 this: MainActivity 가 OnMapReadyCallback 을 구현하므로
    }



    /* 필요 permission 요청 */
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQ_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 퍼미션을 획득하였을 경우 맵 로딩 실행
                mapLoad();
            } else {
                // 퍼미션 미획득 시 액티비티 종료
                Toast.makeText(this, "앱 실행을 위해 권한 허용이 필요함", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //db에 저장된 장소 파란색 마커 표시 - 지오코더 이용
    public void mark() {
        addressList = diaryDBManager.getAllAddress();

        Iterator iter = addressList.iterator();

        while(iter.hasNext()){//다음값이 있는지 체크
            String address = (String)iter.next();
            startLatLngService(address);
        }
    }

    /* 주소 → 위도/경도 변환 IntentService 실행 */
    private void startLatLngService(String address) {
        Intent intent = new Intent(this, FetchLatLngIntentService.class);
        intent.putExtra(Constants.RECEIVER, latLngResultReceiver);
        intent.putExtra(Constants.ADDRESS_DATA_EXTRA, address);
        startService(intent);
    }

    /* 주소 → 위도/경도 변환 ResultReceiver */
    class LatLngResultReceiver extends ResultReceiver {
        public LatLngResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            String lat;
            String lng;
            ArrayList<LatLng> latLngList = null;

            if (resultCode == Constants.SUCCESS_RESULT) {
                if (resultData == null) return;
                latLngList = (ArrayList<LatLng>) resultData.getSerializable(Constants.RESULT_DATA_KEY);
                if (latLngList == null) {
                    lat = Double.toString(37.607398);
                    lng = Double.toString(127.042650);
                } else {
                    LatLng latlng = latLngList.get(0);
                    lat = String.valueOf(latlng.latitude);
                    lng = String.valueOf(latlng.longitude);
                }

                Log.d(TAG, "lat: " + lat + "lng " + lng);
                options.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                options.icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_BLUE
                ));
                mGoogleMap.addMarker(options);

            } else {
                Log.d(TAG, getString(R.string.no_address_found));
            }
        }
    }
}
