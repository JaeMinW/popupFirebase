package com.example.testorangapp.model;

public class UserTable {

    private String uuid;
    private String email;
    private String name;
    private String ph;
    private String pwd;
    private String birth;
    private String sex;

    private String createdDate;

    private boolean adminFlag;
    private boolean pushFlag;
    private boolean deletedFlag;

    public UserTable() {
        this.adminFlag = false;
        this.deletedFlag = false;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(boolean adminFlag) {
        this.adminFlag = adminFlag;
    }

    public boolean isPushFlag() {
        return pushFlag;
    }

    public void setPushFlag(boolean pushFlag) {
        this.pushFlag = pushFlag;
    }

    public boolean isDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(boolean deletedFlag) {
        this.deletedFlag = deletedFlag;
    }
}
