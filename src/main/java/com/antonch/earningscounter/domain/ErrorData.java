package com.antonch.earningscounter.domain;

/**
 * Объект содержащий информацию об ошибке
 * при обращении к сервису (например: исчерпан
 * месячный лимит обращений к сервису)
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
public class ErrorData {

    private int code;

    private String type;

    private String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return new StringBuilder("[ErrorData: ")
                .append("code=").append(code)
                .append(", type=").append(type)
                .append(", info=").append(info)
                .append("]").toString();
    }

}
