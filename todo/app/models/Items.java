package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Administrator on 11/7/2017.
 */

@Entity
@Table(name ="tbl_items")
public class Items extends Model {

    @Id
    public Long id;

    public Long categoryId;

    public String itemCode;

    public String itemName;

    public String comments;

    public static Finder<Long,Items> find=new Finder<Long,Items>(Long.class,Items.class);

    public static Items findbyCategoryId(Long Id){
        return find.where().eq("Id",Id).findUnique();
    }

    public static List<Items> getAllItems(){return find.where().findList();}

}
