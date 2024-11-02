package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.internals.User;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;


public class TweetsResource extends ServerResource
{

    /** database. */
    private InMemoryDatabase db_;

   
    /**
     * Constructor.
     * Call for every single user request.
     */
    public TweetsResource()
    {
        db_ = (InMemoryDatabase) getApplication().getContext().getAttributes()
                .get("database");
    }
    
    @Post("json")
    public Representation createTweet(JsonRepresentation representation)
        throws Exception
    {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);

        // Récupérer l'utilisateur de la base de données
        User user = db_.getUser(userId);

        JSONObject object = representation.getJsonObject();
        String contenu = object.getString("contenu");
            
        // Save the tweet
        Tweet tweet = db_.createTweet(contenu,user);

        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("contenu", tweet.getContenu());
        resultObject.put("id", tweet.getIdTweet());
        //resultObject.put("user", user.getName());
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }

    @Get("json")
    public Representation getTweet() throws JSONException {

        String userIdString = (String) getRequest().getAttributes().get("userId");
        Collection<Tweet> tweets = db_.getUserTweet();
        Collection<JSONObject> jsonTweets = new ArrayList<>();
    
       
        if (userIdString != null) {
            int userId = Integer.valueOf(userIdString);
            User user = db_.getUser(userId);
            
            if (user == null) {
                // Si l'utilisateur n'existe pas, retourner une réponse vide ou une erreur
                return new JsonRepresentation(new JSONArray()); 
            }
    
            for (Tweet tweet : tweets) {
                if (tweet.getUser().getId() == userId) {
                    JSONObject current = new JSONObject();
                    current.put("id", tweet.getIdTweet());
                    current.put("contenu", tweet.getContenu());
                    current.put("name", tweet.getUser().getName());
                    jsonTweets.add(current);
                }
            }
        } else {
            // Si l'userId n'est pas fourni, retourner tous les tweets
            for (Tweet tweet : tweets) {
                JSONObject current = new JSONObject();
                current.put("id", tweet.getIdTweet());
                current.put("contenu", tweet.getContenu());
                current.put("name", tweet.getUser().getName());
                jsonTweets.add(current);
            }
        }
    
        JSONArray jsonArray = new JSONArray(jsonTweets);
        return new JsonRepresentation(jsonArray);
    }
    
}
