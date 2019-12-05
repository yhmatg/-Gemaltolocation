package com.esimtek.gemaltolocation.model;

import java.util.List;

public class LocationCode {

    /**
     * code : 200
     * msg : OK
     * success : true
     * Data : {"Table":[{"RFIDLabel_EslCode":"00A266","RFIDLabel_RealTimePosition":"仓库","RFIDLabel_RealTimePosition_Time":"2019-01-11T09:03:27"},{"RFIDLabel_EslCode":"00A266","RFIDLabel_RealTimePosition":"烫印1","RFIDLabel_RealTimePosition_Time":"2019-01-11T09:03:27"},{"RFIDLabel_EslCode":"00A266","RFIDLabel_RealTimePosition":"植入1","RFIDLabel_RealTimePosition_Time":"2019-01-11T09:03:27"},{"RFIDLabel_EslCode":"00A266","RFIDLabel_RealTimePosition":"包装","RFIDLabel_RealTimePosition_Time":"2019-01-11T09:03:27"}]}
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
        private List<TableBean> Table;

        public List<TableBean> getTable() {
            return Table;
        }

        public void setTable(List<TableBean> Table) {
            this.Table = Table;
        }

        public static class TableBean {
            /**
             * RFIDLabel_EslCode : 00A266
             * RFIDLabel_RealTimePosition : 仓库
             * RFIDLabel_RealTimePosition_Time : 2019-01-11T09:03:27
             */

            private String RFIDLabel_EslCode;
            private String RFIDLabel_RealTimePosition;
            private String RFIDLabel_RealTimePosition_Time;

            public String getRFIDLabel_EslCode() {
                return RFIDLabel_EslCode;
            }

            public void setRFIDLabel_EslCode(String RFIDLabel_EslCode) {
                this.RFIDLabel_EslCode = RFIDLabel_EslCode;
            }

            public String getRFIDLabel_RealTimePosition() {
                return RFIDLabel_RealTimePosition;
            }

            public void setRFIDLabel_RealTimePosition(String RFIDLabel_RealTimePosition) {
                this.RFIDLabel_RealTimePosition = RFIDLabel_RealTimePosition;
            }

            public String getRFIDLabel_RealTimePosition_Time() {
                return RFIDLabel_RealTimePosition_Time;
            }

            public void setRFIDLabel_RealTimePosition_Time(String RFIDLabel_RealTimePosition_Time) {
                this.RFIDLabel_RealTimePosition_Time = RFIDLabel_RealTimePosition_Time;
            }
        }
    }
}
