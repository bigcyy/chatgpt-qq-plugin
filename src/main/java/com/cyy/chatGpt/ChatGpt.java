package com.cyy.chatGpt;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author CYY
 * @date 2023年02月11日 下午5:49
 * @description
 */
public class ChatGpt {
    private MessageContext context;
    private String apiKey;
    private Long activeTime;
    private OkHttpClient client;

    private ChatGptApiParam chatGptApiParam;
    public ChatGpt(MessageContext context,String apiKey) {
        this.context = context;
        this.apiKey = apiKey;
        this.activeTime = System.currentTimeMillis();
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        this.chatGptApiParam = new ChatGptApiParam();
    }

    /**
     * 向openAi发送请求获取问题答案
     * @param question 问题
     * @return 答案，超时或者其他异常返回默认信息
     */
    public String doChat(String question){
        this.updateActiveTime();
        String prompt = this.context.getPreMessage() + "\nHuman: " + question;
        this.chatGptApiParam.setPrompt(prompt);
        Gson gson = new Gson();
        String json = gson.toJson(this.chatGptApiParam);
        System.out.println("********************************************");
        System.out.println("json:\n"+json);
        System.out.println("********************************************");
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization",this.apiKey)
                .post(requestBody).build();

        String result = "";
        try (Response response = this.client.newCall(request).execute()) {
            if(response.code() == 200){
                ChatGptApiResult chatGptApiResult = gson.fromJson(response.body().string(), ChatGptApiResult.class);
                for(Choices choices:chatGptApiResult.getChoices()){
                    result += choices.getText();
                }
                result = result.strip();
                context.push(question);
                context.push(result);
                System.out.println("********************************************");
                System.out.println("ans:\n"+gson.toJson(chatGptApiResult));
                System.out.println("********************************************");
            }else {
                result = "chatGpt出错了,错误码:"+response.code();
                //todo 频繁问答对应策略
            }
        } catch (SocketTimeoutException e){
            e.printStackTrace();
            result = "网络超时了，稍后再向我提问吧。";
        }catch (IOException e) {
            e.printStackTrace();
            result = "chatGpt出错了,try again......";
        }
        return result;
    }


    private void updateActiveTime(){
        setActiveTime(System.currentTimeMillis());  //更新活动时间
    }


    public MessageContext getContext() {
        return context;
    }

    public void setContext(MessageContext context) {
        this.context = context;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Long activeTime) {
        this.activeTime = activeTime;
    }

    public boolean isValid() {
        return System.currentTimeMillis() - activeTime > 3*60*1000;
    }
}
