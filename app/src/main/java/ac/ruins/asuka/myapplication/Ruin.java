package ac.ruins.asuka.myapplication;

/**
 * Created by Tsubasa Shimomura on 2017/11/18.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "ruin")
public class Ruin extends Model {

    @Column(name = "prefecture")
    public long prefecture;

    @Column(name = "name")
    public String name;

    @Column(name = "longtitude")
    public double longtitude;

    @Column(name = "latitude")
    public double latitude;

    public Ruin(){
        super();
    }
}
