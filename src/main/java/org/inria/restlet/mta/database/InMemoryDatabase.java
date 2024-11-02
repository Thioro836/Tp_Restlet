package org.inria.restlet.mta.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.internals.User;

/**
 *
 * In-memory database
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class InMemoryDatabase
{

    /** User count (next id to give).*/
    private int userCount_;
    private int tweetCount_;
    /** User Hashmap. */
    Map<Integer, User> users_;
    Map<Integer, Tweet> tweet;

    public InMemoryDatabase()
    {
        users_ = new HashMap<Integer, User>();
        tweet=  new HashMap<Integer, Tweet>();
    }

    /**
     *
     * Synchronized user creation.
     * @param name
     * @param age
     *
     * @return the user created
     */
    public synchronized User createUser(String name, int age)
    {
        User user = new User(name, age);
        user.setId(userCount_);
        users_.put(userCount_, user);
        userCount_ ++;
        return user;
    }

    public Collection<User> getUsers()
    {
        return users_.values();
    }

    public User getUser(int id)
    {
        return users_.get(id);
    }
  
    public synchronized boolean deletUser(int id){
       
        if(users_.containsKey(id)){
        users_.remove(id);
        return true;
        }
        
        return false;
    }


    public synchronized Tweet createTweet(String contenu,User user)
    {
        Tweet newTweet = new Tweet(contenu,user);
        newTweet.setIdTweet(tweetCount_);
        this.tweet.put(tweetCount_,newTweet);
        tweetCount_ ++;
        return newTweet;
    }
    //recuperer les tweet

    public Collection<Tweet> getUserTweet()
    {
        return tweet.values();
    }

    public Tweet getTweet(int id_tweet)
    {
        return tweet.get(id_tweet);
    }

   
}
