package qrmi.adapter.samples.model;

import java.io.Serializable;
import java.util.Date;

public class Stock implements Serializable {
    private static final long serialVersionUID = 3132195541682985722L;

    private String id;
    private Date ipoDate;
    private Date lastUpdate;
    private Double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getIpoDate() {
        return ipoDate;
    }

    public void setIpoDate(Date ipoDate) {
        this.ipoDate = ipoDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
