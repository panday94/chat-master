package com.master.chat.llm.locallm.chatchat;

import cn.hutool.json.JSONUtil;
import okhttp3.*;
import okhttp3.sse.EventSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

import javax.annotation.Nullable;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class t1 {

    public static void main(String[] args) {


        OkHttpClient client =new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(50,TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.MINUTES)
                .build();

        EventSource.Factory factory = EventSources.createFactory(client);
        // 请求体
        HashMap<String, Object>map = new HashMap<>();
        ArrayList<Object> objects = new ArrayList<>();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("role", "user");
        hashMap.put("content", "哈喽，你好");
        objects.add(hashMap);

        HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
        hashMap1.put("role", "system");
        hashMap1.put("content", "请在你输出的时候都带上“喵喵喵”三个字，放在开头。");
        objects.add(hashMap1);

        map.put("messages",objects);
//        map.put("history", Arrays.asList());
        map.put("model","glm4");
        map.put("stream",true);
        map.put("temperature",0.3);
        map.put("presence_penalty",1.2);
        map.put("top_p",0.7);
        map.put("max_tokens",8196);
        String json = JSONUtil.toJsonStr(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        // 请求对象
        Request request =new Request.Builder()
//                .url("http://101.126.30.60:8000/v1/chat/completions")
                .url("http://43.140.221.161:8000/v1/chat/completions")
                .post(body)
                .build();

        EventSourceListener listener = new EventSourceListener() {
            public void onOpen(EventSource eventSource, Response response) {
                System.out.println("onOpen");
            }

            public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String data) {
                System.out.println(eventSource);
                System.out.println(data);
            }

            public void onClosed(EventSource eventSource) {
            }

            public void onFailure(EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
            }
        };
        EventSource eventSource = factory.newEventSource(request, listener);

    }
}
