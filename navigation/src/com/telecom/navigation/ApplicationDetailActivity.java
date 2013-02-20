package com.telecom.navigation;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.telecom.receiver.DownloadCompleteReceiver;

public class ApplicationDetailActivity extends Activity {

    private DownloadManager manager;
    private DownloadCompleteReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_detail_layout);
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void onBtnDownloadClick(View view) {

        // 创建下载请求
        DownloadManager.Request down = new DownloadManager.Request(
                Uri.parse("http://s1.bdstatic.com/r/www/img/i-1.0.0.png"));
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        down.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置下载后文件存放的位置
        down.setDestinationInExternalFilesDir(this, null, "123.png");
        // 将下载请求放入队列
        manager.enqueue(down);
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }
}