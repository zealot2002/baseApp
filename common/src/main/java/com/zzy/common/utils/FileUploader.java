package com.zzy.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.bean.Image;
import com.zzy.common.model.jsonParser.ImageParser;
import com.zzy.commonlib.core.ThreadPool;
import com.zzy.commonlib.http.HAdapter;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.NetDataInvalidException;
import com.zzy.commonlib.http.RequestCtx;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileUploader {
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

}
