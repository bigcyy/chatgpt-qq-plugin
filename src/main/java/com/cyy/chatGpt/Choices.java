package com.cyy.chatGpt;

/**
 * @author CYY
 * @date 2023年02月11日 下午6:52
 * @description chatGpt官方api返回结果封装
 */
public class Choices {
    private String text;
    private int index;
    private String logprobs;
    private String finish_reason;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(String logprobs) {
        this.logprobs = logprobs;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }
}
