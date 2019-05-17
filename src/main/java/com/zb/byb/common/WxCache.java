package com.zb.byb.common;

public class WxCache {

    private static WxCache instance = null;

    private AccessToken accessToken;

    private Ticket ticket;

    private WxCache() {

    }

    public static WxCache getInstance() {
        if (null == instance) {
            synchronized (WxCache.class) {
                if (null == instance)
                    instance = new WxCache();
            }
        }
        return instance;
    }

    public synchronized AccessToken getAccessToken() {
        return accessToken;
    }

    public synchronized void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public synchronized Ticket getTicket() {
        return ticket;
    }

    public synchronized void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
