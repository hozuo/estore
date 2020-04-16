 package top.ericson.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Instock;


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
    InstockMapper mapper;
    
    @Test
    public void test1() {
        ArrayList<Integer> inIdList = new ArrayList<>();
        inIdList.add(1);
        inIdList.add(2);
        inIdList.add(3);
        inIdList.add(4);
        ArrayList<Integer> itemIdList = new ArrayList<>();
        itemIdList.add(1);
        itemIdList.add(3);
        itemIdList.add(4);
        List<Instock> findInstockByPage = mapper.findInstockByPage(0, 9,"in_id; SELECT * FROM sys_user #","asc", inIdList, itemIdList);
        log.debug("findInstockByPage:{}", findInstockByPage);
    }
}
