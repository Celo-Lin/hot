package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.HotCoopDao;
import com.hot.modules.hot.entity.HotCoop;
import com.hot.modules.hot.entity.HotCoopData;
import com.hot.modules.hot.entity.HotCoopProduct;
import com.hot.modules.hot.entity.HotCoopSett;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotCoopService {

    @Autowired
    private HotCoopDao coopDao;

    public HotCoop get(Integer id) {
        return coopDao.get(id);
    }

    public List<HotCoop> list(HotCoop coop) {
        return coopDao.list(coop);
    }

    public int save(HotCoop coop) {
        int i = 0;
        Integer id = coop.getId();
        if (coop.getId() == null) {
            i = coopDao.insert(coop);
            id = coop.getId();
        } else {
            i = coopDao.update(coop);
        }
        coopDao.deleteList(id, coop.getCreateUser());
        List<HotCoopProduct> prodList = coop.getProdList();
        if (prodList != null && prodList.size() > 0) {
            for (HotCoopProduct prod : prodList) {
                prod.setCoopId(id);
                if (prod.getId() == null) {
                    coopDao.insertProduct(prod);
                } else {
                    coopDao.updateProduct(prod);
                }
            }
        }
        List<HotCoopSett> settList = coop.getSettList();
        if (settList != null && settList.size() > 0) {
            for (HotCoopSett sett : settList) {
                sett.setCoopId(id);
                if (sett.getId() == null) {
                    coopDao.insertSett(sett);
                } else {
                    coopDao.updateSett(sett);
                }
            }
        }
        List<HotCoopData> dataList = coop.getDataList();
        if (dataList != null && dataList.size() > 0) {
            for (HotCoopData data : dataList) {
                data.setCoopId(id);
                if (data.getId() == null) {
                    coopDao.insertData(data);
                } else {
                    coopDao.updateData(data);
                }
            }
        }
        return i;
    }

    public int delete(Integer id, String userid) {
        return coopDao.delete(id,userid);
    }

    public int close(Integer id, String userid) {
        return coopDao.close(id,userid);
    }
}
