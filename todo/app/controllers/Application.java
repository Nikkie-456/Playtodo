package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Optional;
import models.Category;
import models.CategoryDetails;
import models.Items;
import models.Users;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

import java.util.Date;


public class Application extends Controller {

    public static Result index() {
      return ok(login.render("Login Page || TODO"));
    }

    public static Result registerPage() {
        return ok(register.render("Register || TODO"));
    }

    public static Result categoryPage() {
        if(checkUserSession())
            return ok(category.render("Category Details || TODO"));
        return index();
    }


    public static Result category() {
        if(checkUserSession())
        return ok(category.render("Category || TODO"));
        return index();
    }


    public static Result categoryDetailsPage() {
        if(checkUserSession())
        return ok(categoryDetails.render("Sub Category Details || TODO"));
        return index();
    }

    public static Result categoryDetails() {
        if(checkUserSession())
        return ok(categoryDetails.render("Sub Category || TODO"));
        return index();
    }

    public static Result itemdetails() {
        if(checkUserSession())
        return ok(itemDetails.render("Item TODO"));
        return index();
    }





    public static Result viewDetails(Long Id) {
        Category itm = Category.findbyCategoryId(Id);
        if(checkUserSession())
        return ok(viewItems.render("View Item Details",itm));
        return index();
    }


    public static Result itemPage() {
        if(checkUserSession())
        return ok(itemDetails.render("Item Definition TODO"));
        return index();
    }

    public static Result register() {

        ObjectNode result= Json.newObject();

        DynamicForm form= Form.form().bindFromRequest();
        String username =form.get("username");
        String password =form.get("password");
        String name =form.get("surname");
        String email =form.get("email");
        String pin =form.get("pin");


        if (Users.findUserByUsername(username) !=null) {
            result.put("message","Users"+username+"already exist!");
            result.put("code","201");
            return ok(result);
        }


        Users userDt=new Users();
        userDt.username= username;
        userDt.password= password;
        userDt.email= email;
        userDt.name= name;
        userDt.pin= pin;
        userDt.save();

        result.put("message","Users"+username+"Created Successfully!");
        result.put("code","2000");
        return ok(result);
    }

    public static Result checkUser() {

        DynamicForm form = Form.form().bindFromRequest();
        Logger.info(form.get("username"));
        ObjectNode result = Json.newObject();

        String username = form.get("username");
        String password = form.get("password");

        Users check = Users.findUserByUsername(username);

        if (check != null) {
            if (!check.password.equals(password)) {
                result.put("message", "Invalid Password!");
                result.put("code", "2001");
                return ok(result);
            }
        } else{
            result.put("message", "Invalid Username!");
            result.put("code", "2002");
            return ok(result);
        }

        session().put("username",check.username);
        result.put("message","Login Successfull!");
        result.put("code","200");
        return ok(result);
    }

    //user object session

    public  static Users getUserBySession() {
        Logger.info("Check if user is logged in here");
        if (session().get("username")!=null)
            return Users.findUserByUsername(session().get("username"));

        Logger.info("User Is Not Logged In");
        return null;
    }

    //user session check

    public static boolean checkUserSession(){
           if (session().get("username")!=null)
               return true;
        return false;
    }

    //clear session

    public static Result logouts() {
        session().clear();
        return index();
    }

    public static Result categDetails(){
        ObjectNode result= Json.newObject();

        DynamicForm form= Form.form().bindFromRequest();
        String categoryCode =form.get("categoryCode");
        String categoryName =form.get("categoryName");
        String comments =form.get("comments");

          Logger.info("New"+categoryCode);
//        if (Users.findUserByUsername(categoryName) !=null) {
//            result.put("message","Category"+categoryName+"already exist!");
//            result.put("code","202");
//            return ok(result);
//        }


        Category categDt=new Category();
        categDt.categoryCode= categoryCode;
        categDt.userid =getUserBySession().id;
        categDt.categoryName= categoryName;
        categDt.comments= comments;
        categDt.save();

        Logger.info("Saved");

        result.put("message","Category"+categoryName+"Created Successfully!");
        result.put("code","2004");
        return categoryDetailsPage();
    }



    public static Result categSubDetails(){
        ObjectNode result= Json.newObject();

        DynamicForm form= Form.form().bindFromRequest();
        String categoryCode =form.get("categoryCode");
        String categorySubCode =form.get("categorySubCode");
        String categoryName =form.get("categoryName");


        if (Users.findUserByUsername(categoryName) !=null) {
            result.put("message","Category"+categoryName+"already exist!");
            result.put("code","203");
            return ok(result);
        }


        CategoryDetails categDt=new CategoryDetails();
        categDt.categoryCode= categoryCode;
        categDt.categorySubCode= categoryCode;
        categDt.categoryName= categoryName;
        categDt.save();

        result.put("message","Category"+categoryName+"Created Successfully!");
        result.put("code","2006");
        return ok(result);
    }

    public static Result itemDetails(){

        ObjectNode result=Json.newObject();

        DynamicForm form=Form.form().bindFromRequest();
        String categoryId=form.get("categoryId");
        String itemCode=form.get("itemCode");
        String itemName=form.get("itemName");
        String comments=form.get("comments");

        Items itemDt=new Items();
        itemDt.categoryId= Long.valueOf(categoryId);
        itemDt.itemCode= itemCode;
        itemDt.itemName= itemName;
        itemDt.comments= comments;
        itemDt.save();

        result.put("message","Item"+itemName+"Created Successfully!");
        result.put("code","2007");
        return viewDetails(Long.valueOf(categoryId));


    }

    public static Result delCat(Long Id) {
        Category del= Category.findbyCategoryId(Id);
        if (del != null) {
            Logger.info("Category Located! Delete Category");
            del.delete();
        }
        else {
        Logger.info("Category Not Found!");
        }

        return categoryDetailsPage();

    }

    public static Result delItm(Long Id) {
        Items del= Items.findbyCategoryId(Id);
        if (del != null) {
            Logger.info("Sure You Want To Delete This Item");
            del.delete();
        }
        else {
            Logger.info("Item Not Found!");
        }

        return viewDetails(Id);

    }


    // Edit Section


    public static Result editCat(Long Id) {
        Category itm = Category.findbyCategoryId(Id);
        return ok(editCategory.render("Edit Category Details",Id,itm));
    }

    public static Result editCategory() {

        ObjectNode result=Json.newObject();

        DynamicForm form=Form.form().bindFromRequest();
        String Id= form.get("categoryId");
        String categoryCode=form.get("categoryCode");
        String categoryName=form.get("categoryName");
        String comments=form.get("comments");

        Category edt= Category.findbyCategoryId(Long.valueOf(Id));
        if (edt != null) {
            Logger.info("Edit Category");
            edt.categoryCode=categoryCode;
            edt.categoryName=categoryName;
            edt.comments=comments;
            edt.save();
        }
        else {
            Logger.info("Category Not Found!");
        }

        return categoryDetailsPage();

    }

    // Forgot Password

    public static Result forgotPswdPage() {
        return ok(forgotPassword.render("Forgot Password"));
    }

    public static Result viewPsword(Users userpswd) {
        return ok(viewPassword.render(userpswd));
    }

    public static Result forgotPassword() {

        DynamicForm form = Form.form().bindFromRequest();
        Logger.info(form.get("username"));
        ObjectNode result = Json.newObject();

        String username = form.get("username");
        String pin = form.get("pin");
        String password = form.get("password");

        Users pinCheck = Users.findUserByUsername(username);

        if (pinCheck != null) {
            if (!pinCheck.pin.equals(pin)) {
                result.put("message", "Pin Is Invalid");
                result.put("code", "2009");
                return ok(result);
            }
        } else{
            result.put("message", "Invalid Pin!");
            result.put("code","2008");
            return ok(result);
        }

        return viewPsword(pinCheck);
    }

    //Sessions Section

    private static final long maxInactivityTime = 300000L; // 5 minutes
    private static final String LAST_SEEN = "last_seen";


//    public Result index1() {
//        return Optional.ofNullable(session(LAST_SEEN))
//                .map(s -> new Date().getTime() - Long.valueOf(s) > maxInactivityTime ? renderfirstVisit() : rendersecondVisit())
//                .orElseGet(this::renderfirstVisit);
//
//    }

    private Result rendersecondVisit() {
               updateLastSeen();
               return ok(secondVisit.render());
    }

    private Result renderfirstVisit() {
        updateLastSeen();
        return ok(firstVisit.render());
    }

    private void updateLastSeen() {
        session().put(LAST_SEEN, String.valueOf(new Date().getTime()));
    }

}
