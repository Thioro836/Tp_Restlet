package org.inria.restlet.mta.internals;

import java.security.Timestamp;
import java.util.Collection;

public class Tweet {
    //id du tweet
    private int id_tweet;
    //contenu du tweet
    private String contenu;

    private User user;

    public Tweet(String contenu, User user){
        this.contenu=contenu;
        this.user=user;
    }
    public String getContenu(){
        return this.contenu;
    }
    public void setContenu(String contenu){
        this.contenu=contenu;
    }

    public int getIdTweet()
    {
        return id_tweet;
    }

    public void setIdTweet(int id)
    {
        id_tweet = id;
    }

    public User getUser() {
        return this.user;
    }
}
