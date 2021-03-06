package org.corbin.common.repository;

import org.corbin.common.base.dao.BaseRepository;
import org.corbin.common.entity.CollectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectInfoRepository extends BaseRepository<CollectInfo, Long> {

    /**
     * 查找我的收藏
     *
     * @param userId
     * @param collectType
     * @return
     */
    Page<CollectInfo> findAllByUserIdAndCollectType(Pageable pageable, Long userId, Integer collectType);

    CollectInfo findByUserIdAndCollectId(Long userId, Long collectId);

    List<CollectInfo> findAllByUserIdAndCollectType(Long userId, Integer collectType);

    /**
     * 最喜欢的2中歌曲类型
     * @param userId
     * @return
     */
    @Query(value = "SELECT collect_song_type from (SELECT collect_info.collect_song_type,COUNT(collect_info.collect_song_type) as collect_song_type_count FROM collect_info WHERE user_id = ?1 GROUP BY collect_song_type ORDER BY collect_song_type_count desc LIMIT 2) as temp_table", nativeQuery = true)
    List<Integer> findMostLoveSongType(Long userId);


    /**
     * 查找用户收藏的歌曲的歌手，没有去重，
     * 用户定位最喜欢的歌手
     * @return
     */
    //此repository没有注册SinggerInfo，查询出的为多字段，不会自动绑定,报类型转换错误
//    @Query(value = "SELECT * FROM singer_info WHERE singer_id IN (SELECT song_info.singer_id FROM song_info,collect_info WHERE song_info.song_id=collect_info.collect_id and collect_info.user_id=?1)",nativeQuery = true)
//    List<SingerInfo> findAllCollectSingInWithRepeat(Long userId);


//    @Query(value = "SELECT * FROM collect_info",nativeQuery = true)
//    List<CollectInfo> findtest();

    /**
     * 查找用户收藏的所有音乐的id
     * @param userId
     * @return
     */
    @Query("select c.collectId from CollectInfo  c where c.userId=?1 and c.collectType=1")
    List<Long> findAllUserCollectSongIdByUserId(Long userId);
}
