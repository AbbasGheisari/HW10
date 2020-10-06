package com.test.domains;


public class UserInfo {

    private String userName;
    private String nationalCoe;
    private Role role;

    public UserInfo(){
    }

    public UserInfo(String userName, String nationalCoe, Role role) {
        this.userName=userName;
        this.nationalCoe=nationalCoe;
        this.role=role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNationalCoe() {
        return nationalCoe;
    }

    public void setNationalCoe(String nationalCoe) {
        this.nationalCoe =nationalCoe ;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName=userName;
    }

}
