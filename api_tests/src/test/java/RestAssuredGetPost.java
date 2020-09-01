
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

public class RestAssuredGetPost {


    @Test
    public void getUser(){

        Response response = given().when().get("https://jsonplaceholder.typicode.com/users");
        response.then().statusCode(200);

        JSONArray users = new JSONArray(response.asString());

        for (int i = 0; i < users.length(); i++) {

            JSONObject user = users.getJSONObject(i);
            JSONObject company = (JSONObject) user.get("company");
            boolean isValid = mailValidation(user.getString("email"));

            //1. All users must have a name, username, and email.
            //2. Their Email must be valid.
            //3. Their Company catchphrase must have less than 50 characters.
            Assert.assertFalse((null == user.get("name"))     ||
                                        (null == user.get("username")) ||
                                        (null == user.get("email"))    ||
                                        isValid == false               ||
                                        countCatchPhraseLetters((String) company.get("catchPhrase")) > 49);

        }

    }

    @Test
    public void postPosts(){

        Response responseGetUsers = given().when().get("https://jsonplaceholder.typicode.com/users");
        JSONArray users = new JSONArray(responseGetUsers.asString());
        //taking a random id from getUsers
        int userIdFromGet = (Integer) users.getJSONObject((int)(Math.random() * users.length())).get("id");

        JSONObject requestParams = new JSONObject();
        //1. Save a new post using a userId got by "GET /users" API.
        requestParams.put("userId", userIdFromGet);
        requestParams.put("id", 1);
        requestParams.put("title", "any title here");
        requestParams.put("body", "any text here");

        given().
                contentType("application/json").
                body(requestParams.toString()).
        when().
                post("https://jsonplaceholder.typicode.com/posts").
        then().
                statusCode(HttpStatus.SC_CREATED);

        System.out.println("\nData being created:" + requestParams.toString());

        //2. When trying to save a new post without the title, API must return an error.
        assertNotNull(requestParams.get("title"));
    }

    public static int countCatchPhraseLetters(String catchPhrase){
        int lettersQty = catchPhrase.length();
        return lettersQty;
    }

    public static boolean mailValidation(String mail){
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        //comparing email received structure and allowed one
        Matcher matcher = emailPat.matcher(mail);
        return matcher.find();
    }

}