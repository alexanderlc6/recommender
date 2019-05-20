package com.sp.cf.service;

import com.sp.cf.common.JsonDataModel;
import com.sp.cf.druid.DruidBehaviorRepository;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *基于用户的协同过滤算法
 *
 * alexlu
 *
 */
@Service
public class BaseUserRecommenderService {
    private static Logger logger = LoggerFactory.getLogger(BaseUserRecommenderService.class);

    @Autowired
    private DruidBehaviorRepository druidBehaviorRepository;

    public  List<RecommendedItem> baseUserRecommender(long userId,int amount) throws Exception {
        //准备数据
        //File file = new File("E:\\迅雷下载\\ml-10m\\ml-10M100K\\ratings.dat");
        //File file = new File("C:\\Users\\Administrator\\Desktop\\test.csv");
        //从druid获取数据
        String dataJson =druidBehaviorRepository.getBehaviorListBySql();
        long startRead = System.currentTimeMillis();
        System.out.println("开始："+startRead);
        //将数据加载到内存中
        DataModel dataModel = new JsonDataModel(dataJson);
        //计算相似度
        //UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        //UserSimilarity similarity = new UncenteredCosineSimilarity(dataModel);
        UserSimilarity similarity = new CityBlockSimilarity(dataModel);
        //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
        UserNeighborhood userNeighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);
        //用基于用户的协同过滤推荐
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        //推荐东西给用户
        List<RecommendedItem> recommendedItemList = recommender.recommend(userId, amount);
        long endRead = System.currentTimeMillis();
        System.out.println("计算结束，耗时："+(endRead -startRead));
        //打印推荐的结果
        System.out.println("使用基于用户的协同过滤算法");
        System.out.println("为用户"+userId+"推荐"+amount+"个商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
        return recommendedItemList;
    }

    public static void main(String[] args){
        try {
            BaseUserRecommenderService baseUserRecommenderService = new BaseUserRecommenderService();
            baseUserRecommenderService.baseUserRecommender(2l,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
