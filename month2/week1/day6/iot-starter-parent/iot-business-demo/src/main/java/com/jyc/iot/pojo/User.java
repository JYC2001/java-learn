package com.jyc.iot.pojo;

//数据库表对应实体（下划线转驼峰已在yml配置，直接写）
public class User {
    private Long id;
    private String userName;
    private String password;
    private String phone;

    //无参构造+有参构造+get/set方法（必须有，MyBatis反射用）
    public User() {}
    public User(Long id, String userName, String password, String phone){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }

    //快速生成get/set：IDEA快捷键Alt+Inset 选择Getters and Setters
    public Long getId(){ return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserName() { return userName;}
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword(){ return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone(){ return phone; }
    public void setPhone(String phone){ this.phone = phone; }
}
