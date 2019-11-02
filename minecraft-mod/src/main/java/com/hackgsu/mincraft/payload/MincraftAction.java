package com.hackgsu.mincraft.payload;

public class MincraftAction
{
    private String action;
    private String data;
    private String username;

    public MincraftAction()
    {
    }

    public MincraftAction(String action, String data, String username)
    {
        this.action = action;
        this.data = data;
        this.username = username;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
