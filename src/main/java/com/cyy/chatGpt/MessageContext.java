package com.cyy.chatGpt;

/**
 * @author CYY
 * @date 2023年02月11日 下午7:42
 * @description 信息上下文，需要给chatGpt提供上文才能实现连续对话
 */
public class MessageContext {
    private int start = 0;
    private int end = 0;
    /**
     * 最大支持存储21-1条信息
     */
    private final String[] turns = new String[21];
    /**
     * 实际可存maxSize-1条信息
     */
    private int maxSize;


    public MessageContext(int maxSize) {
        this.maxSize = maxSize;
    }

    public int size(){
        return start <= end ? (end-start) : (maxSize - start + end);
    }
    public boolean isFull(){
        return (end + 1) % maxSize == start;
    }
    public boolean isEmpty(){
        return start == end;
    }

    public void push(String question){
        if(isFull()){   //如果队列已经满了则将队首出队再加入新元素
            pop();
        }
        turns[end] = question;
        end = (end+1)%maxSize;
    }

    public String pop(){
        if(isEmpty()){
            return null;
        }
        String ans = turns[start];
        start = (start + 1) % maxSize;
        return ans;
    }

    public String getPreMessage(){
        StringBuilder ans = new StringBuilder();
        for(int i = start;i != end;i = (i+1)%maxSize){
            ans.append(" ").append(turns[i]);
        }
        return ans.toString();
    }

}
