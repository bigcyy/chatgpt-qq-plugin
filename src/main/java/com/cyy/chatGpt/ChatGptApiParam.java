package com.cyy.chatGpt;

/**
 * @author CYY
 * @date 2023年02月11日 下午8:36
 * @description chat模式的api参数，聊天模式官方推荐参数
 */
public class ChatGptApiParam {
    private String model = "text-davinci-003";
    private String prompt;
    private int max_tokens = 2048;
    private double temperature = 0.9;
    private double top_p = 1;
    private double frequency_penalty = 0.0;
    private double presence_penalty = 0.6;
    private String[] stop = {" Human:"," AI:"};

    public ChatGptApiParam() {
    }

    public ChatGptApiParam(String prompt) {
        this.prompt = prompt;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTop_p() {
        return top_p;
    }

    public void setTop_p(double top_p) {
        this.top_p = top_p;
    }

    public double getFrequency_penalty() {
        return frequency_penalty;
    }

    public void setFrequency_penalty(double frequency_penalty) {
        this.frequency_penalty = frequency_penalty;
    }

    public double getPresence_penalty() {
        return presence_penalty;
    }

    public void setPresence_penalty(double presence_penalty) {
        this.presence_penalty = presence_penalty;
    }

    public String[] getStop() {
        return stop;
    }

    public void setStop(String[] stop) {
        this.stop = stop;
    }
}
