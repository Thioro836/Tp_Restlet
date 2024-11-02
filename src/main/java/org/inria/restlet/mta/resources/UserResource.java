package org.inria.restlet.mta.resources;

import java.util.Collection;

import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.internals.User;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.resources.TweetsResource;

/**
 *
 * Resource exposing a user.
 *
 * @author msimonin
 * @author ctedeschi
 *
 */
public class UserResource extends ServerResource
{

    /** database. */
    private InMemoryDatabase db_;

    /** Utilisateur géré par cette resource. */
    private User user_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public UserResource()
    {
        db_ = (InMemoryDatabase) getApplication().getContext().getAttributes()
                .get("database");
    }


    @Get("json")
    public Representation getUser() throws Exception
    {
        //Collection<Tweet> tweet = db_.getUserTweet();
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);
        user_ = db_.getUser(userId);

        JSONObject userObject = new JSONObject();
        userObject.put("name", user_.getName());
        userObject.put("age", user_.getAge());
        userObject.put("id", user_.getId());
       // userObject.put("tweet", user_.getUserTweet());

        return new JsonRepresentation(userObject);
    }
    @Delete("json")
    public Representation deleteUser() throws Exception

    {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);
        user_ = db_.getUser(userId);
       boolean isdeleted= db_.deletUser(userId);
        if(isdeleted){
            return new JsonRepresentation("user deleted");
        }
        else{
            return new JsonRepresentation("User not found");
        }

    }
}
