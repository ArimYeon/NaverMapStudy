package com.arimyeon.testmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.UiThread
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource

class NaverMapViewActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    // 위치를 반환하는 구현체
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naver_map_view)

        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        // NaverMap 객체 얻어오기
        // MapView의 getMapAsync() 메서드로 OnMapReadyCallback을 등록하면 비동기로 NaverMap 객체를 얻을 수 있음
        // NaverMap 객체가 준비되면 onMapReady() 콜백 메서드가 호출됨
        mapView.getMapAsync(this)
        // FusedLocationSource는 런타임 권한 처리를 위해 액티비티 또는 프래그먼트를 필요로 함
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        // 지도 유형
        naverMap.mapType = NaverMap.MapType.Basic
        // 레이어 그룹
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRAFFIC, true)
        // 실내 지도
        naverMap.isIndoorEnabled = true
        // 위치 추적 모드 활성화
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        // 위치 변경 이벤트
        // 위치 추적 모드가 활성화되고 사용자의 위치가 변경되면 onLocationChange() 콜백 메서드가 호출되며, 파라미터로 사용자의 위치가 전달됨
        naverMap.addOnLocationChangeListener { location ->
            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
                Toast.LENGTH_SHORT).show()
        }
        // UI와 관련된 설정을 담당하는 클래스 인스턴스 호출
        val uiSettings = naverMap.uiSettings
        uiSettings.isCompassEnabled = true  // 나침반 활성화
        uiSettings.isLocationButtonEnabled = true   // 현위치 버튼 활성화
        uiSettings.isIndoorLevelPickerEnabled = true    // 실내지도 층 피커 활성화
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}