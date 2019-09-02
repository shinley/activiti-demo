package mybatis_plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import model.User;

public class UserTest {
    public void selectByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-14");

    }
}
