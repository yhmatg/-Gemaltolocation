package com.esimtek.gemaltolocation.model;

public class TokenBean {
    private DataBean Data;
    private int code;
    private String msg;
    private boolean success;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean data) {
        Data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        private String ExpireTime;
        private String SignToken;
        private int StaffId;

        public String getExpireTime() {
            return ExpireTime;
        }

        public void setExpireTime(String expireTime) {
            ExpireTime = expireTime;
        }

        public String getSignToken() {
            return SignToken;
        }

        public void setSignToken(String signToken) {
            SignToken = signToken;
        }

        public int getStaffId() {
            return StaffId;
        }

        public void setStaffId(int staffId) {
            StaffId = staffId;
        }
    }
}