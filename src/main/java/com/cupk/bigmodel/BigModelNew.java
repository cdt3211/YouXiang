package com.cupk.bigmodel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BigModelNew extends WebSocketListener {
    public static final String hostUrl = "https://spark-api.xf-yun.com/v3.1/chat";
    public static final String appid = "";
    public static final String apiSecret = "";
    public static final String apiKey = "";


    public static List<RoleContent> historyList = new ArrayList<>(); // 对话历史存储集合

    public static String totalAnswer = ""; // 大模型的答案汇总

    public static String NewQuestion = "";

    public static final Gson gson = new Gson();

    private String userId;
    private CountDownLatch latch;

    public BigModelNew(String userId, CountDownLatch latch) {
        this.userId = userId;
        this.latch = latch;
    }

    public static void main(String[] args) throws Exception {
//        String content = "自动获取的文章内容"; // 模拟自动获取文章内容
//        NewQuestion = "这些是不同帖子的内容，根据它们生成一份总结：" + content;
//
//        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
//        Request request = new Request.Builder().url(url).build();
//        totalAnswer = "";
//        WebSocket webSocket = client.newWebSocket(request, new BigModelNew("1", false));
//        latch.await(10, TimeUnit.SECONDS);
    }


    class MyThread extends Thread {
        private WebSocket webSocket;

        public MyThread(WebSocket webSocket) {
            this.webSocket = webSocket;
        }

        public void run() {
            try {
                JSONObject requestJson = new JSONObject();

                JSONObject header = new JSONObject();
                header.put("app_id", appid);
                header.put("uid", UUID.randomUUID().toString().substring(0, 10));

                JSONObject parameter = new JSONObject();
                JSONObject chat = new JSONObject();
                chat.put("domain", "generalv2");
                chat.put("temperature", 0.5);
                chat.put("max_tokens", 8192);
                parameter.put("chat", chat);

                JSONObject payload = new JSONObject();
                JSONObject message = new JSONObject();
                JSONArray text = new JSONArray();

                if (historyList.size() > 0) {
                    for (RoleContent tempRoleContent : historyList) {
                        text.add(JSON.toJSON(tempRoleContent));
                    }
                }

                RoleContent roleContent = new RoleContent();
                roleContent.role = "user";
                roleContent.content = NewQuestion;
                text.add(JSON.toJSON(roleContent));
                historyList.add(roleContent);

                message.put("text", text);
                payload.put("message", message);

                requestJson.put("header", header);
                requestJson.put("parameter", parameter);
                requestJson.put("payload", payload);

                webSocket.send(requestJson.toString());
                while (true) {
                    Thread.sleep(200);
                    if (latch.getCount() == 0) {
                        break;
                    }
                }
                webSocket.close(1000, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        System.out.print("拾光：");
        MyThread myThread = new MyThread(webSocket);
        myThread.start();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        JsonParse myJsonParse = gson.fromJson(text, JsonParse.class);
        if (myJsonParse.header.code != 0) {
            System.out.println("发生错误，错误码为：" + myJsonParse.header.code);
            System.out.println("本次请求的sid为：" + myJsonParse.header.sid);
            webSocket.close(1000, "");
        }
        List<Text> textList = myJsonParse.payload.choices.text;
        for (Text temp : textList) {
            System.out.print(temp.content);
            totalAnswer = totalAnswer + temp.content;
        }
        if (myJsonParse.header.status == 2) {
            System.out.println();
            System.out.println("*************************************************************************************");
            latch.countDown();
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        try {
            if (null != response) {
                int code = response.code();
                System.out.println("onFailure code: " + code);
                System.out.println("onFailure body: " + response.body().string());
                if (101 != code) {
                    System.out.println("connection failed");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());

        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";

        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        String sha = Base64.getEncoder().encodeToString(hexDigits);

        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);

        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder()
                .addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8)))
                .addQueryParameter("date", date)
                .addQueryParameter("host", url.getHost())
                .build();

        return httpUrl.toString();
    }

    class JsonParse {
        Header header;
        Payload payload;
    }

    class Header {
        int code;
        int status;
        String sid;
    }

    class Payload {
        Choices choices;
    }

    class Choices {
        List<Text> text;
    }

    class Text {
        String role;
        String content;
    }

    class RoleContent {
        String role;
        String content;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    // 生成总结
    public String getSummary(String content) throws Exception {
        String prompt ="下面是不同用户对于同一个产品所发表的帖子，请根据他们的内容生成一份总结，" +
                "要求给出这件产品的用户喜好程度、用户对产品的评价、用户对产品的建议等信息。";
        NewQuestion = prompt + content;

        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();
        totalAnswer = "";
        WebSocket webSocket = client.newWebSocket(request, new BigModelNew("1", latch)); // 创建一个新的 WebSocket 连接


        latch.await(30, TimeUnit.SECONDS);
        return totalAnswer;
    }

    //生成文章摘要
    public String getAbstract(String article) throws Exception {
        String prompt = "下面是一篇文章的内容，请根据这篇文章的内容生成一份100字的摘要。";
        NewQuestion = prompt + article;

        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();
        totalAnswer = "";
        WebSocket webSocket = client.newWebSocket(request, new BigModelNew("1", latch)); // 创建一个新的 WebSocket 连接

        latch.await(30, TimeUnit.SECONDS);
        return totalAnswer;
    }
}
