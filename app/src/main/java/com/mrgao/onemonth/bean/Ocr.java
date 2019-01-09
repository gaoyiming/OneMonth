package com.mrgao.onemonth.bean;

import java.util.List;

/**
 * 文 件 名: Ocr
 * 创 建 人: mr.gao
 * 创建日期: 2019/1/9 13:25
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
public class Ocr {

    /**
     * log_id : 8383984203819456777
     * direction : 0
     * words_result_num : 4
     * words_result : [{"words":"太阳光的色彩是比较明亮、柔和的,给人充满生机和希"},{"words":"望的感觉。在进行色彩配的时候,选择的彩对比不要过"},{"words":"于强烈,尽量选用较为明亮色彩,能够给人比较积极上"},{"words":"的感受"}]
     */

    private long log_id;
    private int direction;
    private int words_result_num;
    private List<WordsResultBean> words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * words : 太阳光的色彩是比较明亮、柔和的,给人充满生机和希
         */

        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
