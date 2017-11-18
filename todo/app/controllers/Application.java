package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
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

public class Application extends Controller {

    public static Result index() {
        return ok(login.render("Login Page || TODO"));
    }

    public static Result registerPage() {
        return ok(register.render("Register || TODO"));
    }

    public static Result categoryPage() {
        return ok(category.render("Category Details || TODO"));
    }


    public static Result category() {
        return ok(category.render("Category || TODO"));
    }


    public static Result categoryDetailsPage() {
        return ok(categoryDetails.render("Sub Category Details || TODO"));
    }

    public static Result categoryDetails() {
        return ok(categoryDetails.render("Sub Category || TODO"));
    }

    public static Result itemdetails() {
        return ok(itemDetails.render("Item TODO"));
    }

    public static Result viewDetails(Long Id) {
        Category itm = Category.findbyCategoryId(Id);
        return ok(viewItems.render("View Item Details",itm));
    }

    public static Result itemPage() {
        return ok(itemDetails.render("Item Definition TODO"));
    }

    public static Result register() {

        ObjectNode result= Json.newObject();

        DynamicForm form= Form.form().bindFromRequest();
        String username =form.get("username");
        String password =form.get("password");
        String name =form.get("surname");
        String email =form.get("email");


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
                return index();
            }
        } else{
            result.put("message", "Invalid Username!");
            result.put("code", "2002");
            return ok(result);
        }

        result.put("message","Login Successfull!");
        result.put("code","2003");
        return categoryDetailsPage();
    }

    public static Result categDetails(){
        ObjectNode result= Json.newObject();

        DynamicForm form= Form.form().bindFromRequest();
        String categoryCode =form.get("categoryCode");
        String categoryName =form.get("categoryName");
        String comments =form.get("comments");


        if (Users.findUserByUsername(categoryName) !=null) {
            result.put("message","Category"+categoryName+"already exist!");
            result.put("code","202");
            return ok(result);
        }


        Category categDt=new Category();
        categDt.categoryCode= categoryCode;
        categDt.categoryName= categoryName;
        categDt.comments= comments;
        categDt.save();

        result.put("message","Category"+categoryName+"Created Successfully!");
        result.put("code","2004");
        return ok(result);
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
        return ok(result);


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

}
