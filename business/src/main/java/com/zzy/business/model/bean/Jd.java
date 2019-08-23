package com.zzy.business.model.bean;

import java.io.Serializable;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Jd implements Serializable {
    private String companyName;
    private String from;
    private String jobName;
    private String address;
    private String headcount;
    private String education;
    private String salary;
    private String phone;
    private String contact;
    private String jobContent;
    private String jobRequirements;
    private String publishTime;

    public Jd() {}

    public Jd(String companyName, String from, String jobName, String address, String headcount,
              String education, String salary, String phone, String contact,
              String jobContent, String jobRequirements, String publishTime) {
        this.companyName = companyName;
        this.from = from;
        this.jobName = jobName;
        this.address = address;
        this.headcount = headcount;
        this.education = education;
        this.salary = salary;
        this.phone = phone;
        this.contact = contact;
        this.jobContent = jobContent;
        this.jobRequirements = jobRequirements;
        this.publishTime = publishTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadcount() {
        return headcount;
    }

    public void setHeadcount(String headcount) {
        this.headcount = headcount;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
