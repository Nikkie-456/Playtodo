package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Administrator on 11/4/2017.
 */

@Entity
@Table(name="tbl_category_details")
public class CategoryDetails extends Model{

    @Id
    public Long id;

    public String categoryCode;

    public String categorySubCode;

    public String categoryName;

    public static Finder<Long,CategoryDetails> find=new Finder<Long,CategoryDetails>(Long.class,CategoryDetails.class);




}
