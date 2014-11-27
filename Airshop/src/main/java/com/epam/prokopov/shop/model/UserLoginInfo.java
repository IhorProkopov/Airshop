package com.epam.prokopov.shop.model;

public class UserLoginInfo {

    private String login;
    private String pass;
    private UserRoles role;
    private long lastVisitTime;
    private int tries;
    private long timeOfTheEnd;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public long getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(long lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public long getTimeOfTheEnd() {
        return timeOfTheEnd;
    }

    public void setTimeOfTheEnd(long timeOfTheEnd) {
        this.timeOfTheEnd = timeOfTheEnd;
    }

    public String getPass() {
        return pass;

    }

    public void setPass(String pass) {
        this.pass = pass;
    }



}
