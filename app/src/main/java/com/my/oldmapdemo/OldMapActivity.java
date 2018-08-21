package com.my.oldmapdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TileOverlay;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.UrlTileProvider;

import java.net.URL;

/**
 * create by my
 */
public class OldMapActivity extends Activity implements View.OnClickListener{

	private AMap aMap;
	private MapView mapView;
	final String url = "http://a.tile.openstreetmap.org/%d/%d/%d.png";
	final String url2 = "http://123.57.32.182:3030/arcgisc/1932_oldBJ/%d/%d/%d/.png";
    private TileOverlay oldmaptileOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oldmap_activity);

		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {

		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
        CheckBox oldmap = (CheckBox) findViewById(R.id.old_map);
        oldmap.setOnClickListener(this);
	}

	private void setUpMap() {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.902385,116.395996),17));
		aMap.setMapTextZIndex(2);
        oldmaptileOverlay = aMap.addTileOverlay(new TileOverlayOptions().tileProvider(new UrlTileProvider(256, 256) {
			
			@Override
			public URL getTileUrl(int x, int y, int zoom) {
				try {
					return new URL(String.format(url2, zoom, x, y));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		})
				.diskCacheEnabled(true)
        		.diskCacheDir("/storage/emulated/0/amap/cache")
        		.diskCacheSize(100000)
        		.memoryCacheEnabled(true)
				.zIndex(1)
				.memCacheSize(100000));


	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.old_map:
                oldmaptileOverlay.setVisible(((CheckBox) view).isChecked());
                break;
        }
    }
}
