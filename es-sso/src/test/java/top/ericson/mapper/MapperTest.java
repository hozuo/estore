 package top.ericson.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.User;


/**
 * @author Ericson
 * @class MapperTest
 * @date 2020/04/07 21:53
 * @version 1.0
 * @description 
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {
    
    @Autowired
    UserMapper mapper;
    
    @Test
    public void test1() {
        Set<Integer> idSet=new HashSet<Integer>();
        idSet.add(1);
        idSet.add(2);
        idSet.add(3);
        List<User> findInstockByPage = mapper.selectUsersNameById(idSet);
        log.debug("findInstockByPage:{}", findInstockByPage);
    }
}
