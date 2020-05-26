package com.esimtek.esimlocation.model;

import java.util.List;

public class LocationOrder {


    /**
     * code : 200
     * msg : Success
     * success : true
     * Data : {"Table":[{"RFIDLabel_RealTimePosition":"WaitImplantation1","StatusTable_UpDataTime":"2019-07-03T16:06:45.547","RFIDLabel_EslCode":"00A458"},{"RFIDLabel_RealTimePosition":"HotstampingLine1","StatusTable_UpDataTime":"2019-07-03T15:13:22.997","RFIDLabel_EslCode":"00A457"},{"RFIDLabel_RealTimePosition":"HotstampingLine1","StatusTable_UpDataTime":"2019-07-03T15:13:22.997","RFIDLabel_EslCode":"00A459"},{"RFIDLabel_RealTimePosition":"HotstampingLine1","StatusTable_UpDataTime":"2019-07-03T15:13:22.997","RFIDLabel_EslCode":"00A460"}]}
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
             * RFIDLabel_RealTimePosition : WaitImplantation1
             * StatusTable_UpDataTime : 2019-07-03T16:06:45.547
             * RFIDLabel_EslCode : 00A458
             */

            private String RFIDLabel_RealTimePosition;
            private String StatusTable_UpDataTime;
            private String RFIDLabel_EslCode;

            public String getRFIDLabel_RealTimePosition() {
                return RFIDLabel_RealTimePosition;
            }

            public void setRFIDLabel_RealTimePosition(String RFIDLabel_RealTimePosition) {
                this.RFIDLabel_RealTimePosition = RFIDLabel_RealTimePosition;
            }

            public String getStatusTable_UpDataTime() {
                return StatusTable_UpDataTime;
            }

            public void setStatusTable_UpDataTime(String StatusTable_UpDataTime) {
                this.StatusTable_UpDataTime = StatusTable_UpDataTime;
            }

            public String getRFIDLabel_EslCode() {
                return RFIDLabel_EslCode;
            }

            public void setRFIDLabel_EslCode(String RFIDLabel_EslCode) {
                this.RFIDLabel_EslCode = RFIDLabel_EslCode;
            }
        }
    }
}
