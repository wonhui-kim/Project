package ddwu.com.mobile.example.lbs.ma01_20180215;

import java.io.Serializable;

public class Diary implements Serializable {
    long _id;
    String name;
    String address;
    String date;
    String write;

    public Diary(String name, String address, String date, String write){
        this.name = name;
        this.address = address;
        this.date = date;
        this.write = write;
    }

    public Diary(long _id, String name, String address, String date, String write){
        this._id = _id;
        this.name = name;
        this.address = address;
        this.date = date;
        this.write = write;
    }

    public long get_id() { return _id; }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(date);
        sb.append("/\t");
        sb.append(name);
        sb.append("(");
        sb.append(address);
        sb.append(")\n");
        sb.append(write);
        return sb.toString();
    }
}
