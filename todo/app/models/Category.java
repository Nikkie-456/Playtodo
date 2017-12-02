package models;

import controllers.Application;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Administrator on 11/4/2017.
 */


@Entity
@Table(name ="tbl_category")
public class Category extends Model {

    @Id
    public Long id;

    public String categoryCode;

    public String categoryName;

    public Long userid;

    public String comments;

    public static Finder<Long,Category> find=new Finder<Long,Category>(Long.class,Category.class);

    public static List<Category> getAllCategory(){
        Long userId= Application.getUserBySession().id;
        return find.where().eq("userid",userId).findList();
    }

    public static Category findbyCategoryId(Long Id){
        return find.where().eq("Id",Id).findUnique();
    }

}
