package com.demit.mehraan.Model;

public class MessageModel {
    String id,name,token,lastmsg,lastmsgtime,lastmessagetime;

    public MessageModel(String id, String name, String token, String lastmsg, String lastmsgtime, String lastmessagetime) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.lastmsg = lastmsg;
        this.lastmsgtime = lastmsgtime;
        this.lastmessagetime = lastmessagetime;
    }

    public MessageModel() {
    }

    public String getLastmessagetime() {
        return lastmessagetime;
    }

    public void setLastmessagetime(String lastmessagetime) {
        this.lastmessagetime = lastmessagetime;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public String getLastmsgtime() {
        return lastmsgtime;
    }

    public void setLastmsgtime(String lastmsgtime) {
        this.lastmsgtime = lastmsgtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
