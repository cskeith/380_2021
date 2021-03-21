package ouhk.comps380f;

import java.io.Serializable;
import java.net.InetAddress;

public class PageVisit implements Serializable {

    private long enteredTimestamp;
    private Long leftTimestamp;
    private String request;
    private InetAddress ipAddress;

    public long getEnteredTimestamp() {
        return enteredTimestamp;
    }

    public void setEnteredTimestamp(long enteredTimestamp) {
        this.enteredTimestamp = enteredTimestamp;
    }

    public Long getLeftTimestamp() {
        return leftTimestamp;
    }

    public void setLeftTimestamp(Long leftTimestamp) {
        this.leftTimestamp = leftTimestamp;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTimeString() {
        if (this.leftTimestamp == null) {
            return "";
        }
        long timeInterval = this.leftTimestamp - this.enteredTimestamp;
        if (timeInterval < 1_000) {
            return "less than one second";
        }
        if (timeInterval < 60_000) {
            return (timeInterval / 1_000) + " seconds";
        }
        return "about " + (timeInterval / 60_000) + " minutes";
    }

}
