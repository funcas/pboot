package com.funcas.pboot.common;


import com.funcas.pboot.common.enumeration.ValueEnum;

import java.io.Serializable;

/**
 * date:  2016/2/27 16:34
 *
 * @author funcas
 * @version 1.0
 */
public class ApiResult implements Serializable {

    private static final long serialVersionUID = 1439768882125600323L;
    private Object retCode;
    private String retMessage;
    private Object result;

    public ApiResult(){}

    private ApiResult(Builder builder) {
        this.retCode = builder.retCode;
        this.retMessage = builder.retMessage;
        this.result = builder.result;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Object retCode;
        private String retMessage;
        private Object result;

        public Builder retCode(Object retCode) {
            this.retCode = retCode;
            return this;
        }

        public Builder retMessage(String retMessage) {
            this.retMessage = retMessage;
            return this;
        }

        public Builder result(Object result) {
            this.result = result;
            return this;
        }

        public Builder apiResultEnum(ValueEnum apiResultEnum) {
            this.retCode = apiResultEnum.getValue();
            this.retMessage = apiResultEnum.getName();
            return this;
        }

        public ApiResult build() {
            return new ApiResult(this);
        }
    }

    public Object getRetCode() {
        return retCode;
    }

    public void setRetCode(Object retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "retCode='" + retCode + '\'' +
                ", retMessage='" + retMessage + '\'' +
                ", result=" + result +
                '}';
    }

}
