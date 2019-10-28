package com.zzy.common.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.Image;
import com.zzy.common.model.jsonParser.ImageParser;
import com.zzy.commonlib.async.AsyncInterface;
import com.zzy.commonlib.core.ThreadPool;
import com.zzy.commonlib.http.HAdapter;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.NetDataInvalidException;
import com.zzy.commonlib.http.RequestCtx;
import com.zzy.commonlib.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileHandler {
    private List<Image> files;
    private List<Image> imgList = new ArrayList<>();
    class BaseHandler extends Handler {
        private HInterface.DataCallback callback;
        public BaseHandler(Looper looper,HInterface.DataCallback callback) {
            super(looper);
            this.callback = callback;
        }

        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            synchronized (imgList){
                try {
                    if(msg.what == HConstant.FAIL){
                        callback.requestCallback(HConstant.FAIL,"","");
                        return;
                    }
                    Image image = ImageParser.parse((String) msg.obj);
                    imgList.add(image);
                    if(imgList.size() == files.size()){
                        callback.requestCallback(HConstant.SUCCESS,imgList,"");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.requestCallback(HConstant.FAIL,e.toString(),"");
                }
            }
        }
    }
    class BaseTask implements Runnable {
        private String filePath;
        private Handler handler;

        public BaseTask(String filePath,Handler handler) {
            this.filePath = filePath;
            this.handler = handler;
        }
        public void run() {
            File file = new File(filePath);
            long size = file.length();
            OkHttpClient httpClient = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/octet-stream");//设置类型，类型为八位字节流
            RequestBody requestBody = RequestBody.create(mediaType, file);//把文件与类型放入请求体

            JSONObject json = new JSONObject();
            try {
                json.put("TOKEN", CommonUtils.getToken());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MultipartBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("json", json.toString())//添加表单数据
                    .addFormDataPart("file", file.getName(), requestBody)//文件名,请求体里的文件
                    .build();

            Request request = new Request.Builder()
                    .header("TOKEN", CommonUtils.getToken())//添加请求头的身份认证Token
                    .url(HttpConstants.FILE_SERVER_URL)
                    .post(multipartBody)
                    .build();
            Call call = httpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("TAG-失败：", e.toString() + "");
                    handler.sendMessage(handler.obtainMessage(HConstant.FAIL, e.toString()));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    Log.e("TAG-成功：", string + "");
                    handler.sendMessage(handler.obtainMessage(HConstant.SUCCESS, string));
                }
            });
        }
    }

    public void post(List<Image> files,final HInterface.DataCallback callback) {
        this.files = files;
        for(Image file:files){
            final BaseHandler handler = new BaseHandler(Looper.getMainLooper(),callback);
            final BaseTask taskThread = new BaseTask(file.getPath(), handler);
            ThreadPool.getInstance().getPool().execute(taskThread);
        }
    }
    public static void post(String path,final HInterface.DataCallback callback) throws JSONException {
        File file = new File(path);//我的文件路径，需要改你自己的
        long size = file.length();//文件长度
        OkHttpClient httpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");//设置类型，类型为八位字节流
        RequestBody requestBody = RequestBody.create(mediaType, file);//把文件与类型放入请求体

        JSONObject json = new JSONObject();
        json.put("TOKEN", CommonUtils.getToken());

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("json", json.toString())//添加表单数据
                .addFormDataPart("file", file.getName(), requestBody)//文件名,请求体里的文件
                .build();

        Request request = new Request.Builder()
                .header("TOKEN", CommonUtils.getToken())//添加请求头的身份认证Token
                .url(HttpConstants.FILE_SERVER_URL)
                .post(multipartBody)
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG-失败：", e.toString() + "");
                if(callback!=null){
                    callback.requestCallback(HConstant.FAIL,e.toString(),"");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("TAG-成功：", string + "");
                if(callback!=null){
                    callback.requestCallback(HConstant.SUCCESS,string,"");
                }
            }
        });
    }
    public static void postHeadPic(String path,final HInterface.DataCallback callback) throws JSONException {
        File file = new File(path);//我的文件路径，需要改你自己的
        long size = file.length();//文件长度
        OkHttpClient httpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");//设置类型，类型为八位字节流
        RequestBody requestBody = RequestBody.create(mediaType, file);//把文件与类型放入请求体

//        JSONObject json = new JSONObject();
//        json.put("TOKEN", CommonUtils.getToken());
//        json.put("imgType", 1);

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("TOKEN",CommonUtils.getToken())//添加表单数据
                .addFormDataPart("imgType","1")//添加表单数据
                .addFormDataPart("file", file.getName(), requestBody)//文件名,请求体里的文件
                .build();

        Request request = new Request.Builder()
//                .header()//添加请求头的身份认证Token
                .headers(new Headers.Builder()
                        .add("TOKEN", CommonUtils.getToken())
                        .add("imgType","1")
                        .build())
                .url(HttpConstants.HEAD_FILE_SERVER_URL)
                .post(multipartBody)
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG-失败：", e.toString() + "");
                if(callback!=null){
                    callback.requestCallback(HConstant.FAIL,e.toString(),"");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("TAG-成功：", string + "");
                if(callback!=null){
                    callback.requestCallback(HConstant.SUCCESS,string,"");
                }
            }
        });
    }


    class SaveFileHandler extends Handler {
        private HInterface.DataCallback callback;
        public SaveFileHandler(Looper looper,HInterface.DataCallback callback) {
            super(looper);
            this.callback = callback;
        }

        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
                try {
                    if(msg.what == HConstant.FAIL){
                        callback.requestCallback(HConstant.FAIL,"","");
                        return;
                    }
                    List<String> ret = (List<String>) msg.obj;
                    callback.requestCallback(HConstant.SUCCESS,ret,"");
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.requestCallback(HConstant.FAIL,e.toString(),"");
                }
        }
    }
    class SaveFileTask implements Runnable {
        private List<String> fileList;
        private Handler handler;

        public SaveFileTask(List<String> fileList,Handler handler) {
            this.fileList = fileList;
            this.handler = handler;
        }
        public void run() {
            try{
                List<String> ret = new ArrayList<>();
                for(String s:fileList){
                    Bitmap b = returnBitMap(s);
                    String path = saveImageToPhotos(AppUtils.getApp(),b);
                    ret.add(path);
                }
                handler.sendMessage(handler.obtainMessage(HConstant.SUCCESS,ret));
            }catch (Exception e){
                e.printStackTrace();
                handler.sendMessage(handler.obtainMessage(HConstant.FAIL, e.toString()));
            }
        }
    }


    public void savePicToLocal(List<String> fileList,final HInterface.DataCallback callback){
        final SaveFileHandler handler = new SaveFileHandler(Looper.getMainLooper(),callback);
        final SaveFileTask taskThread = new SaveFileTask(fileList, handler);
        ThreadPool.getInstance().getPool().execute(taskThread);
    }

    private static Bitmap returnBitMap(String url) {
        URL myFileUrl;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String saveImageToPhotos(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "dk");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        return file.getAbsolutePath();
    }
}
