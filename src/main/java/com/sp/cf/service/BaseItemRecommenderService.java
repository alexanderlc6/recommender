package com.sp.cf.service;

import com.sp.cf.common.JsonDataModel;
import com.sp.cf.druid.DruidBehaviorRepository;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Describe:
 * 内容项与内容项之间的相似度。
 * 1）得到内容项（Item）的历史评分数据；
 * 2）针对内容项进行内容项之间的相似度计算，找到目标内容项的“最近邻居”；
 * 3）产生推荐。这里内容项之间的相似度是通过比较两个内容项上的用户行为选择矢量得到的。
 *
 *alexlu
 *
 */
@Service
public class BaseItemRecommenderService {
    private static Logger logger = LoggerFactory.getLogger(BaseItemRecommenderService.class);

    @Autowired
    private DruidBehaviorRepository druidBehaviorRepository;

    public List<RecommendedItem> baseItemRecommender(long userId,long itemId,int amount) throws Exception {
        long startRead = System.currentTimeMillis();
        System.out.println("开始："+startRead);
        //File file = new File("E:\\迅雷下载\\ml-10m\\ml-10M100K\\ratings.dat");
        //File file = new File("C:\\Users\\Administrator\\Desktop\\test.txt");

        //从druid获取数据
        String dataJson =druidBehaviorRepository.getBehaviorListBySql();
        //将数据加载到内存中
        DataModel dataModel = new JsonDataModel(dataJson);
        //DataModel dataModel = new GroupLensDataModel(file);
        //DataModel dataModel = new FileDataModel(file);
        //计算相似度
        //ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        ItemSimilarity itemSimilarity = new CityBlockSimilarity(dataModel);
        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于物品的协同过滤推荐
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);

        List<RecommendedItem> recommendedItemList = recommender.recommendedBecause(userId, itemId, amount);
        long endRead = System.currentTimeMillis();
        System.out.println("文件读取结束，耗时："+(endRead -startRead));

        long start = System.currentTimeMillis();
        //打印推荐的结果
        System.out.println("使用基于物品的协同过滤算法");
        System.out.println("根据用户"+userId+"当前浏览的商品"+itemId+"，推荐"+amount+"个相似的商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
        System.out.println(System.currentTimeMillis() -start);
        return recommendedItemList;
    }

    public void main(String[] args){
        try {
            BaseItemRecommenderService service = new BaseItemRecommenderService();
            //给用户ID等于5的用户推荐10个与2398相似的商品
            service.baseItemRecommender(1l,802l,100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
