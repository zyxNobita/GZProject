package com.hr.nipuream.gz.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.controller.other.activity.MainActivity;
import com.hr.nipuream.gz.controller.other.bean.UpdateBean;
import org.apache.http.client.ClientProtocolException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  1.更新app版本，
 *  2.上报用户手机信息的
 * @author Nipuream
 *
 */
@SuppressWarnings("unused")
public class UpdateVersionServer extends Service{

	/**
	 * 通知View
	 */
	//	private RemoteViews views;
	private NotificationManager manager;
	//下载过程中点击回到主界面
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;
	private int downloadCount = 0;
	private int resultCode = -1;

	//上报次数
	private int reportNum = 0;

	public  static final int UPDATE_NOTIFICATION_ID = 0x45;
	public static final String UPDATE_APP_SUCCESSFUL="com.dss.app.hrxt.update_newVersion_app";
	public static final String NEW_VERSION_APP_INFORMATION = "com.dss.app.hrxt.newVersion_app";
	public static final int DOWNLOAD_SUCCESSFUL= 1;
	public static final int DOWNLOAD_FAIL=2;

	public static final String UPDATE_SAVENAME = "GZ.apk";


	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(msg.what == DOWNLOAD_SUCCESSFUL){

					//					manager.notify(UPDATE_NOTIFICATION_ID, n);
					manager.cancel(UPDATE_NOTIFICATION_ID);
					// stopService
					stopService(updateIntent);
					UpdateVersionServer.this.stopSelf();

					Uri uri = Uri.fromFile(new File(
							Environment.getExternalStorageDirectory(),
							UPDATE_SAVENAME));
					Intent installIntent = new Intent(Intent.ACTION_VIEW);
					installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
					startActivity(installIntent);

					//					updatePendingIntent = PendingIntent.getActivity(UpdateVersionServer.this, 0, installIntent, 0);
					//					n.defaults = Notification.DEFAULT_SOUND;//铃声提醒
					//					n.setLatestEventInfo(UpdateVersionServer.this, "华瑞车联", "下载完成,点击安装。", updatePendingIntent);


				}else if(msg.what == DOWNLOAD_FAIL){
//					n.setLatestEventInfo(UpdateVersionServer.this, "华瑞车联", "下载失败！", updatePendingIntent);
					manager.notify(UPDATE_NOTIFICATION_ID, createNotification(getString(R.string.download_error),-1));
					stopService(updateIntent);
					UpdateVersionServer.this.stopSelf();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//create Notification Manager
		manager=(NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
	}



	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			UpdateBean appInfo = (UpdateBean) intent.getSerializableExtra(NEW_VERSION_APP_INFORMATION);
			if(appInfo != null){
				String appLoad = appInfo.getUploadFile();
				initNotification(appInfo.getVersionId());
				downFile(appInfo.getUploadFile());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public IBinder onBind(Intent intent) {
		return null;
	}


	private Notification n;

	public void initNotification(String appVersion){
		try {

			updateIntent  = new Intent(this, MainActivity.class);
			updatePendingIntent = PendingIntent.getActivity(this,0,updateIntent,0);

			Notification.Builder builder = new Notification.Builder(this)
					.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
					.setAutoCancel(true)
					.setContentTitle(getString(R.string.app_name))
//					.setContentText("0%")
					.setContentIntent(updatePendingIntent)
					.setSmallIcon(R.mipmap.ic_launcher)
					.setWhen(System.currentTimeMillis())
					.setTicker(getString(R.string.start_download))
					.setProgress(100,0,false)
					.setOngoing(true);
			n = builder.getNotification();

//			n = new Notification();
//			updateIntent  = new Intent(this, InitialActivity.class);
//			updatePendingIntent = PendingIntent.getActivity(this,0,updateIntent,0);
//
//			n.icon = R.drawable.launcher_icon;
//			//			n.contentView = views;
//			n.tickerText="华瑞车联开始下载！";
//			n.setLatestEventInfo(HrxtApplication.getInstance().getApplicationContext(), "华瑞车联", "0%", updatePendingIntent);
//			n.when = System.currentTimeMillis();

			//set stay in notification bar always
			//			n.flags = Notification.FLAG_NO_CLEAR;
			//set forground service   After android 4.0 need startForeground(1,n);
			//			n.flags = Notification.FLAG_FOREGROUND_SERVICE;
			//			startForeground(UPDATE_NOTIFICATION_ID, n);

			manager.notify(UPDATE_NOTIFICATION_ID, n);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private Notification createNotification(String title ,int progress){
		Notification.Builder builder = new Notification.Builder(this)
				.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
				.setAutoCancel(true)
				.setContentTitle(title)
//				.setContentText(contentText)
				.setContentIntent(updatePendingIntent)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setWhen(System.currentTimeMillis())
				.setOngoing(true);

		if(progress != -1){
			builder.setProgress(100,progress,false);
		}


		return builder.getNotification();
	}

	public void downFile(final String path) {
		//	pBar.show();
		new Thread() {
			@Override
			public void run() {
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setConnectTimeout(5000);
					int max = conn.getContentLength();
					//				pBar.setMax(conn.getContentLength());
					InputStream is = conn.getInputStream();
					File file = new File(
							Environment.getExternalStorageDirectory(),
							UPDATE_SAVENAME);
					FileOutputStream fos = new FileOutputStream(file);
					BufferedInputStream bis = new BufferedInputStream(is);
					byte[] buffer = new byte[1024];
					int len;
					int total = 0;
					while ((len = bis.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
						total += len;
						//					Message msg = handler.obtainMessage();
						//					msg.getData().putInt("step", total);
						//					handler.sendMessage(msg);
						//						views.setProgressBar(R.id.download_notification_progress, conn.getContentLength(), total, false);
						if((downloadCount == 0)||(int) (total*100/max)-2>downloadCount){
							downloadCount += 2;
//							n.setLatestEventInfo(UpdateVersionServer.this, "正在下载", (total*100/max)+"%", updatePendingIntent);
//							createNotification("正在下载",(total*100/max)+"%");
							manager.notify(UPDATE_NOTIFICATION_ID, createNotification(getString(R.string.downloading),(total*100/max)));

						}
					}
					fos.flush();

					fos.close();
					bis.close();
					is.close();

					//下载完成
					mHandler.obtainMessage(DOWNLOAD_SUCCESSFUL).sendToTarget();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					mHandler.obtainMessage(DOWNLOAD_FAIL).sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
					mHandler.obtainMessage(DOWNLOAD_FAIL).sendToTarget();
				}
			}

		}.start();
	}

}
