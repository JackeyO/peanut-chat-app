package com.sici.chat.repositories.es.room;

import com.sici.chat.model.chat.room.es.RoomDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author jackey
 * @description
 * @date 5/29/25 20:41
 */
public interface RoomElasticSearchRepository extends ElasticsearchRepository<RoomDocument, Long> {
}
