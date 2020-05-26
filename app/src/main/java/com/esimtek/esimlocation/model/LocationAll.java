package com.esimtek.esimlocation.model;

public class LocationAll {

    /**
     * code : 200
     * msg : Success
     * success : true
     * Data : {"ReaderLocation_HotgoldLine1":"150","ReaderLocation_ImplantationLine2":"150","ReaderLocation_WarehouseEntry":"100","ReaderLocation_PackagingLine":"222","ReaderLocation_HotgoldLine3":"130","ReaderLocation_ImplantationLine3":"123","ReaderLocation_WarehouseOut":"200","ReaderLocation_HotgoldLine2":"120","ReaderLocation_ImplantationLine1":"140"}
     */

    private int code;
    private String msg;
    private boolean success;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String HotstampingLine1;
        private String HotstampingLine2;
        private String WaitImplantation1;
        private String WaitImplantation2;
        private String ImplantationLine1;
        private String ImplantationLine2;
        private String WaitPackaging;

        public String getHotstampingLine1() {
            return HotstampingLine1;
        }

        public String getHotstampingLine2() {
            return HotstampingLine2;
        }

        public String getWaitImplantation1() {
            return WaitImplantation1;
        }

        public String getWaitImplantation2() {
            return WaitImplantation2;
        }

        public String getImplantationLine1() {
            return ImplantationLine1;
        }

        public String getImplantationLine2() {
            return ImplantationLine2;
        }

        public String getWaitPackaging() {
            return WaitPackaging;
        }
    }
}
