package top.wefor.now.data.database;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import top.wefor.now.data.model.entity.Zhihu;
import top.wefor.now.data.model.realm.RealmZhihu;

/**
 * 用与Realm数据库的的
 * Created by ice on 16/4/13 16:12.
 */
public class ZhihuDbHelper {

    private List<Zhihu> mEntityList;
    private Realm mRealm;

    public ZhihuDbHelper(List<Zhihu> entityList, Realm realm) {
        mEntityList = entityList;
        mRealm = realm;
    }


    public void saveToDatabase() {
        if (mEntityList.size() > 0)
            for (int i = mEntityList.size() - 1; i >= 0; i--) {
                mRealm.beginTransaction();
                RealmZhihu realmZhihu = new RealmZhihu();
                realmZhihu.setFromEntity(mEntityList.get(i));
                mRealm.copyToRealmOrUpdate(realmZhihu);
                mRealm.commitTransaction();
            }

    }

    public void getFromDatabase() {
        mEntityList.clear();

        RealmResults<RealmZhihu> zcoolRealmResults = mRealm.where(RealmZhihu.class).findAll();
        for (int i = zcoolRealmResults.size() - 1; i >= 0; i--) {
            mEntityList.add(zcoolRealmResults.get(i).toEntity());
        }
    }

    /**
     * @param pageSize 分页大小.
     * @param page     第几页，从1开始.
     */
    public void getFromDatabase(int pageSize, int page) {
        if (page == 1) mEntityList.clear();
        RealmResults<RealmZhihu> zcoolRealmResults = mRealm.where(RealmZhihu.class).findAll();
        //反序取数据,即 startPos > endPos.
        int startPos = Math.max(zcoolRealmResults.size() - 1 - (page - 1) * pageSize, 0);
        int endPos = Math.max(startPos - pageSize, 0);

        for (int i = startPos; i > endPos; i--) {
            mEntityList.add(zcoolRealmResults.get(i).toEntity());
        }
    }

}
