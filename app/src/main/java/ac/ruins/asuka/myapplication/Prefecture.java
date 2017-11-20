package ac.ruins.asuka.myapplication;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

/**
 * Created by Tsubasa Shimomura on 2017/11/18.
 */

@Table
public class Prefecture {
    @PrimaryKey
    public long id;

    @Column
    public String name;
}
