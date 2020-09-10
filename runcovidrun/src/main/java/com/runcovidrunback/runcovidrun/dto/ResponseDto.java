package com.runcovidrunback.runcovidrun.dto;

public class ResponseDto {

    private String sms;

    public ResponseDto(String sms) {
        this.sms = sms;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }
}
