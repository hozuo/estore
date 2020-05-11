package top.ericson.service;

import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.metadata.IPage;

import top.ericson.pojo.Supplier;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.SupplierInfo;

/**
 * @author Ericson
 * @class InstockService
 * @date 2020/04/20
 * @version 1.0
 * @description 供应商service
 */
public interface SupplierService {

    Integer create(SupplierInfo supplierInfo);

    Integer deleteById(Integer id);

    Integer update(SupplierInfo supplierInfo);

    Supplier findById(Integer id);

    IPage<Supplier> findPage(PageQuery pageQuery);

    String findNameById(Integer id);

    Map<Integer, String> findNamesById(Set<Integer> idSet);

}
