package com.example.jayasaripalli.repos.model;

public class Repository {
    private int id;
    private String nodeId;
    private String name;
    private String fullName;
    private Owner owner;

    public int getId(){
        return id;
    }
    public void setId(int input){
        this.id = input;
    }
    public String getNodeId(){
        return nodeId;
    }
    public void setNodeId(String input){
        this.nodeId = input;
    }
    public String getName(){
        return name;
    }
    public void setName(String input){
        this.name = input;
    }
    public String getFullName(){
        return fullName;
    }
    public void setFullName(String input){
        this.fullName = input;
    }
    public Owner getOwner(){
        return owner;
    }
    public void setOwner(Owner input){
        this.owner = input;
    }

public class Owner{
        private String login;
        private int id;
        private String nodeId;
        private String avatarUrl;
        private String gravatarId;

        public String getLogin(){
            return login;
        }
        public void setLogin(String input){
            this.login = input;
        }
        public int getId(){
            return id;
        }
        public void setId(int input){
            this.id = input;
        }
        public String getNodeId(){
            return nodeId;
        }
        public void setNodeId(String input){
            this.nodeId = input;
        }
        public String getAvatarUrl(){
            return avatarUrl;
        }
        public void setAvatarUrl(String input){
            this.avatarUrl = input;
        }
        public String getGravatarId(){
            return gravatarId;
        }
        public void setGravatarId(String input){
            this.gravatarId = input;
        }
    }

}
